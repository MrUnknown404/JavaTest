package main.java.javatest.entity.entityliving;

import main.java.javatest.blocks.Block;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.world.World;

public class EntityPlayer extends EntityLiving {
	
	public EntityPlayer(double x, double y) {
		super(x, y, 24, 44, EntityProperties.PLAYER);
	}
	
	@Override
	public void gameTickAlive() {
		super.gameTick();
		if (getPositionY() > (World.getWorldHeight() * 3) * Block.getBlockSize()) {
			setPositionY(-44 - (-15 * -Block.getBlockSize()));
		}
	}
}
