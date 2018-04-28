package main.java.javatest.entity.entityliving;

import main.java.javatest.Main;
import main.java.javatest.entity.util.EntityProperties;

public class EntityPlayer extends EntityLiving {
	
	public EntityPlayer(double x, double y) {
		super(x, y, 24, 44, EntityProperties.PLAYER);
	}
	
	@Override
	public void gameTickAlive() {
		super.gameTick();
		if (getPositionY() > Main.HEIGHT * 10) {
			setPositionY(0);
		}
	}
}
