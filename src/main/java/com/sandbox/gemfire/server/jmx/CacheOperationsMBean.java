package com.sandbox.gemfire.server.jmx;

public interface CacheOperationsMBean {
	void stop();

	void populateGrocery(int id, String name, String price);
	void populateEmployee(int id, String firstName, String surname, String role);

	void clearGroceries();
	void clearEmployees();

	String printGroceries();
	String printEmployees();
}