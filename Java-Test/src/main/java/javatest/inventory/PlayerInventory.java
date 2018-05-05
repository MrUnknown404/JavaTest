package main.java.javatest.inventory;

import main.java.javatest.items.ItemStack;
import main.java.javatest.util.math.MathHelper;

public class PlayerInventory extends Inventory {

	private boolean isInventoryOpen = false;
	private int selectedSlot = 0;
	
	public PlayerInventory() {
		super(10, 3);
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

	public int findItemInt(ItemStack stack) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == stack) {
				return i;
			}
		}
		return 0;
	}
}
