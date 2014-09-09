package com.sandbox.gemfire.server.jmx;

import static org.mockito.Mockito.verify;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CacheJMXClientTest {
	private static final String HOST = "localhost";
	private static final String PORT = "1234";
	private static final String JMX_OBJECT_NAME = "com.sandbox.gemfire:type=CacheServer";

	@Mock
	private MBeanServerConnection server;

	private CacheJMXClient jmxClient;

	@Before
	public void initialise() {
		jmxClient = new CacheJMXClient(HOST, PORT) {
			@Override
			protected MBeanServerConnection initialiseServer(String host, String port) {
				return server;
			}
		};
	}

	@Test public void
	invokesStopOnCache() throws Exception {
		jmxClient.stopCache();

		verify(server).invoke(
				new ObjectName(JMX_OBJECT_NAME),
				"stop",
				new Object[] {},
				new String[] {});
	}

	@Test public void
	invokesPopulateGrocery_WithGivenParameters() throws Exception {
		jmxClient.populateGrocery(1, "Apple", "0.30");

		verify(server).invoke(
				new ObjectName(JMX_OBJECT_NAME),
				"populateGrocery",
				new Object[] {1, "Apple", "0.30"},
				new String[] {Integer.class.getName(), String.class.getName(), String.class.getName()});
	}

	@Test public void
	invokesPopulateEmployee_WithGivenParameters() throws Exception {
		jmxClient.populateEmployee(501, "Jon", "Eland", "Cashier");

		verify(server).invoke(
				new ObjectName(JMX_OBJECT_NAME),
				"populateEmployee",
				new Object[] {501, "Jon", "Eland", "Cashier"},
				new String[] {Integer.class.getName(), String.class.getName(), String.class.getName(), String.class.getName()});
	}

	@Test public void
	invokesClearGroceries() throws Exception {
		jmxClient.clearGroceries();

		verify(server).invoke(
				new ObjectName(JMX_OBJECT_NAME),
				"clearGroceries",
				new Object[] {},
				new String[] {});
	}

	@Test public void
	invokesClearEmployees() throws Exception {
		jmxClient.clearEmployees();

		verify(server).invoke(
				new ObjectName(JMX_OBJECT_NAME),
				"clearEmployees",
				new Object[] {},
				new String[] {});
	}
}