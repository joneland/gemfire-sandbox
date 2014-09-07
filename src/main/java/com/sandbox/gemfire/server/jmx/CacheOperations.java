package com.sandbox.gemfire.server.jmx;

import com.sandbox.gemfire.server.GroceryStoreCache;

public class CacheOperations implements CacheOperationsMBean {
	private GroceryStoreCache cache;

	public CacheOperations(GroceryStoreCache cache) {
		this.cache = cache;
	}

	public void stop() {
		cache.stop();
	}
}