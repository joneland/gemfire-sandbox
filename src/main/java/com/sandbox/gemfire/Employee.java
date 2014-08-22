package com.sandbox.gemfire;

import java.io.Serializable;

public class Employee implements Serializable, CacheItem {
	private static final long serialVersionUID = 5267646543432714142L;

	private int id;
	private String firstName;
	private String surname;
	private String role;

	public Employee(int id, String firstName, String surname, String role) {
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSurname() {
		return surname;
	}

	public String getRole() {
		return role;
	}

	public String toString() {
		return String.format("id: %d, firstName: %s, surname: %s, role: %s", id, firstName, surname, role);
	}
}