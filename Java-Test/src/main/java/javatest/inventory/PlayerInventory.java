package main.java.javatest.inventory;

import main.java.javatest.init.Items;
import main.java.javatest.items.ItemStack;
import main.java.javatest.util.math.MathHelper;

public class PlayerInventory extends Inventory {

	private boolean isInventoryOpen = false;
	private int selectedSlot = 0;
	private ItemStack itemInMouse;
	
	public PlayerInventory() {
		super(10, 3);
		addItemTo(new ItemStack(1, Items.SWORD), 0);
	}
	
	public ItemStack getSelectedItem() {
		return items.get(selectedSlot);
	}
	
	public boolean getIsInventoryOpen() {
		return isInventoryOpen;
	}
	
	public int getSelectedSlot() {
		return selectedSlot;
	}
	
	public ItemStack getItemInMouse() {
		return itemInMouse;
	}
	
	public void setIsInventoryOpen(boolean bool) {
		isInventoryOpen = bool;
	}
	
	public void setSelectedSlot(int number) {
		selectedSlot = MathHelper.clamp(selectedSlot = number, 0, slotsX - 1);
	}
	
	public void setItemInMouse(ItemStack item) {
		if (item == null) {
			itemInMouse = null;
			return;
		}
		itemInMouse = new ItemStack(item.getCount(), item.getItem());
	}
}
