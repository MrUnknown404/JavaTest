package main.java.javatest.entity.entityliving;

import java.awt.Rectangle;

import main.java.javatest.Main;
import main.java.javatest.blocks.Block;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.inventory.PlayerInventory;
import main.java.javatest.util.math.MathHelper;

public class EntityPlayer extends EntityLiving {
	
	private PlayerInventory inventory = new PlayerInventory();
	public int direction = 1;
	
	public EntityPlayer(double x, double y) {
		super(x, y, 24, 44, EntityProperties.PLAYER);
	}
	
	@Override
	public void gameTickAlive() {
		super.gameTick();
		if (getPositionY() > (Main.getWorldHandler().getWorld().getWorldInfo().worldHeight * 3) * Block.getBlockSize()) {
			setPositionY(-44 - (-15 * -Block.getBlockSize()));
		}
	}
	
	/** Returns the player's inventory */
	public PlayerInventory getInventory() {
		return inventory;
	}
	
	/** Returns the entities magnet bounds */
	public Rectangle getMagnetBounds() {
		return new Rectangle(MathHelper.floor(getPositionX()) - 32, MathHelper.floor(getPositionY()) - 32, width + 64, height + 64);
	}
	
	/** Returns the entities pickup bounds */
	public Rectangle getPickupBounds() {
		return new Rectangle(MathHelper.floor(getPositionX()) - 4, MathHelper.floor(getPositionY()) - 4, width + 8, height + 8);
	}
	
	/** Returns the entities interaction bounds */
	public Rectangle getInteractionBounds() {
		return new Rectangle(MathHelper.floor(getPositionX()) - 64, MathHelper.floor(getPositionY()) - 64, width + 128, height + 128);
	}
}
