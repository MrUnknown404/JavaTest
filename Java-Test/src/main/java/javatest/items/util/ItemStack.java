package main.java.javatest.items.util;

import main.java.javatest.Main;
import main.java.javatest.util.math.MathHelper;

public class ItemStack {
	public static final ItemStack EMPTY = new ItemStack(0, new ItemEmpty());
	
	private int count;
	private Item item;
	
	public ItemStack(int count, Item item) {
		this.count = count;
		this.item = item;
	}
	
	public ItemStack(Item item) {
		this.count = 1;
		this.item = item;
	}
	
	public void decreaseCount() {
		count--;
		if (count == 0) {
			item = ItemStack.EMPTY.getItem();
			if (Main.getWorldHandler().getPlayer().getInventory().getItemInMouse() == this) {
				Main.getWorldHandler().getPlayer().getInventory().setItemInMouse(null);
			}
		}
	}
	
	public void increaseCount() {
		count = MathHelper.clamp(count + 1, 0, item.getMaxStack());
	}
	
	public void increaseCount(int i) {
		count = MathHelper.clamp(count + i, 0, item.getMaxStack());
	}
	
	public int getCount() {
		return count;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setCount(int i) {
		count = MathHelper.clamp(i, 0, item.getMaxStack());
		if (count == 0) {
			item = ItemStack.EMPTY.getItem();
			if (Main.getWorldHandler().getPlayer().getInventory().getItemInMouse() == this) {
				Main.getWorldHandler().getPlayer().getInventory().setItemInMouse(null);
			}
		}
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemStack) {
			if (((ItemStack) obj).getItem().equals(getItem())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + getItem().getName() + ", " + count + ")";
	}
}
