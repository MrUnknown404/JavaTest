package main.java.javatest.init;

import java.util.ArrayList;
import java.util.List;

import main.java.javatest.items.Item;

public class Items {
	public static List<Item> items = new ArrayList<Item>();
	
	public static final Item STICK = new Item("stick", 999, 32, 1, 1f, 1f, Item.ItemType.item);
	public static final Item SWORD = new Item("sword", 1, 32, 10, 100f, 1.25f, Item.ItemType.sword);
	
	public static Item findItem(String name) {
		if (name.equals("") || name == null) {
			return items.get(0);
		}
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equals(name)) {
				return items.get(i);
			}
		}
		return null;
	}
}
