package main.java.javatest.blocks;

import main.java.javatest.blocks.util.Block;
import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.util.math.BlockPos;

public class BlockDirt extends Block {

	public BlockDirt(BlockPos pos) {
		super(pos, BlockProperties.DIRT);
	}
}
