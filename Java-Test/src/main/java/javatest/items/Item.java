package main.java.javatest.items;

import main.java.javatest.init.Items;

public class Item {
	private String name;
	private int id = 0;
	
	public Item(String name) {
		this.name = name;
	}
	
	public void addThis() {
		Items.items.add(this);
		id = Items.findItemInt(this);
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			if (((Item) obj).name.equals(name) && ((Item) obj).id == id) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + name + ")";
	}
}
