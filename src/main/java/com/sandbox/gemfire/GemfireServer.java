package com.sandbox.gemfire;

import java.io.IOException;
import java.util.List;

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
}