package main.java.javatest.entity.entityliving;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import main.java.javatest.Main;
import main.java.javatest.entity.Entity;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.util.TickableGameObject;
import main.java.javatest.util.math.MathHelper;

public abstract class EntityLiving extends Entity {

	public int direction = 1;
	protected int maxInvincibilityTime, invincibilityTime;
	protected boolean isDead, wasHit;
	protected EntityLiving attacker;
	private double moveDirX = 0, jumpY = 0;
	
	public EntityLiving(double x, double y, int width, int height, int maxInvincibilityTime, EntityProperties type) {
		super(x, y, width, height, type);
		this.maxInvincibilityTime = maxInvincibilityTime;
	}
	
	@Override
	public void tick() {
		if (invincibilityTime > 0) {
			invincibilityTime--;
		}
	}
	
	protected void hit(float damage, int critChance, EntityLiving attacker) {
		if (invincibilityTime == 0 && wasHit) {
			wasHit = false;
			if (new Random().nextBoolean()) {
				damage = (float) (damage + ThreadLocalRandom.current().nextDouble((double) damage / 8));
			} else {
				damage = (float) (damage - ThreadLocalRandom.current().nextDouble((double) damage / 8));
			}
			
			this.attacker = attacker;
			damage = (float) MathHelper.roundTo(damage, 2);
			
			if (new Random().nextInt(100) < critChance) {
				onHit(damage * 2, true);
				knockback(attacker, true);
			} else {
				onHit(damage, false);
				knockback(attacker, false);
			}
			invincibilityTime = maxInvincibilityTime;
		}
	}
	
	protected void knockback(EntityLiving attacker, boolean wasCrit) {
		if (wasCrit) {
			if (getEntityProperties().getKnockback() != 0) {
				if (attacker.direction == 1) {
					addVelocity(getEntityProperties().getKnockback() * 2, -getEntityProperties().getKnockback() * 2);
				} else if (attacker.direction == -1) {
					addVelocity(-getEntityProperties().getKnockback() * 2, -getEntityProperties().getKnockback() * 2);
				}
			}
		} else {
			if (getEntityProperties().getKnockback() != 0) {
				if (attacker.direction == 1) {
					addVelocity(getEntityProperties().getKnockback(), -getEntityProperties().getKnockback());
				} else if (attacker.direction == -1) {
					addVelocity(-getEntityProperties().getKnockback(), -getEntityProperties().getKnockback());
				}
			}
		}
	}
	
	public abstract void onHit(float damage, boolean isCrit);
	
	@Override
	public void doVelocity() {
		if (getJumpY() != 0) {
			TickableGameObject o = getBelow(-getJumpY());
			TickableGameObject o2 = getAbove(-getJumpY());
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
				setJumpY(MathHelper.clamp(getJumpY() - Main.getWorldHandler().getWorldInfo().friction, 0, Double.MAX_VALUE));
			} else if (getVelocityY() < 0) {
				setJumpY(MathHelper.clamp(getJumpY() + Main.getWorldHandler().getWorldInfo().friction, -Double.MAX_VALUE, 0));
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
			TickableGameObject o = null, o2 = null;
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
	
	/** Kills the entity */
	public void killEntity() {
		isDead = true;
	}
}