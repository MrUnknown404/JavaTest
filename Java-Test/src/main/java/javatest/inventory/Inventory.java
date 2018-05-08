package main.java.javatest.inventory;

import java.util.ArrayList;
import java.util.List;

import main.java.javatest.Main;
import main.java.javatest.entity.EntityItem;
import main.java.javatest.items.ItemStack;
import main.java.javatest.items.Slot;

public abstract class Inventory {
	
	protected List<ItemStack> items;
	protected List<Slot> slotsList = new ArrayList<Slot>();
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
		
		for (int sy = 0; sy < slotsY; sy++) {
			for (int sx = 0; sx < slotsX; sx++) {
				if (sy == 0) {
					slotsList.add(new Slot(4 + ((sx * 32) + (sx * 12)), 4 + ((sy * 32) + (sy * 12)), sx + (sy * 10)));
				} else {
					slotsList.add(new Slot(4 + ((sx * 32) + (sx * 12)), 8 + ((sy * 32) + (sy * 12)), sx + (sy * 10)));
				}
			}
		}
	}
	
	public int findItemInt(ItemStack stack) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == stack) {
				return i;
			}
		}
		return 0;
	}
	
	public ItemStack findItem(ItemStack item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).equals(item) && items.get(i).getCount() < item.getItem().getMaxStack()) {
				return items.get(i);
			}
		}
		return null;
	}
	
	public void addItem(ItemStack item, EntityItem ei) {
		ItemStack it = findItem(item);
		if (it == null) {
			for (int i = 0; i < slots; i++) {
				if (items.get(i).equals(ItemStack.EMPTY)) {
					items.set(i, item);
					Main.getWorldHandler().getWorld().removeObjectAll(ei, false);
					break;
				}
			}
		} else if (it.getCount() < item.getItem().getMaxStack()) {
			for (int i = 0; i < slots; i++) {
				if (items.get(i).equals(it)) {
					if (items.get(i).getCount() < item.getItem().getMaxStack()) {
						if (item.getCount() + it.getCount() > item.getItem().getMaxStack()) {
							int itc = it.getCount() + item.getCount() - item.getItem().getMaxStack();
							it.setCount(item.getItem().getMaxStack());
							addItem(new ItemStack(itc, it.getItem()));
						} else {
							it.increaseCount(item.getCount());
						}
						items.set(i, it);
						Main.getWorldHandler().getWorld().removeObjectAll(ei, false);
						break;
					}
				}
			}
		}
	}
	
	public void addItem(ItemStack item) {
		ItemStack it = findItem(item);
		if (it == null) {
			for (int i = 0; i < slots; i++) {
				if (items.get(i).equals(ItemStack.EMPTY)) {
					items.set(i, item);
					break;
				}
			}
		} else if (it.getCount() < item.getItem().getMaxStack()) {
			for (int i = 0; i < slots; i++) {
				if (items.get(i).equals(it)) {
					if (items.get(i).getCount() < item.getItem().getMaxStack()) {
						if (item.getCount() + it.getCount() > item.getItem().getMaxStack()) {
							int itc = it.getCount() + item.getCount() - item.getItem().getMaxStack();
							it.setCount(item.getItem().getMaxStack());
							addItem(new ItemStack(itc, it.getItem()));
						} else {
							it.increaseCount(item.getCount());
						}
						items.set(i, it);
						break;
					}
				}
			}
		}
	}
	
	public void addItemTo(ItemStack item, int slot) {
		ItemStack it = findItem(item);
		if (it == null) {
			if (items.get(slot).equals(ItemStack.EMPTY)) {
				items.set(slot, item);
			}
		} else if (!items.get(slot).equals(it)) {
			items.set(slot, item);
		}
	}
	
	public void combindItemTo(ItemStack item, int slot) {
		ItemStack it = items.get(slot);
		if (it == null) {
			return;
		}
		if (items.get(slot).equals(it)) {
			if (items.get(slot).getCount() < item.getItem().getMaxStack()) {
				if (item.getCount() + it.getCount() > item.getItem().getMaxStack()) {
					int itc = it.getCount() + item.getCount() - item.getItem().getMaxStack();
					
					it.setCount(item.getItem().getMaxStack());
					Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse = new ItemStack(itc, it.getItem());
				} else {
					it.setCount(item.getCount() + it.getCount());
					Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse = null;
				}
			}
		}
	}
	
	public List<ItemStack> getItems() {
		return items;
	}
	
	public List<Slot> getSlotsList() {
		return slotsList;
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
	
	public boolean canPickup(ItemStack item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).equals(item) && items.get(i).getCount() < item.getItem().getMaxStack()) {
				return true;
			} else if (items.get(i).equals(ItemStack.EMPTY)) {
				return true;
			}
		}
		return false;
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
