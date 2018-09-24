package main.java.javatest.blocks.util;

import main.java.javatest.init.Blocks;

public class BlockProperties {
	public static final BlockProperties AIR = new BlockProperties(Blocks.EnumBlocks.air);
	public static final BlockProperties STONE = new BlockProperties(Blocks.EnumBlocks.stone);
	public static final BlockProperties DIRT = new BlockProperties(Blocks.EnumBlocks.dirt);
	public static final BlockProperties GRASS = new BlockProperties(Blocks.EnumBlocks.grass);
	public static final BlockProperties DIRTBRICK = new BlockProperties(Blocks.EnumBlocks.dirtbrick);
	public static final BlockProperties STONEBRICK = new BlockProperties(Blocks.EnumBlocks.stonebrick);
	
	private boolean hasCollision = true;
	private int hardness;
	private Blocks.EnumBlocks type;
	
	public BlockProperties(Blocks.EnumBlocks type) {
		this.type = type;
		
		if (!Blocks.getBlocks().contains(this) && type != Blocks.EnumBlocks.air) {
			Blocks.getBlocks().add(this);
		}
		
		switch (type) {
			case air:
				setHasCollision(false);
				setHardness(0);
				break;
			case dirt:
				setHardness(6);
				break;
			case dirtbrick:
				setHardness(10);
				break;
			case grass:
				setHardness(8);
				break;
			case stone:
				setHardness(10);
				break;
			case stonebrick:
				setHardness(12);
				break;
			default:
				setHardness(10);
				break;
		}
	}
	
	public BlockProperties() {}
	
	public static BlockProperties findBlockPropertyWithName(String name) {
		for (int i = 0; i < Blocks.getBlocks().size(); i++) {
			if (Blocks.getBlocks().get(i).getBlockType().toString().equals(name)) {
				return Blocks.getBlocks().get(i);
			}
		}
		return null;
	}
	
	public boolean getHasCollision() {
		return hasCollision;
	}
	
	public int getHardness() {
		return hardness;
	}
	
	protected BlockProperties setHasCollision(boolean bool) {
		hasCollision = bool;
		return this;
	}
	
	protected BlockProperties setHardness(int hardness) {
		this.hardness = hardness;
		return this;
	}
	
	public Blocks.EnumBlocks getBlockType() {
		return type;
	}
}
