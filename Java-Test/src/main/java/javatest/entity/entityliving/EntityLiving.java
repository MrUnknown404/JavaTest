package main.java.javatest.entity.entityliving;

import main.java.javatest.entity.Entity;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.world.World;

public class EntityLiving extends Entity {

	protected boolean isDead = false;
	private double moveDirX = 0;
	private double jumpY = 0;
	
	public EntityLiving(double x, double y, int width, int height, EntityProperties type) {
		super(x, y, width, height, type);
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	public void doVelocity() {
		if (getJumpY() != 0) {
			GameObject o = getBelow(-getJumpY());
			GameObject o2 = getAbove(-getJumpY());
			if ((o != null || o2 != null) && getEntityProperties().getDoCollision()) {
				if (o != null) {
					double y = o.getPositionY() - getPositionY();
					if (y > getJumpY()) {
						addPositionY(y - height);
					}
				} else if (o2 != null) {
					double y = o2.getPositionY() - getPositionY();
					if (y < getJumpY()) {
						addPositionY(y + o2.getHeight());
						setJumpY(0);
						setGravityY(0);
					}
				}
			} else {
				addPositionY(jumpY);
			}
			
			if (getJumpY() > 0) {
				setJumpY(MathHelper.clamp(getJumpY() - World.getWorldInfo().friction, 0, Double.MAX_VALUE));
			} else if (getVelocityY() < 0) {
				setJumpY(MathHelper.clamp(getJumpY() + World.getWorldInfo().friction, -Double.MAX_VALUE, 0));
			}
		}
		
		super.doVelocity();
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
			
			if ((o != null || o2 != null) && getEntityProperties().getDoCollision()) {
				if (o != null) {
					double x = o.getPositionX() - getPositionX();
					if (x > moveDirX) {
						addPositionX(x - width);
					}
				} else if (o2 != null) {
					addPositionX(o2.getPositionX() - getPositionX() + o2.getWidth());
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
	
	/** Set move direction X */
	public void setMoveDirX(double x) {
		moveDirX = x;
	}
	
	public double getJumpY() {
		return jumpY;
	}
	
	public void setJumpY(double y) {
		jumpY = y;
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