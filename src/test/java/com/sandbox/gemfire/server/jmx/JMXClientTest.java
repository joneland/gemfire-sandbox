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
public class JMXClientTest {
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
				new String[] {},
				new String[] {});
	}
}