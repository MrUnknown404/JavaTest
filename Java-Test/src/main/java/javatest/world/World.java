package main.java.javatest.world;

import java.util.Random;

import main.java.javatest.blocks.Block;
import main.java.javatest.blocks.BlockDirt;
import main.java.javatest.blocks.BlockGrass;
import main.java.javatest.blocks.BlockStone;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.Console;
import main.java.javatest.util.ObjectHandler;
import main.java.javatest.util.math.BlockPos;

public class World {

	private static final double FRICTION = 0.2;
	private static final double GRAVITY = 1.75;
	private static final double MAX_FALL_SPEED = (GRAVITY * GRAVITY) * ((GRAVITY * GRAVITY) * (GRAVITY * GRAVITY));
	
	private static int worldWidth, worldHeight;
	
	public static void generateWorld(int xSize, int ySize) {
		System.out.println(Console.info(Console.WarningType.Info) + "Creating a new world...");
		
		worldWidth = xSize;
		worldHeight = ySize;
		
		for (int i = 0; i < worldWidth; i++) {
			for (int i2 = 4; i2 < worldHeight; i2++) {
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
		
		System.out.println(Console.info(Console.WarningType.Info) + "Placing player!");
		ObjectHandler.addObjectAll(new EntityPlayer(((worldWidth * Block.getBlockSize()) / 2) + 4, -44));
		
		System.out.println(Console.info(Console.WarningType.Info) + "Finished creating the world!");
	}
	
	public static double getFriction() {
		return FRICTION;
	}
	
	public static double getGravity() {
		return GRAVITY;
	}
	
	public static double getMaxFallSpeed() {
		return MAX_FALL_SPEED;
	}

	public static int getWorldWidth() {
		return worldWidth;
	}

	public static int getWorldHeight() {
		return worldHeight;
	}
}
