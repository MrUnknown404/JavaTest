package main.java.javatest.util;

import main.java.javatest.blocks.Block;
import main.java.javatest.blocks.BlockStone;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.handlers.ObjectHandler;
import main.java.javatest.util.math.BlockPos;

public class CreateTestLevel {
	private static final int tempMulti = 1; //TEMPORARY
	
	public static void createLevel(int BLOCK_WIDTH, int BLOCK_HEIGHT) {
		System.out.println(Console.info(Console.EnumWarningType.Info) + "Creating a level...");
		
		final int HEIGHT = BLOCK_HEIGHT * Block.SIZE;
		final int WIDTH = BLOCK_WIDTH * Block.SIZE;
		
		BLOCK_WIDTH *= tempMulti;
		BLOCK_HEIGHT *= tempMulti;
		
		for (int i = 0; i < BLOCK_WIDTH; i++) {
			for (int i2 = 0; i2 < BLOCK_HEIGHT; i2++) {
				//if (ThreadLocalRandom.current().nextBoolean()) {
					//if (ThreadLocalRandom.current().nextBoolean()) {
						//if (ThreadLocalRandom.current().nextBoolean()) {
							//if (ThreadLocalRandom.current().nextBoolean()) {
								//if (ThreadLocalRandom.current().nextBoolean()) {
									ObjectHandler.addObjectAll(new BlockStone(new BlockPos(i, i2)));
								//}
							//}
						//}
					//}
				//}
			}
		}
		
		ObjectHandler.addObjectAll(new EntityPlayer((WIDTH / 2) - 20, (HEIGHT / 2) - 256, 24, 44));
		System.out.println(Console.info(Console.EnumWarningType.Info) + "Finished creating the level!");
	}
}
