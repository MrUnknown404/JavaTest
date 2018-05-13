package main.java.javatest.util.math;

public class BlockPos {

	public static final BlockPos ZERO = new BlockPos();
	private int x, y;
	
	public BlockPos(Vec2i vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public BlockPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public BlockPos(double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
	}
	
	public BlockPos() {
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
		if (obj instanceof BlockPos) {
			if (((BlockPos) obj).x == x && ((BlockPos) obj).y == y) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
