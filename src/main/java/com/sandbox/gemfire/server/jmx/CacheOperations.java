package com.sandbox.gemfire.server.jmx;

import static com.sandbox.gemfire.server.CacheRegion.EMPLOYEES;
import static com.sandbox.gemfire.server.CacheRegion.GROCERIES;

import com.sandbox.gemfire.server.GroceryStoreCache;
import com.sandbox.gemfire.server.item.Employee;
import com.sandbox.gemfire.server.item.Grocery;

public class CacheOperations implements CacheOperationsMBean {
	private GroceryStoreCache cache;

	public CacheOperations(GroceryStoreCache cache) {
		this.cache = cache;
	}

	public void stop() {
		cache.stop();
	}

	public void populateGrocery(int id, String name, String price) {
		cache.populate(GROCERIES, new Grocery(id, name, price));
	}

	public void populateEmployee(int id, String firstName, String surname, String role) {
		cache.populate(EMPLOYEES, new Employee(id, firstName, surname, role));
	}

	public void clearGroceries() {
		cache.clear(GROCERIES);
	}

	public void clearEmployees() {
		cache.clear(EMPLOYEES);
	}

	public String printGroceries() {
		return cache.print(GROCERIES);
	}

	public String printEmployees() {
		return cache.print(EMPLOYEES);
	}
}