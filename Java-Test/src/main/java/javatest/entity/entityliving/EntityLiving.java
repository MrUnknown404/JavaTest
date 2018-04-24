package main.java.javatest.entity.entityliving;

import main.java.javatest.entity.Entity;

public class EntityLiving extends Entity {

	protected boolean isDead = false;
	
	public EntityLiving(double x, double y, int width, int height) {
		super(x, y, width, height);
	}
	
	@Override
	public void tick() {
		
	}
	
	/** Ticks that occur while alive */
	public void tickAlive() {
		if (isDead) {
			return;
		}
		doVelocity();
	}
	
	/** Game Ticks that occurs while alive */
	public void gameTickAlive() {
		
	}
	
	/** Gets isDead */
	public boolean getIsDead() {
		return isDead;
	}
	
	/** Sets isDead */
	public void setIsDead(boolean bool) {
		isDead = bool;
	}
	
	/** Kills the entity */
	public void killEntity() {
		isDead = true;
	}
}