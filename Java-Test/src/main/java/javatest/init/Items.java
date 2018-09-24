package main.java.javatest.init;

import java.util.ArrayList;
import java.util.List;

import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.items.ItemBlock;
import main.java.javatest.items.ItemStick;
import main.java.javatest.items.util.Item;
import main.java.javatest.items.util.ItemPickaxe;
import main.java.javatest.items.util.ItemSword;

public class Items {
	public static List<Item> items = new ArrayList<Item>();
	
	public static final Item STICK = new ItemStick();
	public static final Item SWORD = new ItemSword("sword", 32, 10, 100f, 1f);
	public static final Item PICKAXE = new ItemPickaxe("pickaxe", 32, 2, 1f, 10f);
	
	public Items() {
		for (int i = 0; i < Blocks.getBlocks().size(); i++) {
			BlockProperties bp = Blocks.getBlocks().get(i);
			if (bp != BlockProperties.AIR) {
				items.add(new ItemBlock(bp));
			}
		}
	}
	
	public static Item getItemFromBlock(BlockProperties block) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) instanceof ItemBlock && ((ItemBlock) items.get(i)).getBlock() == block) {
				return items.get(i);
			}
		}
		return null;
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
