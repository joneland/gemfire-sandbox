package com.sandbox.gemfire.server.item;

import static java.lang.String.format;

public class Grocery implements CacheItem {
	private static final long serialVersionUID = -1145880717859373291L;

	private int id;
	private String name;
	private String price;

	public Grocery(int id, String name, String price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public String toString() {
		return format("id: %d, name: %s, price: %s", id, name, price);
	}
}