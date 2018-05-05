package main.java.javatest.items;

import main.java.javatest.Main;
import main.java.javatest.util.math.MathHelper;

public class ItemStack {
	public static final ItemStack EMPTY = new ItemStack(0, new Item("empty"));
	
	private static final int MAX_STACK = 999;
	private int count;
	private Item item;
	
	public ItemStack(int count, Item item) {
		this.count = count;
		this.item = item;
	}
	
	public void decreaseCount() {
		count--;
		if (count == 0) {
			Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().set(Main.getWorldHandler().getWorld().getPlayer().getInventory().findItemInt(this), ItemStack.EMPTY);
		}
	}
	
	public void addCount() {
		count++;
		count = MathHelper.clamp(count, 0, MAX_STACK);
	}
	
	public static int getMaxStack() {
		return MAX_STACK;
	}
	
	public int getCount() {
		return count;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemStack) {
			if (((ItemStack) obj).getCount() == getCount() && ((ItemStack) obj).getItem().equals(getItem())) {
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
