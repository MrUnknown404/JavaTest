package main.java.javatest.entity.entityliving;

import java.awt.Rectangle;

import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.inventory.PlayerInventory;
import main.java.javatest.items.util.Item;
import main.java.javatest.util.math.MathHelper;

public class EntityPlayer extends EntityLiving {
	
	private PlayerInventory inventory = new PlayerInventory();
	private final double speed = 3;
	
	public EntityPlayer(double x, double y) {
		super(x, y, 24, 44, 30, EntityProperties.PLAYER);
	}
	
	@Override
	public void onHit(float damage, boolean isCrit) {
		
	}
	
	public double getSpeed() {
		return speed;
	}
	
	/** Returns the player's inventory */
	public PlayerInventory getInventory() {
		return inventory;
	}
	
	/** Returns the player's magnet bounds */
	public Rectangle getMagnetBounds() {
		return new Rectangle(MathHelper.floor(getPositionX()) - 32, MathHelper.floor(getPositionY()) - 32, width + 64, height + 64);
	}
	
	/** Returns the player's pickup bounds */
	public Rectangle getPickupBounds() {
		return new Rectangle(MathHelper.floor(getPositionX()) - 4, MathHelper.floor(getPositionY()) - 4, width + 8, height + 8);
	}
	
	/** Returns the player's interaction bounds */
	public Rectangle getInteractionBounds() {
		return new Rectangle(MathHelper.floor(getPositionX()) - 64, MathHelper.floor(getPositionY()) - 64, width + 128, height + 128);
	}
	
	/** Returns the player's swing bounds */
	public Rectangle getSwingBounds(Item item, int dir) {
		if (dir == 1) {
			return new Rectangle(MathHelper.floor(getPositionX() + width), MathHelper.floor(getPositionY() - 12), item.getRange(), item.getRange() + 20);
		} else if (dir == -1) {
			return new Rectangle(MathHelper.floor(getPositionX()) - item.getRange(), MathHelper.floor(getPositionY() - 12), item.getRange(), item.getRange() + 20);
		}
		return null;
	}
}
