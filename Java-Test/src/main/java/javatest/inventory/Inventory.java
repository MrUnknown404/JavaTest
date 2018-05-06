package main.java.javatest.inventory;

import java.util.ArrayList;
import java.util.List;

import main.java.javatest.Main;
import main.java.javatest.entity.EntityItem;
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
	
	public ItemStack findItem(ItemStack item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).equals(item) && items.get(i).getCount() < ItemStack.getMaxStack()) {
				return items.get(i);
			}
		}
		return null;
	}
	
	public void addItem(ItemStack item, EntityItem ei) {
		ItemStack it = Main.getWorldHandler().getWorld().getPlayer().getInventory().findItem(item);
		if (it == null) {
			for (int i = 0; i < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlots(); i++) {
				if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).equals(ItemStack.EMPTY)) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().set(i, item);
					Main.getWorldHandler().getWorld().removeObjectAll(ei, false);
					break;
				}
			}
		} else if (it.getCount() < ItemStack.getMaxStack()) {
			for (int i = 0; i < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlots(); i++) {
				if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).equals(it)) {
					it.addCount(item.getCount());
					Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().set(i, it);
					Main.getWorldHandler().getWorld().removeObjectAll(ei, false);
					break;
				}
			}
		}
	}
	
	public void addItem(ItemStack item) {
		ItemStack it = Main.getWorldHandler().getWorld().getPlayer().getInventory().findItem(item);
		if (it == null) {
			for (int i = 0; i < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlots(); i++) {
				if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).equals(ItemStack.EMPTY)) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().set(i, item);
					break;
				}
			}
		} else if (it.getCount() < ItemStack.getMaxStack()) {
			for (int i = 0; i < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlots(); i++) {
				if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).equals(it)) {
					it.addCount(item.getCount());
					Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().set(i, it);
					break;
				}
			}
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
