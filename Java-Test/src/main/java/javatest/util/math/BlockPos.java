package main.java.javatest.util.math;

public class BlockPos {

	public static final BlockPos ZERO = new BlockPos();
	public int x, y;
	
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
	
	public BlockPos add(int x, int y) {
		return new BlockPos(this.x + x, this.y + y);
	}
	
	public BlockPos add(BlockPos pos) {
		return add(pos.x, pos.y);
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
