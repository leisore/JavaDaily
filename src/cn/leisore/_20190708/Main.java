package cn.leisore._20190708;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.common.utils.Bytes;

import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) {
		
		int[] idx = new int[10]; 
		Random rand = new Random();
		for (int i = 0; i < 100; i++) {
			int nextInt = rand.nextInt(10000);
			for (int j =0 ; j< idx.length; j++) {
				if (nextInt < (j+1)*1000) {
					idx[j] += 1;
					break;
				}	
			}
			
			System.out.println(nextInt);
		}
		for (int j =0 ; j< idx.length; j++) {
			System.out.println((j+1)*100 + ": " + idx[j]);
		}
		//System.out.println(codec(System.currentTimeMillis(),1));
	}

	static long codec(long l, int sortOrder) {

		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(l);
		byte[] bytes = buffer.array();

		long v;
		byte b = bytes[0];
		if (sortOrder == 0) {
			v = b ^ 0x80; // Flip sign bit back
			for (int i = 1; i < 8; i++) {
				b = bytes[0 + i];
				v = (v << 8) + (b & 0xff);
			}
		} else {
			b = (byte) (b ^ 0xff);
			v = b ^ 0x80; // Flip sign bit back
			for (int i = 1; i < 8; i++) {
				b = bytes[0 + i];
				b ^= 0xff;
				v = (v << 8) + (b & 0xff);
			}
		}
		return v;
	}
}

class HashServiceImpl {
	private final String salt;
	private final AtomicBoolean init = new AtomicBoolean(false);
	private final List<ThrashConfig> configs;
	private final InternalSemaphore permit;
	private volatile ThrashConfig currentConfig;
	private Random rng = new Random(2019);
	private ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

	HashServiceImpl(String salt, List<ThrashConfig> configs) {
		this.salt = salt;
		this.currentConfig = ThrashConfig.INIT_CONFIG;
		this.permit = new InternalSemaphore(currentConfig.max_concurrent);
		this.configs = Collections.unmodifiableList(configs);
	}

	public Integer hash(String input) {
		long st = System.currentTimeMillis();
		if (!init.get()) {
			if (init.compareAndSet(false, true)) {
				int startTime = 30;
				int totalPermit = ThrashConfig.INIT_CONFIG.max_concurrent;
				for (ThrashConfig thrashConfig : configs) {
					final int tmpTotal = totalPermit;
					scheduler.schedule(() -> refresh(thrashConfig, tmpTotal), startTime, TimeUnit.SECONDS);
					startTime += thrashConfig.durationInSec;
					totalPermit = thrashConfig.max_concurrent;
				}
			}
		}
		try {
			permit.acquire();
			long rtt = nextRTT();
			Thread.sleep(rtt);
			return (input + salt).hashCode();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			long cost = System.currentTimeMillis() - st;
			permit.release();
		}
		throw new IllegalStateException("Unexpected exception");
	}

	private void refresh(ThrashConfig thrashConfig, int totalPermit) {
		this.currentConfig = thrashConfig;
		int permitChange = totalPermit - thrashConfig.max_concurrent;
		if (permitChange != 0) {
			if (permitChange > 0) {
				permit.reducePermit(permitChange);
			} else {
				permit.addPermit(Math.abs(permitChange));
			}
		}

	}

	private long nextRTT() {
		double u = rng.nextDouble();
		int x = 0;
		double cdf = 0;
		while (u >= cdf) {
			x++;
			cdf = 1 - Math.exp(-1.0D * 1 / currentConfig.avg_rtt * x);
		}
		return x;
	}
}

class InternalSemaphore extends Semaphore {
	public InternalSemaphore(int permits) {
		super(permits);
	}

	public InternalSemaphore(int permits, boolean fair) {
		super(permits, fair);
	}

	public void acquire() throws InterruptedException {
		super.acquire();
	}

	void reducePermit(int n) {
		super.reducePermits(n);
	}

	void addPermit(int n) {
		super.release(n);
	}
}

class ThrashConfig {
	static final ThrashConfig INIT_CONFIG = new ThrashConfig(1600, 50);
	final long durationInSec = 6;
	final double avg_rtt;
	final int max_concurrent;

	public ThrashConfig(int max_concurrent, double avg_rtt) {
		this.avg_rtt = avg_rtt;
		this.max_concurrent = max_concurrent;
	}

	@Override
	public String toString() {
		return "Duration :" + durationInSec + " averageRTT:" + avg_rtt + " maxConcurrency:" + this.max_concurrent;
	}
}

class BaseConfig {
	private final int maxThreadCount;
	private final int port;
	private List<ThrashConfig> thrashConfigs = new ArrayList<>(10);

	protected BaseConfig(int maxThreadCount, int port) {
		this.maxThreadCount = maxThreadCount;
		this.port = port;
	}

	public static BaseConfig loadConf() {
		Gson gson = new Gson();
		String origin = loadResourceAsString("provider-conf.json");
		GlobalConf globalConf = gson.fromJson(origin, GlobalConf.class);
		String env = System.getProperty("quota");
		if (env == null) {
			System.out.println("[PROVIDER-SERVICE] No specific args found, use [DEFAULT] to run demo provider");
			env = "small";
		}
		BaseConfig config;
		switch (env) {
		case "small":
			config = new BaseConfig(200, 20880);
			config.thrashConfigs = globalConf.small;
			break;
		case "medium":
			config = new BaseConfig(450, 20870);
			config.thrashConfigs = globalConf.medium;
			break;
		case "large":
			config = new BaseConfig(650, 20890);
			config.thrashConfigs = globalConf.large;
			break;
		default:
			throw new IllegalStateException(
					"[PROVIDER-SERVICE] Bad property: quota,value:" + env + ". valid value is small/medium/large");
		}

		return config;

	}

	private static String loadResourceAsString(String fileName) {
		ClassLoader classLoader = getClassLoader();

		Enumeration<URL> resources;
		try {
			resources = classLoader.getResources(fileName);
		} catch (IOException e) {
			throw new IllegalStateException("Failed to load provider-conf.json,cause:" + e.getMessage(), e);
		}

		while (resources.hasMoreElements()) {
			URL url = resources.nextElement();
			try {
				String s = new String(Files.readAllBytes(Paths.get(url.toURI()))).replace("\n", "").trim();
				return s;
			} catch (Exception e) {
				throw new IllegalStateException("Failed to load provider-conf.json,cause:" + e.getMessage(), e);
			}
		}
		throw new IllegalStateException("Can not found provider-conf.json");
	}

	private static ClassLoader getClassLoader() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			return classLoader;
		}
		return BaseConfig.class.getClassLoader();
	}

	public int getMaxThreadCount() {
		return maxThreadCount;
	}

	public int getPort() {
		return port;
	}

	public List<ThrashConfig> getConfigs() {
		return thrashConfigs;
	}

}

class GlobalConf {
	public final List<ThrashConfig> small;
	public final List<ThrashConfig> medium;
	public final List<ThrashConfig> large;

	public GlobalConf(List<ThrashConfig> small, List<ThrashConfig> medium, List<ThrashConfig> large) {
		this.small = small;
		this.medium = medium;
		this.large = large;
	}
}
