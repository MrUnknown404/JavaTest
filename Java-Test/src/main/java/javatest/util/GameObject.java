package main.java.javatest.util;

import java.awt.Graphics;

import main.java.javatest.util.math.Vec2d;

public abstract class GameObject {
	
	private Vec2d pos;
	
	public GameObject(double x, double y) {
		pos = new Vec2d(x, y);
	}
	
	/** Runs 60 time a second */
	public abstract void tick();
	/** Runs 20 time a second */
	public abstract void gameTick();
	public abstract void render(Graphics g);
	
	public abstract void doVelocity();
	
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
	public void setX(double x) {
		pos.x = x;
	}
	
	/** Sets the objects Y position */
	public void setY(double y) {
		pos.y = y;
	}
	
	/** Gets the objects position */
	public Vec2d getPosition() {
		return pos;
	}
	
	/** Gets the objects X position */
	public double getX() {
		return pos.x;
	}
	
	/** Gets the objects Y position */
	public double getY() {
		return pos.y;
	}
}
