package main.java.javatest.init;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import main.java.javatest.blocks.util.BlockProperties;

public class Blocks {
	public static List<BlockProperties> blocks = new ArrayList<BlockProperties>();
	
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
	
	public static int findItemInt(BlockProperties item) {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i) == item) {
				return i;
			}
		}
		return 0;
	}
	
	public static BlockProperties getRandomItem() {
		return blocks.get(ThreadLocalRandom.current().nextInt(1, blocks.size()));
	}
	
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
	}
}
