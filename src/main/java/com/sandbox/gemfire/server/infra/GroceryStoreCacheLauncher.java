package com.sandbox.gemfire.server.infra;

import static com.sandbox.gemfire.server.CacheRegion.EMPLOYEES;
import static com.sandbox.gemfire.server.CacheRegion.GROCERIES;

import java.io.IOException;

import com.sandbox.gemfire.server.GroceryStoreCache;

public class GroceryStoreCacheLauncher {
	private static final int SERVER_PORT = 10000;

	public static void main(String[] args) {
		new GroceryStoreCacheLauncher().launch();
	}

	public void launch() {
		GroceryStoreCache cache = new GroceryStoreCache(SERVER_PORT, GROCERIES, EMPLOYEES);

		try {
			cache.start();
		} catch (IOException e) {
			throw new RuntimeException("Failed to launch cache server", e);
		}
	}
}