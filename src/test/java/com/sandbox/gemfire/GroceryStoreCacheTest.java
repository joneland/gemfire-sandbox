package com.sandbox.gemfire;

import static com.sandbox.gemfire.CacheRegion.EMPLOYEES;
import static com.sandbox.gemfire.CacheRegion.GROCERIES;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GroceryStoreCacheTest {
	private static final int SERVER_PORT = 10000;

	private GroceryStoreCache groceryStoreCache;

	@Before public void
	initialise() {
		groceryStoreCache = new GroceryStoreCache(SERVER_PORT, EMPLOYEES, GROCERIES);
	}

	@After public void
	tearDown() {
		groceryStoreCache.stop();
	}

	@Test public void
	cacheIsNotRunning_WhenCacheIsNotStarted() {
		assertThat(groceryStoreCache.isRunning(), is(false));
	}

	@Test public void
	cacheIsRunning_WhenCacheIsStarted() throws IOException {
		groceryStoreCache.start();

		assertThat(groceryStoreCache.isRunning(), is(true));
	}

	@Test public void
	cacheIsNotRunning_WhenCacheIsStartedThenStopped() throws IOException {
		groceryStoreCache.start();
		groceryStoreCache.stop();

		assertThat(groceryStoreCache.isRunning(), is(false));
	}

	@Test public void
	printsGroceryDetails_WhenCacheIsPopulatedWithGrocery() throws IOException {
		groceryStoreCache.start();

		groceryStoreCache.populate(GROCERIES, new Grocery(101, "Apple", "£0.30"));

		assertThat(groceryStoreCache.print(GROCERIES), is("id: 101, name: Apple, price: £0.30\n"));
	}

	@Test public void
	printsEmployeeDetails_WhenCacheIsPopulatedWithEmployee() throws IOException {
		groceryStoreCache.start();

		groceryStoreCache.populate(EMPLOYEES, new Employee(501, "Jon", "Eland", "Cashier"));

		assertThat(groceryStoreCache.print(EMPLOYEES), is("id: 501, firstName: Jon, surname: Eland, role: Cashier\n"));
	}

	@Test public void
	printsMultipleGroceryDetails_WhenCacheIsPopulatedWithMultipleGroceries() throws IOException {
		groceryStoreCache.start();

		groceryStoreCache.populate(GROCERIES, new Grocery(101, "Apple", "£0.30"));
		groceryStoreCache.populate(GROCERIES, new Grocery(102, "Banana", "£0.15"));

		assertThat(groceryStoreCache.print(GROCERIES), is("id: 102, name: Banana, price: £0.15\nid: 101, name: Apple, price: £0.30\n"));
	}

	@Test public void
	regionIsEmpty_WhenCacheIsPopulatedAndThenCleared() throws IOException {
		groceryStoreCache.start();

		groceryStoreCache.populate(GROCERIES, new Grocery(101, "Apple", "£0.30"));
		groceryStoreCache.clear(GROCERIES);

		assertThat(groceryStoreCache.print(GROCERIES), is(""));
	}
}