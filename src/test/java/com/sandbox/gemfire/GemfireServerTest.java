package com.sandbox.gemfire;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class GemfireServerTest {
	private static final int SERVER_PORT = 10000;

	private GemfireServer gemfireServer;

	@Before
	public void initialise() {
		gemfireServer = new GemfireServer(SERVER_PORT);
	}

	@Test public void
	serverIsNotRunning_WhenServerIsNotStarted() {
		assertThat(gemfireServer.isRunning(), is(false));
	}

	@Test public void
	serverIsRunning_WhenServerIsStarted() throws IOException {
		gemfireServer.start();

		assertThat(gemfireServer.isRunning(), is(true));
	}

	@Test public void
	serverIsNotRunning_WhenServerIsStartedThenStopped() throws IOException {
		gemfireServer.start();
		gemfireServer.stop();

		assertThat(gemfireServer.isRunning(), is(false));
	}
}