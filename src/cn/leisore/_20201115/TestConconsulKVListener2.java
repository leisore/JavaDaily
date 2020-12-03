package cn.leisore._20201115;

import java.util.concurrent.CompletableFuture;

import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.cache.KVCache;

public class TestConconsulKVListener2 {

	public static void main(String[] args) throws InterruptedException {

		Consul client = Consul.builder()
				.withUrl("http://localhost:8500")
				.build();

		final KeyValueClient kvClient = client.keyValueClient();

		KVCache cache = KVCache.newCache(kvClient, "a/b");
		cache.addListener(newValues -> {
			System.out.println("Changed " + newValues);
			CompletableFuture.runAsync( () -> {
				kvClient.putValue("a/b/c", "test");
			});
		});
		cache.start();
		Thread.sleep(10000000);
	}
}
