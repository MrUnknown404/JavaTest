package main.java.javatest.blocks.util;

import java.util.ArrayList;
import java.util.List;

import main.java.javatest.util.Console;

public class BlockProperties {
	private static List<BlockProperties> blocks = new ArrayList<BlockProperties>();
	
	public static final BlockProperties AIR = new BlockProperties(BlockType.air);
	public static final BlockProperties STONE = new BlockProperties(BlockType.stone);
	public static final BlockProperties DIRT = new BlockProperties(BlockType.dirt);
	public static final BlockProperties GRASS = new BlockProperties(BlockType.grass);
	
	private boolean hasCollision;
	private BlockType type;
	
	public BlockProperties(BlockType type) {
		this.type = type;
		
		addProperties();
		
		switch (type) {
			case air:
				setHasCollision(false);
				break;
			case stone:
				setHasCollision(true);
				break;
			case grass:
				setHasCollision(true);
				break;
			case dirt:
				setHasCollision(true);
				break;
			default:
				System.out.println(Console.info(Console.WarningType.Error) + "Invalid type");
				setHasCollision(false);
				break;
		}
	}
	
	private void addProperties() {
		if (!blocks.contains(this)) {
			blocks.add(this);
			if (blocks.size() == BlockType.values().length) { //all this is debug
				int ti = 1;
				System.out.print(Console.info() + "Block properties : ");
				for (BlockProperties b : blocks) {
					if (blocks.size() == ti) {
						System.out.print(b.type.toString().toUpperCase() + "!");
					} else {
						System.out.print(b.type.toString().toUpperCase() + ", ");
					}
					ti++;
				}
				System.out.println();
			}
		}
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
	
	public BlockType getBlockType() {
		return type;
	}
	
	public enum BlockType {
		air  (0),
		stone(1),
		dirt (2),
		grass(3);
		
		private final int fId;
		
		private BlockType(int id) {
			fId = id;
		}

		public static BlockType getNumber(int id) {
			for (BlockType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
