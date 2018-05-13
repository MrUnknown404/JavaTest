package main.java.javatest.blocks.util;

import main.java.javatest.init.Blocks;

public class BlockProperties {
	public static final BlockProperties AIR = new BlockProperties(Blocks.EnumBlocks.air);
	public static final BlockProperties STONE = new BlockProperties(Blocks.EnumBlocks.stone);
	public static final BlockProperties DIRT = new BlockProperties(Blocks.EnumBlocks.dirt);
	public static final BlockProperties GRASS = new BlockProperties(Blocks.EnumBlocks.grass);
	public static final BlockProperties DIRTBRICK = new BlockProperties(Blocks.EnumBlocks.dirtbrick);
	public static final BlockProperties STONEBRICK = new BlockProperties(Blocks.EnumBlocks.stonebrick);
	
	private boolean hasCollision;
	private Blocks.EnumBlocks type;
	
	public BlockProperties(Blocks.EnumBlocks type) {
		this.type = type;
		
		if (!Blocks.blocks.contains(this) && type != Blocks.EnumBlocks.air) {
			Blocks.blocks.add(this);
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
		for (int i = 0; i < Blocks.blocks.size(); i++) {
			if (Blocks.blocks.get(i).getBlockType().toString().equals(name)) {
				return Blocks.blocks.get(i);
			}
		}
		return null;
	}
	
	public boolean getHasCollision() {
		return hasCollision;
	}
	
	protected BlockProperties setHasCollision(boolean bool) {
		hasCollision = bool;
		return this;
	}
	
	public Blocks.EnumBlocks getBlockType() {
		return type;
	}
}
