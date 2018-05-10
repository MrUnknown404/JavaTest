package main.java.javatest.blocks.util;

import java.util.List;

import main.java.javatest.init.Blocks;

public class BlockProperties {
	public static final BlockProperties AIR = new BlockProperties(Blocks.EnumBlocks.air);
	public static final BlockProperties STONE = new BlockProperties(Blocks.EnumBlocks.stone);
	public static final BlockProperties DIRT = new BlockProperties(Blocks.EnumBlocks.dirt);
	public static final BlockProperties GRASS = new BlockProperties(Blocks.EnumBlocks.grass);
	
	private boolean hasCollision;
	private int hardness, wrongToolMultiplier;
	private Blocks.EnumBlocks type;
	
	public BlockProperties(Blocks.EnumBlocks type) {
		this.type = type;
		
		if (!Blocks.blocks.contains(this) && type != Blocks.EnumBlocks.air) {
			Blocks.blocks.add(this);
		}
		
		switch (type) {
			case air:
				setHardness(0);
				setWrongToolMultiplier(0);
				setHasCollision(false);
				break;
			case dirt:
				setHardness(35);
				setWrongToolMultiplier(2);
				setHasCollision(true);
				break;
			case grass:
				setHardness(45);
				setWrongToolMultiplier(2);
				setHasCollision(true);
				break;
			case stone:
				setHardness(60);
				setWrongToolMultiplier(8);
				setHasCollision(true);
				break;
			default:
				setHardness(-1);
				setWrongToolMultiplier(0);
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
	
	public int getHardness() {
		return hardness;
	}
	
	public int getWrongToolMultiplier() {
		return wrongToolMultiplier;
	}
	
	public boolean getHasCollision() {
		return hasCollision;
	}
	
	protected BlockProperties setHasCollision(boolean bool) {
		hasCollision = bool;
		return this;
	}
	
	protected BlockProperties setHardness(int hardness) {
		this.hardness = hardness;
		return this;
	}
	
	protected BlockProperties setWrongToolMultiplier(int wrongToolMultiplier) {
		this.wrongToolMultiplier = wrongToolMultiplier;
		return this;
	}
	
	public static List<BlockProperties> getBlocks() {
		return Blocks.blocks;
	}
	
	public Blocks.EnumBlocks getBlockType() {
		return type;
	}
}
