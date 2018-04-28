package main.java.javatest.util;

import java.util.Random;

import main.java.javatest.blocks.Block;
import main.java.javatest.blocks.BlockDirt;
import main.java.javatest.blocks.BlockGrass;
import main.java.javatest.blocks.BlockStone;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.math.BlockPos;

public class CreateTestLevel {
	private static final int tempMulti = 1; //TEMPORARY
	
	/**
	 * All of this is temporary! it will all be changed!
	 * 
	 * @param BLOCK_WIDTH
	 * @param BLOCK_HEIGHT
	 */
	public static void createLevel(int BLOCK_WIDTH, int BLOCK_HEIGHT) {
		System.out.println(Console.info(Console.WarningType.Info) + "Creating a level...");
		
		final int HEIGHT = BLOCK_HEIGHT * Block.SIZE;
		final int WIDTH = BLOCK_WIDTH * Block.SIZE;
		
		BLOCK_WIDTH *= tempMulti;
		BLOCK_HEIGHT *= tempMulti;
		
		for (int i = 0; i < BLOCK_WIDTH; i++) {
			for (int i2 = 4; i2 < BLOCK_HEIGHT; i2++) {
				if (i2 == 4) {
					ObjectHandler.addObjectAll(new BlockGrass(new BlockPos(i, i2)));
				} else if (i2 == 5) {
					if (new Random().nextBoolean() && new Random().nextBoolean()) {
						ObjectHandler.addObjectAll(new BlockGrass(new BlockPos(i, i2)));
					} else {
						ObjectHandler.addObjectAll(new BlockDirt(new BlockPos(i, i2)));
					}
				} else if (i2 == 6 || i2 == 7) {
					ObjectHandler.addObjectAll(new BlockDirt(new BlockPos(i, i2)));
				} else if (i2 == 8) {
					if (new Random().nextBoolean() && new Random().nextBoolean()) {
						ObjectHandler.addObjectAll(new BlockStone(new BlockPos(i, i2)));
					} else {
						ObjectHandler.addObjectAll(new BlockDirt(new BlockPos(i, i2)));
					}
				} else if (i2 == 9) {
					if (new Random().nextBoolean()) {
						ObjectHandler.addObjectAll(new BlockDirt(new BlockPos(i, i2)));
					} else {
						ObjectHandler.addObjectAll(new BlockStone(new BlockPos(i, i2)));
					}
				} else if (i2 == 10) {
					if (new Random().nextBoolean() && new Random().nextBoolean()) {
						ObjectHandler.addObjectAll(new BlockDirt(new BlockPos(i, i2)));
					} else {
						ObjectHandler.addObjectAll(new BlockStone(new BlockPos(i, i2)));
					}
				} else if (i2 == 11) {
					if (new Random().nextBoolean() && new Random().nextBoolean() && new Random().nextBoolean()) {
						ObjectHandler.addObjectAll(new BlockDirt(new BlockPos(i, i2)));
					} else {
						ObjectHandler.addObjectAll(new BlockStone(new BlockPos(i, i2)));
					}
				} else {
					ObjectHandler.addObjectAll(new BlockStone(new BlockPos(i, i2)));
				}
			}
		}
		
		ObjectHandler.addObjectAll(new EntityPlayer((WIDTH / 2) - 20, (HEIGHT / 2) - 256));
		System.out.println(Console.info(Console.WarningType.Info) + "Finished creating the level!");
	}
}
