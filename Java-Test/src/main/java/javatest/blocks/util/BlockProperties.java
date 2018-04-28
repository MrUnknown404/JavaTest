package main.java.javatest.blocks.util;

public class BlockProperties {
	public static final BlockProperties AIR = new BlockProperties(BlockType.AIR);
	public static final BlockProperties STONE = new BlockProperties(BlockType.STONE);
	
	private boolean hasCollision;
	public BlockType type;
	
	public BlockProperties(BlockType type) {
		this.type = type;
		
		switch (type) {
			case AIR:
				setHasCollision(false);
				break;
			case STONE:
				setHasCollision(true);
				break;
			default:
				setHasCollision(false);
				break;
		}
	}
	
	public boolean getHasCollision() {
		return hasCollision;
	}
	
	public BlockProperties setHasCollision(boolean bool) {
		hasCollision = bool;
		return this;
	}
	
	public BlockType getBlockType() {
		return type;
	}
	
	public enum BlockType {
		AIR  (0),
		STONE(1);
		
		public final int fId;
		
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
