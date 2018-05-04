package main.java.javatest.items;

import main.java.javatest.init.EnumItems;

public class Item {
	private String name;
	private int id = 0;
	
	public Item(String name) {
		this.name = name;
	}
	
	public void addThis() {
		EnumItems.items.add(this);
		id = EnumItems.findItemInt(this);
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
}
