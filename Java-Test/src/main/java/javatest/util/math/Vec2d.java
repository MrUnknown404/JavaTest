package main.java.javatest.util.math;

public class Vec2d {
	
	public static final Vec2d ZERO = new Vec2d(0.0D, 0.0D);
	public double x;
	public double y;

	public Vec2d(double x, double y) {
		if (x == -0.0D) {
			x = 0.0D;
		}
		
		if (y == -0.0D) {
			y = 0.0D;
		}
		
		this.x = x;
		this.y = y;
	}
	
	public Vec2d() {
		this(0, 0);
	}
	
	public Vec2d(Vec2i vec) {
		this((double) vec.getX(), (double) vec.getY());
	}
	
	public Vec2d subtract(Vec2d vec) {
		return subtract(vec.x, vec.y);
	}
	
	public Vec2d subtract(double x, double y) {
		return new Vec2d(this.x - x, this.y - y);
	}

	public Vec2d add(Vec2d vec) {
		return new Vec2d(x + vec.x, y + vec.y);
	}
	
	public Vec2d add(double x, double y) {
		return new Vec2d(this.x + x, this.y + y);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}