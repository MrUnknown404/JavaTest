package main.java.javatest.entity;

import main.java.javatest.util.math.Vec2d;

public class EntityLiving extends Entity {

	protected boolean isDead = false;
	protected Vec2d moveDir = new Vec2d();
	
	public EntityLiving(double x, double y) {
		super(x, y);
	}
	
	@Override
	public void tickAlive() {
		if (this.isDead) {
			return;
		}
		doVelocity();
		
		if (this.moveDir.x != 0) {
			addPositionX(this.moveDir.x);
		}
		if (this.moveDir.y != 0) {
			addPositionY(this.moveDir.y);
		}
	}
	
	/** Get move direction */
	public Vec2d getMoveDir() {
		return this.moveDir;
	}
	
	/** Get move direction X */
	public double getMoveDirX() {
		return this.moveDir.x;
	}
	
	/** Get move direction Y */
	public double getMoveDirY() {
		return this.moveDir.y;
	}
	
	/** Set move direction */
	public void setMoveDir(Vec2d vec) {
		this.moveDir = vec;
	}
	
	/** Set move direction X */
	public void setMoveDirX(double x) {
		this.moveDir.x = x;
	}
	
	/** Set move direction Y */
	public void setMoveDirY(double y) {
		this.moveDir.y = y;
	}
	
	@Override
	public void doVelocity() {
		/*
		if (getVelocity() != Vec2d.ZERO) {
			addPosition(getVelocity());
		}
		/*
		if (getVelocity() != Vec2d.ZERO) {
			addPosition(getVelocity());
			if (getVelocityX() < 0.02 && getVelocityX() > -0.02) {
				setVelocity(0, getVelocityY());
			}
			if (getVelocityY() < 0.02 && getVelocityY() > -0.02) {
				setVelocity(getVelocityX(), 0);
			}
			if (getVelocityX() > 0) {
				setVelocity(velocity.subtract(1, 0));
			} else if (getVelocityX() < 0) {
				setVelocity(velocity.add(1, 0));
			}
			if (getVelocityY() > 0) {
				setVelocity(velocity.subtract(0.0, 1));
			} else if (getVelocityY() < 0) {
				setVelocity(velocity.add(0.0, 1));
			}
		}//*/
	}
	
}