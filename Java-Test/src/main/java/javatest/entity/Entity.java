package main.java.javatest.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.handlers.ObjectHandler;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2d;

public class Entity extends GameObject {
	
	private static final double GRAVITY = 1.75; //temp
	private static final double FRICTION = 0.2;
	private static final double MAX_FALL_SPEED = (GRAVITY * GRAVITY) * ((GRAVITY * GRAVITY) * (GRAVITY * GRAVITY));
	
	private double gravityY = 0;
	private boolean doGravity = true;
	public boolean canJump = false;
	private Vec2d velocity = new Vec2d();
	
	public Entity(double x, double y, int width, int height) {
		super(x, y, width, height);
	}
	
	@Override
	public void tick() {
		doVelocity();
	}
	
	@Override
	public void gameTick() {
		
	}
	
	@Override
	public void render(Graphics g) {
		
	}
	
	/** Gets whats immediately below */
	public GameObject getBelow() {
		for (int i = 0; i < ObjectHandler.objects.size(); i++) {
			GameObject obj = ObjectHandler.objects.get(i);
			if (obj != this) {
				if (getBoundsBottom().intersects(new Rectangle(obj.getBounds().x, (int) (obj.getBounds().y - GRAVITY), (int) obj.getBounds().getWidth(), (int) obj.getBounds().getHeight()))) {
					return obj;
				}
			}
		}
		return null;
	}
	
	/** Gets whats below + argument */
	public GameObject getBelow(double y) {
		for (int i = 0; i < ObjectHandler.objects.size(); i++) {
			GameObject obj = ObjectHandler.objects.get(i);
			if (obj != this) {
				if (getBoundsBottom().intersects(new Rectangle(obj.getBounds().x, (int) (obj.getBounds().y - GRAVITY + y), (int) obj.getBounds().getWidth(), (int) obj.getBounds().getHeight()))) {
					return obj;
				}
			}
		}
		return null;
	}
	
	private void setGravityY(double amount) {
		gravityY = amount;
	}
	
	private double getGravityY() {
		return gravityY;
	}
	
	private void addGravityY(double amount) {
		gravityY += amount;
	}
	
	public void doVelocity() {
		if (doGravity) {
			GameObject obj = getBelow();
			if (obj != null && !canJump) {
				setPositionY(obj.getPositionY() - height);
				addVelocityY(-getVelocityY());
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
			addPositionX(getVelocityX());
			if (getVelocityX() > 0) {
				setVelocityX(MathHelper.clamp(getVelocityX() - FRICTION, 0, Double.MAX_VALUE));
			} else if (getVelocityX() < 0) {
				setVelocityX(MathHelper.clamp(getVelocityX() + FRICTION, -Double.MAX_VALUE, 0));
			}
		}
		
		if (getVelocityY() != 0) {
			GameObject o = getBelow(-getVelocityY());
			if (o != null && this instanceof EntityPlayer) {
				double y = o.getPositionY() - getPositionY();
				if (y > getVelocityY()) {
					addPositionY(y - height);
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
	
	/** Gets the entities X velocity */
	public double getVelocityX() {
		return velocity.x;
	}
	
	/** Gets the entities Y velocity */
	public double getVelocityY() {
		return velocity.y;
	}
	
	/** Kills the entity */
	public void killEntity() {
		ObjectHandler.removeObject(this);
	}
	
	/** Disables gravity for this entity */
	protected void disableGravity() {
		doGravity = false;
	}
}
