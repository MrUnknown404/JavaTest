package main.java.javatest.util;

import java.util.concurrent.ThreadLocalRandom;

import main.java.javatest.blocks.Block;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.handlers.ObjectHandler;
import main.java.javatest.util.math.BlockPos;

public class CreateTestLevel {
	public static void createLevel(int BLOCK_WIDTH, int BLOCK_HEIGHT, int WIDTH , int HEIGHT) {
		for (int i = 0; i < BLOCK_WIDTH; i++) {
			for (int i2 = 0; i2 < BLOCK_HEIGHT - 1; i2++) {
				if (ThreadLocalRandom.current().nextBoolean()) {
					if (ThreadLocalRandom.current().nextBoolean()) {
						if (ThreadLocalRandom.current().nextBoolean()) {
							if (ThreadLocalRandom.current().nextBoolean()) {
								if (ThreadLocalRandom.current().nextBoolean()) {
									ObjectHandler.addObject(new Block(new BlockPos(i, i2)));
								}
							}
						}
					}
				}
			}
		}
		
		ObjectHandler.addObject(new EntityPlayer((WIDTH / 2) - 23, (HEIGHT / 2) - 256, 24, 44));
	}
}
