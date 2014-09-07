package com.sandbox.gemfire.server.infra;

import static com.sandbox.gemfire.server.CacheRegion.EMPLOYEES;
import static com.sandbox.gemfire.server.CacheRegion.GROCERIES;
import static java.lang.management.ManagementFactory.getPlatformMBeanServer;

import java.io.IOException;

import javax.management.ObjectName;

import com.sandbox.gemfire.server.GroceryStoreCache;
import com.sandbox.gemfire.server.jmx.CacheOperations;

public class ApplicationLauncher {
	private static final int SERVER_PORT = 10000;

	public static void main(String[] args) {
		new ApplicationLauncher().launch();
	}

	public void launch() {
		GroceryStoreCache cache = new GroceryStoreCache(SERVER_PORT, GROCERIES, EMPLOYEES);
		configureJmxOperationsOn(cache);

		try {
			cache.start();
		} catch (IOException e) {
			throw new RuntimeException("Failed to launch cache server", e);
		}
	}

	private void configureJmxOperationsOn(GroceryStoreCache cache) {
		try {
			getPlatformMBeanServer().registerMBean(
					new CacheOperations(cache),
					new ObjectName("com.sandbox.gemfire:type=CacheServer"));
		} catch (Exception e) {
			throw new RuntimeException("Failed to register JMX operations", e);
		}
	}
}