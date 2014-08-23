package com.sandbox.gemfire;

import static com.sandbox.gemfire.CacheRegion.EMPLOYEES;
import static com.sandbox.gemfire.CacheRegion.GROCERIES;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GemfireServerTest {
	private static final int SERVER_PORT = 10000;

	private GemfireServer gemfireServer;

	@Before public void
	initialise() {
		gemfireServer = new GemfireServer(SERVER_PORT, EMPLOYEES, GROCERIES);
	}

	@After public void
	tearDown() {
		gemfireServer.stop();
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

	@Test public void
	printsGroceryDetails_WhenCacheIsPopulatedWithGrocery() throws IOException {
		gemfireServer.start();

		gemfireServer.populate(GROCERIES, new Grocery(101, "Apple", "£0.30"));

		assertThat(gemfireServer.print(GROCERIES), is("id: 101, name: Apple, price: £0.30\n"));
	}

	@Test public void
	printsEmployeeDetails_WhenCacheIsPopulatedWithEmployee() throws IOException {
		gemfireServer.start();

		gemfireServer.populate(EMPLOYEES, new Employee(501, "Jon", "Eland", "Cashier"));

		assertThat(gemfireServer.print(EMPLOYEES), is("id: 501, firstName: Jon, surname: Eland, role: Cashier\n"));
	}

	@Test public void
	printsMultipleGroceryDetails_WhenCacheIsPopulatedWithMultipleGroceries() throws IOException {
		gemfireServer.start();

		gemfireServer.populate(GROCERIES, new Grocery(101, "Apple", "£0.30"));
		gemfireServer.populate(GROCERIES, new Grocery(102, "Banana", "£0.15"));

		assertThat(gemfireServer.print(GROCERIES), is("id: 102, name: Banana, price: £0.15\nid: 101, name: Apple, price: £0.30\n"));
	}
}