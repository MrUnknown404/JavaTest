package main.java.javatest.inventory;

import java.util.ArrayList;
import java.util.List;

import main.java.javatest.Main;
import main.java.javatest.client.gui.InventoryHud;
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
					if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).getCount() < ItemStack.getMaxStack()) {
						if (item.getCount() + it.getCount() > ItemStack.getMaxStack()) {
							int itc = it.getCount() + item.getCount() - ItemStack.getMaxStack();
							it.setCount(ItemStack.getMaxStack());
							Main.getWorldHandler().getWorld().getPlayer().getInventory().addItem(new ItemStack(itc, it.getItem()));
						} else {
							it.addCount(item.getCount());
						}
						Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().set(i, it);
						Main.getWorldHandler().getWorld().removeObjectAll(ei, false);
						break;
					}
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
					if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).getCount() < ItemStack.getMaxStack()) {
						if (item.getCount() + it.getCount() > ItemStack.getMaxStack()) {
							int itc = it.getCount() + item.getCount() - ItemStack.getMaxStack();
							it.setCount(ItemStack.getMaxStack());
							Main.getWorldHandler().getWorld().getPlayer().getInventory().addItem(new ItemStack(itc, it.getItem()));
						} else {
							it.addCount(item.getCount());
						}
						Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().set(i, it);
						break;
					}
				}
			}
		}
	}
	
	public void addItemTo(ItemStack item, int slot) {
		ItemStack it = Main.getWorldHandler().getWorld().getPlayer().getInventory().findItem(item);
		if (it == null) {
			if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(slot).equals(ItemStack.EMPTY)) {
				Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().set(slot, item);
			}
		} else if (!Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(slot).equals(it)) {
			Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().set(slot, item);
		}
	}
	
	public void combindItemTo(ItemStack item, int slot) {
		ItemStack it = Main.getWorldHandler().getWorld().getPlayer().getInventory().findItem(item);
		if (it == null) {
			return;
		}
		if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(slot).equals(it)) {
			if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(slot).getCount() < ItemStack.getMaxStack()) {
				if (item.getCount() + it.getCount() > ItemStack.getMaxStack()) {
					int itc = it.getCount() + item.getCount() - ItemStack.getMaxStack();
					
					it.setCount(ItemStack.getMaxStack());
					InventoryHud.itemInHand = new ItemStack(itc, it.getItem());
				} else {
					it.setCount(item.getCount() + it.getCount());
					InventoryHud.itemInHand = null;
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
			if (items.get(i).equals(item) && items.get(i).getCount() < ItemStack.getMaxStack()) {
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
