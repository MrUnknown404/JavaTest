package main.java.javatest.entity;

import main.java.javatest.util.handlers.ObjectHandler;
import main.java.javatest.util.math.Vec2d;

public class EntityLiving extends Entity {

	protected boolean isDead = false;
	private Vec2d moveDir = new Vec2d();
	
	public EntityLiving(double x, double y) {
		super(x, y);
	}
	
	@Override
	public void tickAlive() {
		if (isDead) {
			return;
		}
		doVelocity();
		
		if (moveDir.x != 0) {
			addPositionX(moveDir.x);
		}
		if (moveDir.y != 0) {
			addPositionY(moveDir.y);
		}
	}
	
	@Override
	public void gameTickAlive() {
		
	}
	
	/** Get move direction */
	public Vec2d getMoveDir() {
		return moveDir;
	}
	
	/** Get move direction X */
	public double getMoveDirX() {
		return moveDir.x;
	}
	
	/** Get move direction Y */
	public double getMoveDirY() {
		return moveDir.y;
	}
	
	/** Gets isDead */
	public boolean getIsDead() {
		return isDead;
	}
	
	/** Set move direction */
	public void setMoveDir(Vec2d vec) {
		moveDir = vec;
	}
	
	/** Set move direction X */
	public void setMoveDirX(double x) {
		moveDir.x = x;
	}
	
	/** Set move direction Y */
	public void setMoveDirY(double y) {
		moveDir.y = y;
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