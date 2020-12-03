package cn.leisore._20201115;
import java.util.Map;

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.cache.ServiceHealthCache;
import com.orbitz.consul.cache.ServiceHealthKey;
import com.orbitz.consul.model.health.ServiceHealth;

public class TestConsulService2 {

	public static void main(String[] args) throws InterruptedException {
		Consul client = Consul.builder().withUrl("http://localhost:8500").build();
		
		HealthClient healthClient = client.healthClient();
		
		ServiceHealthCache svHealth = ServiceHealthCache.newCache(healthClient, "pproxies");
		svHealth.addListener((Map<ServiceHealthKey, ServiceHealth> newValues) -> {
			System.out.println(newValues);
		});
		svHealth.start();
		// ...

		// Discover only "passing" nodes
		//List<ServiceHealth> nodes= healthClient.getAllServiceInstances("myService").getResponse();
		//List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances("myService").getResponse();
		//nodes.forEach(System.out::println);
		
		System.out.println("HAHA");
		Thread.sleep(100000);
	}

}
