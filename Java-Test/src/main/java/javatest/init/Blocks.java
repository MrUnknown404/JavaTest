package main.java.javatest.init;

import java.util.ArrayList;
import java.util.List;

import main.java.javatest.blocks.util.BlockProperties;

public class Blocks {
	private static List<BlockProperties> blocks = new ArrayList<BlockProperties>();
	
	public static BlockProperties findBlock(String name) {
		if (name.equals("") || name == null) {
			return blocks.get(0);
		}
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).getBlockType().toString().equals(name)) {
				return blocks.get(i);
			}
		}
		return null;
	}
	
	public static List<BlockProperties> getBlocks() {
		return blocks;
	}
	
	public enum EnumBlocks {
		air       (0),
		stone     (1),
		dirt      (2),
		grass     (3),
		dirtbrick (4),
		stonebrick(5);
		
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
	}
}
