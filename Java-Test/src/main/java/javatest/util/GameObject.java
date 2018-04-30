package main.java.javatest.util;

import java.awt.Rectangle;

import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2d;

public class GameObject {
	private Vec2d pos = new Vec2d();
	private float lightLevel;
	private int blockLightLevel = 15;
	private boolean isActive;
	protected final int height, width;
	
	public GameObject(double x, double y, int width, int height) {
		pos = new Vec2d(x, y);
		this.width = width;
		this.height = height;
		
		lightLevel = MathHelper.clamp(MathHelper.normalize(blockLightLevel, 15) - 0.1f, 0.0f, 0.9f);
	}
	
	/** Runs 60 times a second */
	public void tick() {}
	/** Runs 20 times a second */
	public void gameTick() {}
	
	/** Adds to the objects position */
	public void addPosition(double x, double y) {
		pos = pos.add(x, y);
	}
	
	/** Adds to the objects position */
	public void addPosition(Vec2d vec) {
		pos = pos.add(vec);
	}
	
	/** Adds to the objects X position */
	public void addPositionX(double x) {
		pos.x += x;
	}
	
	/** Adds to the objects Y position */
	public void addPositionY(double y) {
		pos.y += y;
	}
	
	/** Sets the objects position */
	public void setPosition(double x, double y) {
		pos = new Vec2d(x, y);
	}
	
	/** Sets the objects position */
	public void setPosition(Vec2d vec) {
		pos = vec;
	}
	
	/** Sets the objects X position */
	public void setPositionX(double x) {
		pos.x = x;
	}
	
	/** Sets the objects Y position */
	public void setPositionY(double y) {
		pos.y = y;
	}
	
	/** Gets the objects position */
	public Vec2d getPosition() {
		return pos;
	}
	
	/** Gets the objects X position */
	public double getPositionX() {
		return pos.x;
	}
	
	/** Gets the objects Y position */
	public double getPositionY() {
		return pos.y;
	}
	
	/** Returns the object's width */
	public int getWidth() {
		return width;
	}
	
	/** Returns the object's height */
	public int getHeight() {
		return height;
	}
	
	/** Gets the entities entire bounds */
	public Rectangle getBoundsAll() {
		return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width, height);
	}
	
	/** Gets the entities top bounds */
	public Rectangle getBoundsTop() {
		return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width, height / 4);
	}
	
	/** Gets the entities bottom bounds */
	public Rectangle getBoundsBottom() {
		return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()) + (height - (height / 4)), width, height / 4);
	}
	
	/** Gets the entities left bounds */
	public Rectangle getBoundsLeft() {
		return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width / 4, height);
	}
	
	/** Gets the entities right bounds */
	public Rectangle getBoundsRight() {
		return new Rectangle(MathHelper.floor(getPositionX() + (width - (width / 4))), MathHelper.floor(getPositionY()), width / 4, height);
	}
	
	public float getBlockLightLevel() {
		return blockLightLevel;
	}
	
	public float getLightLevel() {
		return lightLevel;
	}
	
	public void setIsActive(boolean bool) {
		isActive = bool;
	}
	
	public boolean getIsActive() {
		return isActive;
	}
}
