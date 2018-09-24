package main.java.javatest.world.util;

import java.awt.Rectangle;

import main.java.javatest.blocks.util.Block;

public class BlockLocationData {
	private int x, y, blockLocID;
	
	BlockLocationData(int x, int y, int blockLocID) {
		this.x = x;
		this.y = y;
		this.blockLocID = blockLocID;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getBlockLocID() {
		return blockLocID;
	}
	
	public Rectangle getBoundsAll() {
		return new Rectangle(x * Block.getBlockSize(), y * Block.getBlockSize(), Block.getBlockSize(), Block.getBlockSize());
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + " | " + blockLocID + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BlockLocationData) {
			if (((BlockLocationData) obj).x == x && ((BlockLocationData) obj).y == y && ((BlockLocationData) obj).blockLocID == blockLocID) {
				return true;
			}
		}
		return false;
	}
}
