package main.java.javatest.inventory;

import java.util.ArrayList;
import java.util.List;

import main.java.javatest.items.ItemStack;

public abstract class Inventory {
	
	protected List<ItemStack> items;
	protected int slotsX, slotsY;
	protected final int slots;
	
	public Inventory(int slotsX, int slotsY) {
		this.slotsX = slotsX;
		this.slotsY = slotsY;
		slots = slotsX * slotsY;
		items = new ArrayList<ItemStack>(slots);
		
		for (int i = 0; i < slots; i++) {
			items.add(ItemStack.EMPTY);
		}
	}
	
	public List<ItemStack> getItems() {
		return items;
	}
	
	public int getSlots() {
		return slots;
	}
	
	public int getSlotsX() {
		return slotsX;
	}
	
	public int getSlotsY() {
		return slotsY;
	}
	
	public boolean isFull() {
		List<ItemStack> its = new ArrayList<ItemStack>(slots);
		for (int i = 0; i < items.size(); i++) {
			if (!items.get(i).equals(ItemStack.EMPTY)) {
				its.add(items.get(i));
			}
		}
		
		if (its.size() == slots) {
			return true;
		}
		return false;
	}
}
