package main.java.javatest.util;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.java.javatest.util.math.Vec2d;

public abstract class GameObject {
	
	private Vec2d pos;
	public int height;
	public int width;
	
	public GameObject(double x, double y, int width, int height) {
		pos = new Vec2d(x, y);
		this.width = width;
		this.height = height;
	}
	
	/** Runs 60 time a second */
	public abstract void tick();
	/** Runs 20 time a second */
	public abstract void gameTick();
	public abstract void render(Graphics g);
	
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
	
	/** Gets the entities bounds */
	public Rectangle getBounds() {
		return new Rectangle((int) getPositionX(), (int) getPositionY(), width, height);
	}
	
	/** Gets the entities top bounds */
	public Rectangle getBoundsTop() {
		return new Rectangle((int) getPositionX(), (int) getPositionY(), width, height / 4);
	}
	
	/** Gets the entities bottom bounds */
	public Rectangle getBoundsBottom() {
		return new Rectangle((int) getPositionX(), (int) getPositionY() + (height - (width / 4)), width, height / 4);
	}
	
	/** Gets the entities left bounds */
	public Rectangle getBoundsLeft() {
		return new Rectangle((int) getPositionX(), (int) getPositionY() + 1, width / 4, height - 2);
	}
	
	/** Gets the entities right bounds */
	public Rectangle getBoundsRight() {
		return new Rectangle((int) getPositionX() + (width - (width / 4)), (int) getPositionY() + 1, width / 4, height - 2);
	}
}
