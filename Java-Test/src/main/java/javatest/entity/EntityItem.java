package main.java.javatest.entity;

import main.java.javatest.Main;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.items.ItemStack;

public class EntityItem extends Entity {

	private ItemStack item = ItemStack.EMPTY;
	private int timer = 10;
	
	public EntityItem(double x, double y, ItemStack item) {
		super(x, y, 16, 16, EntityProperties.ITEM);
		this.item = item;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (Main.getWorldHandler().getPlayer() == null) {
			return;
		} else if (timer != 0) {
			timer--;
			return;
		}
		
		if (getBoundsAll().intersects(Main.getWorldHandler().getPlayer().getMagnetBounds()) && Main.getWorldHandler().getPlayer().getInventory().canPickup(item)) {
			if (getEntityProperties().getDoGravity() && doGravity) {
				doGravity = false;
			}
			
			if (getPositionX() < Main.getWorldHandler().getPlayer().getPositionX() + (Main.getWorldHandler().getPlayer().getWidth() / 4)) {
				setVelocityX(2);
			} else if (getPositionX() + width > Main.getWorldHandler().getPlayer().getPositionX()) {
				setVelocityX(-2);
			}
			
			if (getPositionY() < Main.getWorldHandler().getPlayer().getPositionY() + (Main.getWorldHandler().getPlayer().getHeight() / 2)) {
				setVelocityY(2);
			} else if (getPositionY() + height > Main.getWorldHandler().getPlayer().getPositionY() + (Main.getWorldHandler().getPlayer().getHeight() / 2)) {
				setVelocityY(-2);
			}
			
			if (getBoundsAll().intersects(Main.getWorldHandler().getPlayer().getPickupBounds())) {
				Main.getWorldHandler().getPlayer().getInventory().addItem(item, this);
			}
		} else if (!doGravity && getEntityProperties().getDoGravity()) {
			doGravity = true;
		}
	}
	
	public ItemStack getItemStack() {
		return item;
	}
}
