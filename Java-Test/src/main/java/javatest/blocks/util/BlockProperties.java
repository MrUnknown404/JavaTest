package main.java.javatest.blocks.util;

public class BlockProperties {
	public static final BlockProperties AIR = new BlockProperties(EnumBlockType.AIR);
	public static final BlockProperties STONE = new BlockProperties(EnumBlockType.STONE);
	
	private boolean hasCollision;
	public EnumBlockType type;
	
	public BlockProperties(EnumBlockType type) {
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
	
	public EnumBlockType getBlockType() {
		return type;
	}
	
	public enum EnumBlockType {
		AIR  (0),
		STONE(1);
		
		public final int fId;
		
		private EnumBlockType(int id) {
			fId = id;
		}

		public static EnumBlockType getNumber(int id) {
			for (EnumBlockType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
