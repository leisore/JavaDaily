package cn.leisore._20201115;
import java.util.Optional;

import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.cache.KVCache;
import com.orbitz.consul.model.kv.Value;

public class TestConconsulKVListener {

	public static void main(String[] args) {

		Consul client = Consul.builder().withUrl("http://localhost:8500").build();

		KeyValueClient kvClient = client.keyValueClient();

		KVCache cache = KVCache.newCache(kvClient, "foo");
		cache.addListener(newValues -> {
		    // Cache notifies all paths with "foo" the root path
		    // If you want to watch only "foo" value, you must filter other paths
		    Optional<Value> newValue = newValues.values().stream()
		            .filter(value -> value.getKey().equals("foo"))
		            .findAny();

		    newValue.ifPresent(value -> {
		        // Values are encoded in key/value store, decode it if needed
		        Optional<String> decodedValue = newValue.get().getValueAsString();
		        decodedValue.ifPresent(v -> System.out.println(String.format("Value is: %s", v))); //prints "bar"
		    });
		});
		cache.start();
	}

}
