package main.java.javatest.blocks.util;

import java.util.ArrayList;
import java.util.List;

import main.java.javatest.init.EnumBlocks;

public class BlockProperties {
	private static List<BlockProperties> blocks = new ArrayList<BlockProperties>();
	
	public static final BlockProperties AIR = new BlockProperties(EnumBlocks.air);
	public static final BlockProperties STONE = new BlockProperties(EnumBlocks.stone);
	public static final BlockProperties DIRT = new BlockProperties(EnumBlocks.dirt);
	public static final BlockProperties GRASS = new BlockProperties(EnumBlocks.grass);
	
	private boolean hasCollision;
	private EnumBlocks type;
	
	public BlockProperties(EnumBlocks type) {
		this.type = type;
		
		if (!blocks.contains(this) && type != EnumBlocks.air) {
			blocks.add(this);
		}
		
		switch (type) {
			case air:
				setHasCollision(false);
				break;
			default:
				setHasCollision(true);
				break;
		}
	}
	
	public static BlockProperties findBlockPropertyWithName(String name) {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).getBlockType().toString().equals(name)) {
				return blocks.get(i);
			}
		}
		return null;
	}
	
	public boolean getHasCollision() {
		return hasCollision;
	}
	
	public BlockProperties setHasCollision(boolean bool) {
		hasCollision = bool;
		return this;
	}
	
	public static List<BlockProperties> getBlocks() {
		return blocks;
	}
	
	public EnumBlocks getBlockType() {
		return type;
	}
}
