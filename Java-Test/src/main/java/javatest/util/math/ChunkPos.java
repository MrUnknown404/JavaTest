package main.java.javatest.util.math;

public class ChunkPos {
	
	public static final ChunkPos ZERO = new ChunkPos();
	private int x, y;
	
	public ChunkPos(Vec2i vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public ChunkPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public ChunkPos() {
		this(0, 0);
	}
	
	public void addX(int x) {
		this.x += x;
	}
	
	public void addY(int y) {
		this.y += y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ChunkPos) {
			if (((ChunkPos) obj).x == x && ((ChunkPos) obj).y == y) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
