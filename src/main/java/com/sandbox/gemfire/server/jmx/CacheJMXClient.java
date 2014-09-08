package com.sandbox.gemfire.server.jmx;

import static java.lang.String.format;
import static javax.management.remote.JMXConnectorFactory.connect;

import java.io.IOException;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXServiceURL;

public class CacheJMXClient {
	private static final String JMX_URL = "service:jmx:rmi:///jndi/rmi://%s:%s/jmxrmi";
	private static final String JMX_OBJECT_NAME = "com.sandbox.gemfire:type=CacheServer";

	private MBeanServerConnection server;

	public CacheJMXClient(String host, String port) {
		server = initialiseServer(host, port);
	}

	protected MBeanServerConnection initialiseServer(String host, String port) {
		try {
			return connect(new JMXServiceURL(format(JMX_URL, host, port))).getMBeanServerConnection();
		} catch (IOException e) {
			throw new RuntimeException(format("Failed to connect to JMX running on %s:%s", host, port) ,e);
		}
	}

	public void stopCache() throws Exception {
		server.invoke(
				new ObjectName(JMX_OBJECT_NAME),
				"stop",
				new String[] {},
				new String[] {});
	}
}