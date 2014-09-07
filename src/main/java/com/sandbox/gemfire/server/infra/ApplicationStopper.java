package com.sandbox.gemfire.server.infra;

import static javax.management.remote.JMXConnectorFactory.connect;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXServiceURL;

public class ApplicationStopper {
	public static void main(String[] args) {
		new ApplicationStopper().stop();
	}

	public void stop() {
		try {
			MBeanServerConnection mbeanServerConnection = connect(
					new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:10001/jmxrmi")).getMBeanServerConnection();

			mbeanServerConnection.invoke(
					new ObjectName("com.sandbox.gemfire:type=CacheServer"),
					"stop",
					new String[] {},
					new String[] {});
		} catch (Exception e) {
			throw new RuntimeException("Failed to stop application", e);
		}
	}
}