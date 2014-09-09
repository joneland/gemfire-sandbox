package com.sandbox.gemfire.server;

import static com.sandbox.gemfire.server.CacheRegion.EMPLOYEES;
import static com.sandbox.gemfire.server.CacheRegion.GROCERIES;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sandbox.gemfire.server.jmx.CacheOperations;

public class CacheITest {
	private static final int SERVER_PORT = 10000;

	private GroceryStoreCache cache;
	private CacheOperations cacheOperations;

	@Before public void
	initialise() {
		cache = new GroceryStoreCache(SERVER_PORT, EMPLOYEES, GROCERIES);
		cacheOperations = new CacheOperations(cache);
	}

	@After public void
	tearDown() {
		cache.stop();
	}

	@Test public void
	cacheIsNotRunning_WhenCacheIsNotStarted() {
		assertThat(cache.isRunning(), is(false));
	}

	@Test public void
	cacheIsRunning_WhenCacheIsStarted() throws IOException {
		cache.start();

		assertThat(cache.isRunning(), is(true));
	}

	@Test public void
	cacheIsNotRunning_WhenCacheIsStartedThenStopped() throws IOException {
		cache.start();
		cache.stop();

		assertThat(cache.isRunning(), is(false));
	}

	@Test public void
	printsGroceryDetails_WhenCacheIsPopulatedWithGrocery() throws IOException {
		cache.start();

		cacheOperations.populateGrocery(101, "Apple", "0.30");

		assertThat(cacheOperations.printGroceries(), is("id: 101, name: Apple, price: 0.30\n"));
	}

	@Test public void
	printsEmployeeDetails_WhenCacheIsPopulatedWithEmployee() throws IOException {
		cache.start();

		cacheOperations.populateEmployee(501, "Jon", "Eland", "Cashier");

		assertThat(cacheOperations.printEmployees(), is("id: 501, firstName: Jon, surname: Eland, role: Cashier\n"));
	}

	@Test public void
	printsMultipleGroceryDetails_WhenCacheIsPopulatedWithMultipleGroceries() throws IOException {
		cache.start();

		cacheOperations.populateGrocery(101, "Apple", "0.30");
		cacheOperations.populateGrocery(102, "Banana", "0.15");

		assertThat(cacheOperations.printGroceries(), is("id: 102, name: Banana, price: 0.15\nid: 101, name: Apple, price: 0.30\n"));
	}

	@Test public void
	groceryRegionIsEmpty_WhenCacheIsPopulatedAndThenCleared() throws IOException {
		cache.start();

		cacheOperations.populateGrocery(101, "Apple", "£0.30");
		cacheOperations.clearGroceries();

		assertThat(cacheOperations.printGroceries(), is(""));
	}

	@Test public void
	employeeRegionIsEmpty_WhenCacheIsPopulatedAndThenCleared() throws IOException {
		cache.start();

		cacheOperations.populateEmployee(501, "Jon", "Eland", "Cashier");
		cacheOperations.clearEmployees();

		assertThat(cacheOperations.printEmployees(), is(""));
	}
}