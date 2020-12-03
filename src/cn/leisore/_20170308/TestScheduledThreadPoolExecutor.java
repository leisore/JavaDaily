package cn.leisore._20170308;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class TestScheduledThreadPoolExecutor {

	public static void main(String[] args) {

		ScheduledThreadPoolExecutor pool = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(1, new ThreadFactory() {
			int id = 1;

			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setName("T-" + id++);
				return thread;
			}
		});
		pool.setKeepAliveTime(30, TimeUnit.SECONDS);
		pool.setMaximumPoolSize(10);
		pool.setCorePoolSize(1);
		pool.allowCoreThreadTimeOut(true);

		for (int i = 0; i < 100; i++) {
			final int j = i;
			pool.scheduleAtFixedRate(new Runnable() {

				String name = "R" + j;
				@Override
				public void run() {
					Random random = new Random();
					int sec = random.nextInt(10) + 1;
					try {
						TimeUnit.SECONDS.sleep(sec);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + "-- run-" + name + "-" + sec);
				}
			}, 0, 10, TimeUnit.SECONDS);
		}

	}
}
