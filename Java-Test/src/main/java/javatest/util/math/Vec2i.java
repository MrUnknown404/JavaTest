package main.java.javatest.util.math;

public class Vec2i {
	
	public static final Vec2i NULL_VECTOR = new Vec2i();
	private final int x, y;

	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vec2i(double x, double y) {
		this(MathHelper.floor(x), MathHelper.floor(y));
	}

	public Vec2i() {
		this(0, 0);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
