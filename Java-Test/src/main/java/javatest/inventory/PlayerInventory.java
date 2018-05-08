package main.java.javatest.inventory;

import main.java.javatest.init.Items;
import main.java.javatest.items.ItemStack;
import main.java.javatest.util.math.MathHelper;

public class PlayerInventory extends Inventory {

	private boolean isInventoryOpen = false;
	private int selectedSlot = 0;
	public ItemStack itemInMouse;
	
	public PlayerInventory() {
		super(10, 3);
		addItemTo(new ItemStack(1, Items.PICKAXE), 0);
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
	
	public void setIsInventoryOpen(boolean bool) {
		isInventoryOpen = bool;
	}
	
	public void setSelectedSlot(int number) {
		selectedSlot = MathHelper.clamp(selectedSlot = number, 0, slotsX - 1);
	}
}
