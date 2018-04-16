package main.java.javatest.entity;

import main.java.javatest.util.GameObject;
import main.java.javatest.util.handlers.ObjectHandler;

public class EntityLiving extends Entity {

	protected boolean isDead = false;
	private double moveDirX = 0;
	
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
		
		if (moveDirX != 0) {
			GameObject o = null, o2 = null;
			if (moveDirX > 0) {
				o = getRight(-moveDirX);
			} else if (moveDirX < 0) {
				o2 = getLeft(-moveDirX);
			}
			
			if ((o != null || o2 != null) && doCollision) {
				if (o != null) {
					double x = o.getPositionX() - getPositionX();
					if (x > moveDirX) {
						addPositionX(x - width);
					}
				} else if (o2 != null) {
					addPositionX(o2.getPositionX() - getPositionX() + o2.width);
				}
			} else {
				addPositionX(moveDirX);
			}
		}
	}
	
	/** Game Ticks that occurs while alive */
	public void gameTickAlive() {
		
	}
	
	/** Get move direction X */
	public double getMoveDirX() {
		return moveDirX;
	}
	
	/** Gets isDead */
	public boolean getIsDead() {
		return isDead;
	}
	
	/** Set move direction X */
	public void setMoveDirX(double x) {
		moveDirX = x;
	}
	
	/** Sets isDead */
	public void setIsDead(boolean bool) {
		isDead = bool;
	}
	
	@Override
	public void killEntity() {
		isDead = true;
		ObjectHandler.removeObject(this); //temp
	}
}