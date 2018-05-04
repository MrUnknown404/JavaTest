package main.java.javatest.inventory;

import java.util.Random;

import main.java.javatest.init.EnumItems;
import main.java.javatest.items.ItemStack;
import main.java.javatest.util.Console;
import main.java.javatest.util.math.MathHelper;

public class PlayerInventory extends Inventory {

	private boolean isInventoryOpen = false;
	private int selectedSlot = 0;
	
	public PlayerInventory() {
		super(10, 3);
		
		for (int i = 0; i < slots; i++) {
			if (new Random().nextBoolean()) {
				items.set(i, new ItemStack(i + 1, EnumItems.findItem(EnumItems.getRandomItem().getName())));
			}
		}
		
		if (items.size() > slots) {
			Console.print(Console.WarningType.FatalError + "Inventory is bigger then the maximum size!");
		}
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
		selectedSlot = number;
		selectedSlot = MathHelper.clamp(number, 0, slotsX - 1);
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
