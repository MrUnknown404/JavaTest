package main.java.javatest.blocks;

import main.java.javatest.blocks.util.Block;
import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.util.math.BlockPos;

public class BlockGrass extends Block {

	public BlockGrass(BlockPos pos) {
		super(pos, BlockProperties.GRASS);
	}
}
