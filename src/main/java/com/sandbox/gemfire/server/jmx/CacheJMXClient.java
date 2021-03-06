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

	public void populateGrocery(int id, String name, String price) throws Exception {
		server.invoke(
				new ObjectName(JMX_OBJECT_NAME),
				"populateGrocery",
				new Object[] {id, name, price},
				new String[] {Integer.class.getName(), String.class.getName(), String.class.getName()});
	}

	public void populateEmployee(int id, String firstName, String surname, String role) throws Exception {
		server.invoke(
				new ObjectName(JMX_OBJECT_NAME),
				"populateEmployee",
				new Object[] {id, firstName, surname, role},
				new String[] {Integer.class.getName(), String.class.getName(), String.class.getName(), String.class.getName()});
		
	}

	public void clearGroceries() throws Exception {
		server.invoke(
				new ObjectName(JMX_OBJECT_NAME),
				"clearGroceries",
				new Object[] {},
				new String[] {});
	}

	public void clearEmployees() throws Exception {
		server.invoke(
				new ObjectName(JMX_OBJECT_NAME),
				"clearEmployees",
				new Object[] {},
				new String[] {});
		
	}
}