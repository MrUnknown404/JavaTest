package main.java.javatest.init;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import main.java.javatest.items.Item;

public class Items {
	public static List<Item> items = new ArrayList<Item>();
	
	public static final Item STICK = new Item(EnumItems.stick.toString());
	
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
	
	public static int findItemInt(Item item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == item) {
				return i;
			}
		}
		return 0;
	}
	
	public static Item getRandomItem() {
		return items.get(ThreadLocalRandom.current().nextInt(1, items.size()));
	}
	
	public enum EnumItems {
		stick(0);
		
		private final int fId;
		
		private EnumItems(int id) {
			fId = id;
		}
	
		public static EnumItems getNumber(int id) {
			for (EnumItems type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
