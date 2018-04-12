package main.java.javatest.entity;

import java.awt.Graphics;

import main.java.javatest.util.GameObject;
import main.java.javatest.util.math.Vec2d;

public abstract class Entity extends GameObject {
	
	protected Vec2d velocity = new Vec2d();
	
	public Entity(double x, double y) {
		super(x, y);
	}
	
	@Override
	public void tick() {
		
	}
	
	public abstract void tickAlive();
	
	@Override
	public void render(Graphics g) {
		
	}
	
	public abstract void doVelocity();
	
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
}
