package main.java.javatest.entity;

import java.awt.Graphics;

import main.java.javatest.blocks.Block;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.handlers.ObjectHandler;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2d;

public class Entity extends GameObject {
	
	private static final double GRAVITY = 1.75; //temp
	private static final double FRICTION = 0.2;
	private static final double MAX_FALL_SPEED = (GRAVITY * GRAVITY) * ((GRAVITY * GRAVITY) * (GRAVITY * GRAVITY));
	
	public boolean canJump = false;
	protected boolean doCollision = true;
	private double gravityY = 0;
	private boolean doGravity = true;
	private Vec2d velocity = new Vec2d();
	
	public Entity(double x, double y, int width, int height) {
		super(x, y, width, height);
	}
	
	@Override
	public void tick() {
		super.tick();
		doVelocity();
	}
	
	@Override
	public void gameTick() {
		
	}
	
	@Override
	public void render(Graphics g) {
		
	}
	
	/** Gets what's below the entity plus the argument */
	public GameObject getBelow(double y) {
		for (int i = 0; i < ObjectHandler.getObjectsActive().size(); i++) {
			GameObject obj = ObjectHandler.getObjectsActive().get(i);
			if (obj instanceof Block && obj != this) {
				Block tObj = (Block) obj;
				if (tObj.getIsActive() && tObj.getBlockType().getHasCollision()) {
					if (getBoundsBottom().intersects(tObj.getBoundsTop().x, tObj.getBoundsTop().y - GRAVITY + y, tObj.getBoundsTop().getWidth(), tObj.getBoundsTop().getHeight())) {
						return tObj;
					}
				}
			}
		}
		return null;
	}

	/** Gets what's above the entity plus the argument */
	public GameObject getAbove(double y) {
		for (int i = 0; i < ObjectHandler.getObjectsActive().size(); i++) {
			GameObject obj = ObjectHandler.getObjectsActive().get(i);
			if (obj instanceof Block && obj != this) {
				Block tObj = (Block) obj;
				if (tObj.getIsActive() && tObj.getBlockType().getHasCollision()) {
					if (getBoundsTop().intersects(tObj.getBoundsBottom().x, tObj.getBoundsBottom().y - GRAVITY + y, tObj.getBoundsBottom().getWidth(), tObj.getBoundsBottom().getHeight())) {
						return tObj;
					}
				}
			}
		}
		return null;
	}
	
	/** Gets what's left the entity plus the argument */
	public GameObject getLeft(double x) {
		for (int i = 0; i < ObjectHandler.getObjectsActive().size(); i++) {
			GameObject obj = ObjectHandler.getObjectsActive().get(i);
			if (obj instanceof Block && obj != this) {
				Block tObj = (Block) obj;
				if (tObj.getIsActive() && tObj.getBlockType().getHasCollision()) {
					if (getBoundsLeft().intersects(tObj.getBoundsRight().x + x, tObj.getBoundsRight().y, tObj.getBoundsRight().getWidth(), tObj.getBoundsRight().getHeight())) {
						return tObj;
					}
				}
			}
		}
		return null;
	}
	
	/** Gets what's right the entity plus the argument */
	public GameObject getRight(double x) {
		for (int i = 0; i < ObjectHandler.getObjectsActive().size(); i++) {
			GameObject obj = ObjectHandler.getObjectsActive().get(i);
			if (obj instanceof Block && obj != this) {
				Block tObj = (Block) obj;
				if (tObj.getIsActive() && tObj.getBlockType().getHasCollision()) {
					if (getBoundsRight().intersects(tObj.getBoundsLeft().x + x, tObj.getBoundsLeft().y, tObj.getBoundsLeft().getWidth(), tObj.getBoundsLeft().getHeight())) {
						return tObj;
					}
				}
			}
		}
		return null;
	}
	
	public void doVelocity() {
		if (doGravity) {
			GameObject obj = getBelow(0);
			if (obj != null && !canJump) {
				setPositionY(obj.getPositionY() - height);
				setVelocityY(0); //fix this
				setGravityY(0);
				canJump = true;
			} else if (canJump && obj == null) {
				canJump = false;
			}
			
			if (!canJump) {
				addGravityY(GRAVITY / (GRAVITY * 2));
				if (getGravityY() > MAX_FALL_SPEED) {
					setGravityY(MAX_FALL_SPEED);
				}
			}
		}
		
		if (getVelocityX() != 0) {
			GameObject o = getLeft(-getVelocityX());
			GameObject o2 = getRight(-getVelocityX());
			if ((o != null || o2 != null) && doCollision) {
				if (o != null) {
					double y = o.getPositionX() - getPositionX();
					if (y > getVelocityX()) {
						addPositionX(getPositionX() - o.getPositionX());
					}
				} else if (o2 != null) {
					double y = o2.getPositionX() - getPositionX();
					if (y < getVelocityX()) {
						addPositionX(o2.getPositionX() - getPositionX() + o2.width);
					}
				}
			} else {
				addPositionX(getVelocityX());
			}
			
			if (getVelocityX() > 0) {
				setVelocityX(MathHelper.clamp(getVelocityX() - FRICTION, 0, Double.MAX_VALUE));
			} else if (getVelocityX() < 0) {
				setVelocityX(MathHelper.clamp(getVelocityX() + FRICTION, -Double.MAX_VALUE, 0));
			}
		}
		
		if (getVelocityY() != 0) {
			GameObject o = getBelow(-getVelocityY());
			GameObject o2 = getAbove(-getVelocityY());
			if ((o != null || o2 != null) && doCollision) {
				if (o != null) {
					double y = o.getPositionY() - getPositionY();
					if (y > getVelocityY()) {
						addPositionY(y - height);
					}
				} else if (o2 != null) {
					double y = o2.getPositionY() - getPositionY();
					if (y < getVelocityY()) {
						addPositionY(y + o2.height);
						setVelocityY(0);
					}
				}
			} else {
				addPositionY(getVelocityY());
			}
			
			if (getVelocityY() > 0) {
				setVelocityY(MathHelper.clamp(getVelocityY() - FRICTION, 0, Double.MAX_VALUE));
			} else if (getVelocityY() < 0) {
				setVelocityY(MathHelper.clamp(getVelocityY() + FRICTION, -Double.MAX_VALUE, 0));
			}
		}
		
		if (getGravityY() != 0) {
			GameObject o = getBelow(-getGravityY());
			if (o != null && this instanceof EntityPlayer) {
				double y = o.getPositionY() - getPositionY();
				if (y > getGravityY()) {
					addPositionY(y - height);
				}
			} else {
				addPositionY(getGravityY());
			}
			
			if (getGravityY() > 0) {
				setGravityY(MathHelper.clamp(getGravityY() - FRICTION, 0, Double.MAX_VALUE));
			} else if (getGravityY() < 0) {
				setGravityY(MathHelper.clamp(getGravityY() + FRICTION, -Double.MAX_VALUE, 0));
			}
		}
	}
	
	/** Adds to the entities velocity */
	public void addVelocity(double x, double y) {
		velocity = velocity.add(velocity.x + x, velocity.y + y);
	}
	
	/** Adds to the entities velocity */
	public void addVelocity(Vec2d vec) {
		velocity = velocity.add(velocity.x + vec.x, velocity.y + vec.y);
	}
	
	/** Adds to the entities X velocity */
	public void addVelocityX(double x) {
		velocity.x += x;
	}
	
	/** Adds to the entities Y velocity */
	public void addVelocityY(double y) {
		velocity.y += y;
	}
	
	/** Sets the entities velocity */
	public void setVelocity(double x, double y) {
		velocity = new Vec2d(x, y);
	}
	
	/** Sets the entities velocity */
	public void setVelocity(Vec2d vec) {
		velocity = vec;
	}
	
	/** Sets the entities X velocity */
	public void setVelocityX(double x) {
		velocity.x = x;
	}
	
	/** Sets the entities Y velocity */
	public void setVelocityY(double y) {
		velocity.y = y;
	}
	
	/** Gets the entities velocity */
	public Vec2d getVelocity() {
		return velocity;
	}
	
	private void setGravityY(double amount) {
		gravityY = amount;
	}
	
	public double getGravityY() {
		return gravityY;
	}
	
	private void addGravityY(double amount) {
		gravityY += amount;
	}
	
	/** Gets the entities X velocity */
	public double getVelocityX() {
		return velocity.x;
	}
	
	/** Gets the entities Y velocity */
	public double getVelocityY() {
		return velocity.y;
	}
	
	/** Disables gravity for this entity */
	protected void disableGravity() {
		doGravity = false;
	}
	
	/** Disables collision for this entity */
	protected void disableCollision() {
		doCollision = false;
	}
}
