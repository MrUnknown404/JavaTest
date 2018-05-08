package main.java.javatest.init;

import java.util.ArrayList;
import java.util.List;

import main.java.javatest.items.Item;
import main.java.javatest.items.Item.ItemType;

public class Items {
	public static List<Item> items = new ArrayList<Item>();
	
	public static final Item STICK = new Item("stick", 999, 0, Item.ItemType.item);
	public static final Item PICKAXE = new Item("pickaxe", 1, 10, Item.ItemType.tool);
	
	public Items() {
		for (int i = 0; i < Blocks.EnumBlocks.values().length; i++) {
			if (Blocks.EnumBlocks.getNumber(i) != Blocks.EnumBlocks.air) {
				new Item(Blocks.EnumBlocks.getNumber(i).toString(), 999, 0, ItemType.block);//.addThis();
			}
		}
	}
	
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
