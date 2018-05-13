package main.java.javatest.util;

public abstract class TickableGameObject extends GameObject {
	
	public TickableGameObject(double x, double y, int width, int height) {
		super(x, y, width, height);
	}
	
	/** Runs 60 times a second */
	public abstract void tick();
}
