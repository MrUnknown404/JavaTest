package main.java.javatest.util.math;

public class BlockPos {

	public static final BlockPos ZERO = new BlockPos();
	public int x;
	public int y;
	
	public BlockPos(int x, int y) {
		this.x = x;
		this.y = y;
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
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
