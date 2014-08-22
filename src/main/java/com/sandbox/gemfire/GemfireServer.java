package com.sandbox.gemfire;

import static com.gemstone.gemfire.cache.RegionShortcut.REPLICATE;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.server.CacheServer;

public class GemfireServer {
	private int serverPort;
	private Cache cache;

	public GemfireServer(int serverPort) {
		this.serverPort = serverPort;
		this.cache = new CacheFactory().set("mcast-port", "0").create();
	}

	public void start() throws IOException {
		configureRegions("EMPLOYEES", "GROCERIES");
		startCacheServer();
	}

	private void configureRegions(String... regions) {
		for (String region : regions) {
			cache.createRegionFactory(REPLICATE).create(region);
		}
	}

	private void startCacheServer() throws IOException {
		CacheServer cacheServer = cache.addCacheServer();
		cacheServer.setPort(serverPort);
		cacheServer.start();
	}

	public void stop() {
		cache.close();
	}

	public boolean isRunning() {
		List<CacheServer> cacheServers = cache.getCacheServers();
		return cacheServers.size() == 0 ? false : cacheServers.get(0).isRunning();
	}

	public void populate(String region, CacheItem cacheItem) {
		cache.getRegion(region).put(cacheItem.getId(), cacheItem);
	}

	public String print(String region) {
		StringBuilder cacheItems = new StringBuilder();
		for (Entry<Object, Object> cacheItem : cache.getRegion(region).entrySet()) {
			cacheItems.append(cacheItem.getValue() + "\n");
		}
		return cacheItems.toString();
	}
}