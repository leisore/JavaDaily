package cn.leisore._20180116;

import java.io.File;
import java.util.UUID;

import org.ehcache.Cache;
import org.ehcache.CachePersistenceException;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.CacheManagerConfiguration;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

public class EHCacheTest {

	public static void main(String[] args) throws CachePersistenceException {
		CacheManagerConfiguration<PersistentCacheManager> persistence = CacheManagerBuilder
				.persistence(new File("./", "myData"));

		PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder().with(persistence)
				.build(true);

		persistentCacheManager.createCache("c1",
				CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder
						.newResourcePoolsBuilder().heap(10000, EntryUnit.ENTRIES).disk(1000, MemoryUnit.MB, true)));

		persistentCacheManager.createCache("c2",
				CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder
						.newResourcePoolsBuilder().heap(10000, EntryUnit.ENTRIES).disk(1000, MemoryUnit.MB, true)));
		
		Cache<Long, String> c1 = persistentCacheManager.getCache("c1", Long.class,
				String.class);
		
		Cache<Long, String> c2 = persistentCacheManager.getCache("c2", Long.class,
				String.class);
		
		Cache<Long, String> c3 = persistentCacheManager.getCache("c3", Long.class,
				String.class);

		for (long i = 0; i <= 10000; i++) {
			System.out.println(c1.get(i));
			c1.put(i, i + "");
		}
		
		for (long i = 0; i <= 100; i++) {
			System.out.println(c2.get(i));
			c2.put(i, i + "");
		}
		
		System.out.println(c3);

		persistentCacheManager.close();

	}

}
