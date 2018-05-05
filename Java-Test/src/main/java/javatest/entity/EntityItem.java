package main.java.javatest.entity;

import main.java.javatest.Main;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.items.ItemStack;

public class EntityItem extends Entity {

	private ItemStack item = ItemStack.EMPTY;
	private int timer = 10;
	
	public EntityItem(double x, double y, ItemStack item) {
		super(x, y, 12, 12, EntityProperties.ITEM);
		this.item = item;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (Main.getWorldHandler().getWorld().getPlayer() == null) {
			return;
		} else if (timer != 0) {
			timer--;
			return;
		}
		
		if (getBoundsAll().intersects(Main.getWorldHandler().getWorld().getPlayer().getMagnetBounds())) {
			if (getEntityProperties().getDoGravity() && doGravity) {
				doGravity = false;
			}
			
			if (getPositionX() < Main.getWorldHandler().getWorld().getPlayer().getPositionX() + (Main.getWorldHandler().getWorld().getPlayer().getWidth() / 4)) {
				setVelocityX(2);
			} else if (getPositionX() + width > Main.getWorldHandler().getWorld().getPlayer().getPositionX()) {
				setVelocityX(-2);
			}
			
			if (getPositionY() < Main.getWorldHandler().getWorld().getPlayer().getPositionY() + (Main.getWorldHandler().getWorld().getPlayer().getHeight() / 2)) {
				setVelocityY(2);
			} else if (getPositionY() + height > Main.getWorldHandler().getWorld().getPlayer().getPositionY() + (Main.getWorldHandler().getWorld().getPlayer().getHeight() / 2)) {
				setVelocityY(-2);
			}
			
			boolean tb = false;
			if (getBoundsAll().intersects(Main.getWorldHandler().getWorld().getPlayer().getPickupBounds())) {
				if (!Main.getWorldHandler().getWorld().getPlayer().getInventory().isFull()) {
					for (int i = 0; i < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlots(); i++) {
						if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).getItem().equals(item.getItem())) {
							Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().set(i, new ItemStack(item.getCount() + Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).getCount(), item.getItem()));
							tb = true;
							break;
						}
					}
					
					if (!tb) {
						for (int i = 0; i < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlots(); i++) {
							if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).equals(ItemStack.EMPTY)) {
								Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().set(i, item);
								break;
							}
						}
					}
					
					Main.getWorldHandler().getWorld().removeObjectAll(this, false);
				}
			}
		} else if (!doGravity && getEntityProperties().getDoGravity()) {
			doGravity = true;
		}
	}
	
	public ItemStack getItemStack() {
		return item;
	}
}
