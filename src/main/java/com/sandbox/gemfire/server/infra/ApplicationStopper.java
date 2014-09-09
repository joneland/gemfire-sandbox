package com.sandbox.gemfire.server.infra;

import com.sandbox.gemfire.server.jmx.CacheJMXClient;

public class ApplicationStopper {
	public static void main(String[] args) {
		new ApplicationStopper().stop();
	}

	public void stop() {
		try {
			new CacheJMXClient("localhost", "10001").stopCache();
		} catch (Exception e) {
			throw new RuntimeException("Failed to call stop on cache", e);
		}
	}
}