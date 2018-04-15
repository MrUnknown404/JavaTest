package main.java.javatest.util.math;

public class Vec2i {
	
	public static final Vec2i NULL_VECTOR = new Vec2i();
	private final int x;
	private final int y;

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
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Vec2i)) {
			return false;
		} else {
			Vec2i vec3i = (Vec2i) obj;
			return getX() != vec3i.getX() ? false : (getY() != vec3i.getY());
		}
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
