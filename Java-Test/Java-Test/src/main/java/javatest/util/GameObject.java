package main.java.javatest.util;

import java.awt.Graphics;

import main.java.javatest.util.math.Vec2d;

public abstract class GameObject {
	
	protected Vec2d pos;
	
	public GameObject(double x, double y) {
		this.pos = new Vec2d(x, y);
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	/** Adds to the objects position */
	public void addPosition(double x, double y) {
		this.pos = this.pos.add(x, y);
	}
	
	/** Adds to the objects position */
	public void addPosition(Vec2d vec) {
		this.pos = this.pos.add(vec);
	}
	
	/** Adds to the objects X position */
	public void addPositionX(double x) {
		this.pos.x += x;
	}
	
	/** Adds to the objects Y position */
	public void addPositionY(double y) {
		this.pos.y += y;
	}
	
	/** Sets the objects position */
	public void setPosition(double x, double y) {
		this.pos = new Vec2d(x, y);
	}
	
	/** Sets the objects position */
	public void setPosition(Vec2d vec) {
		this.pos = vec;
	}
	
	/** Sets the objects X position */
	public void setX(double x) {
		this.pos.x = x;
	}
	
	/** Sets the objects Y position */
	public void setY(double y) {
		this.pos.y = y;
	}
	
	/** Gets the objects position */
	public Vec2d getPosition() {
		return this.pos;
	}
	
	/** Gets the objects X position */
	public double getX() {
		return this.pos.x;
	}
	
	/** Gets the objects Y position */
	public double getY() {
		return this.pos.y;
	}
}
