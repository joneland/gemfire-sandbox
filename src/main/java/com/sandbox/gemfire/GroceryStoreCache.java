package com.sandbox.gemfire;

import static com.gemstone.gemfire.cache.RegionShortcut.REPLICATE;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.server.CacheServer;

public class GroceryStoreCache {
	private static final String DISABLE_MULTICAST = "0";

	private Cache cache;

	public GroceryStoreCache(int serverPort, CacheRegion... regions) {
		this.cache = new CacheFactory().set("mcast-port", DISABLE_MULTICAST).create();
		configureCacheServer(serverPort);
		configureRegions(regions);
	}

	private void configureCacheServer(int serverPort) {
		CacheServer cacheServer = cache.addCacheServer();
		cacheServer.setPort(serverPort);
	}

	private void configureRegions(CacheRegion... regions) {
		for (CacheRegion region : regions) {
			cache.createRegionFactory(REPLICATE).create(region.name());
		}
	}

	public void start() throws IOException {
		this.cache.getCacheServers().get(0).start();
	}

	public void stop() {
		cache.close();
	}

	public boolean isRunning() {
		List<CacheServer> cacheServers = cache.getCacheServers();
		return cacheServers.size() == 0 ? false : cacheServers.get(0).isRunning();
	}

	public void populate(CacheRegion region, CacheItem cacheItem) {
		cache.getRegion(region.name()).put(cacheItem.getId(), cacheItem);
	}

	public String print(CacheRegion region) {
		StringBuilder cacheItems = new StringBuilder();
		for (Entry<Object, Object> cacheItem : cache.getRegion(region.name()).entrySet()) {
			cacheItems.append(cacheItem.getValue() + "\n");
		}
		return cacheItems.toString();
	}
}