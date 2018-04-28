package main.java.javatest.entity;

import main.java.javatest.blocks.Block;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.ObjectHandler;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2d;

public class Entity extends GameObject {
	
	protected static final double FRICTION = 0.2;
	private static final double GRAVITY = 1.75; //temp
	private static final double MAX_FALL_SPEED = (GRAVITY * GRAVITY) * ((GRAVITY * GRAVITY) * (GRAVITY * GRAVITY));
	
	protected EntityProperties type;
	private double gravityY = 0;
	private Vec2d velocity = new Vec2d();
	
	public Entity(double x, double y, int width, int height, EntityProperties type) {
		super(x, y, width, height);
		this.type = type;
	}
	
	@Override
	public void tick() {
		doVelocity();
	}
	
	@Override
	public void gameTick() {
		
	}
	
	/** Gets what's below the entity plus the argument */
	public GameObject getBelow(double y) {
		for (int i = 0; i < ObjectHandler.getObjectsAll().size(); i++) {
			GameObject obj = ObjectHandler.getObjectsAll().get(i);
			if (obj instanceof Block && obj != this) {
				Block tObj = (Block) obj;
				if (tObj.getBlockProperties().getHasCollision()) {
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
		for (int i = 0; i < ObjectHandler.getObjectsAll().size(); i++) {
			GameObject obj = ObjectHandler.getObjectsAll().get(i);
			if (obj instanceof Block && obj != this) {
				Block tObj = (Block) obj;
				if (tObj.getBlockProperties().getHasCollision()) {
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
		for (int i = 0; i < ObjectHandler.getObjectsAll().size(); i++) {
			GameObject obj = ObjectHandler.getObjectsAll().get(i);
			if (obj instanceof Block && obj != this) {
				Block tObj = (Block) obj;
				if (tObj.getBlockProperties().getHasCollision()) {
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
		for (int i = 0; i < ObjectHandler.getObjectsAll().size(); i++) {
			GameObject obj = ObjectHandler.getObjectsAll().get(i);
			if (obj instanceof Block && obj != this) {
				Block tObj = (Block) obj;
				if (tObj.getBlockProperties().getHasCollision()) {
					if (getBoundsRight().intersects(tObj.getBoundsLeft().x + x, tObj.getBoundsLeft().y, tObj.getBoundsLeft().getWidth(), tObj.getBoundsLeft().getHeight())) {
						return tObj;
					}
				}
			}
		}
		return null;
	}
	
	public boolean isGrounded() {
		GameObject obj = getBelow(0);
		if (obj != null && obj instanceof Block) {
			return true;
		}
		return false;
	}
	
	public void doVelocity() {
		if (getVelocityX() != 0) {
			GameObject o = getLeft(-getVelocityX());
			GameObject o2 = getRight(-getVelocityX());
			if ((o != null || o2 != null) && getEntityProperties().getDoCollision()) {
				if (o != null) {
					double y = o.getPositionX() - getPositionX();
					if (y > getVelocityX()) {
						addPositionX(getPositionX() - o.getPositionX());
					}
				} else if (o2 != null) {
					double y = o2.getPositionX() - getPositionX();
					if (y < getVelocityX()) {
						addPositionX(o2.getPositionX() - getPositionX() + o2.getWidth());
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
			if ((o != null || o2 != null) && getEntityProperties().getDoCollision()) {
				if (o != null) {
					double y = o.getPositionY() - getPositionY();
					if (y > getVelocityY()) {
						addPositionY(y - height);
					}
				} else if (o2 != null) {
					double y = o2.getPositionY() - getPositionY();
					if (y < getVelocityY()) {
						addPositionY(y + o2.getHeight());
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
		
		if (getEntityProperties().getDoGravity()) {
			if (getEntityProperties().getDoCollision()) {
				if (isGrounded() && getGravityY() != 0) {
					setPositionY(getBelow(0).getPositionY() - height);
					if (this instanceof EntityPlayer) {
						((EntityPlayer) this).setJumpY(0);
					}
					setGravityY(0);
				}
			}
			
			if (!isGrounded()) {
				addGravityY(GRAVITY / (GRAVITY * 2));
				if (getGravityY() > MAX_FALL_SPEED) {
					setGravityY(MAX_FALL_SPEED);
				}
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
	
	protected void setGravityY(double amount) {
		gravityY = amount;
	}
	
	public double getGravityY() {
		return gravityY;
	}
	
	protected void addGravityY(double amount) {
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
	
	public EntityProperties getEntityProperties() {
		return type;
	}
}
