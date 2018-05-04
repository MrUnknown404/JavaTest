package main.java.javatest.init;

import main.java.javatest.blocks.util.BlockProperties;

public enum EnumBlocks {
	air  (0),
	stone(1),
	dirt (2),
	grass(3);
	
	private final int fId;
	
	private EnumBlocks(int id) {
		fId = id;
	}
	
	public static EnumBlocks getNumber(int id) {
		for (EnumBlocks type : values()) {
			if (type.fId == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid Type id: " + id);
	}
	
	public static EnumBlocks findItemWithString(String name) {
		if (name.equals("") || name == null) {
			return null;
		}
		for (int i = 0; i < BlockProperties.getBlocks().size(); i++) {
			if (BlockProperties.getBlocks().get(i).getBlockType().toString().equals(name)) {
				return BlockProperties.getBlocks().get(i).getBlockType();
			}
		}
		return null;
	}
}
