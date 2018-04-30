package main.java.javatest.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.javatest.Main;
import main.java.javatest.blocks.Block;
import main.java.javatest.blocks.BlockDirt;
import main.java.javatest.blocks.BlockGrass;
import main.java.javatest.blocks.BlockStone;
import main.java.javatest.entity.Entity;
import main.java.javatest.entity.entityliving.EntityLiving;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.Console;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.math.BlockPos;

public class World {

	private static final double FRICTION = 0.2;
	private static final double GRAVITY = 1.75;
	private static final double MAX_FALL_SPEED = (GRAVITY * GRAVITY) * ((GRAVITY * GRAVITY) * (GRAVITY * GRAVITY));
	
	private static int worldWidth, worldHeight;
	
	private static List<Block> allBlocks = new ArrayList<Block>();
	private static List<Entity> allEntities = new ArrayList<Entity>();
	private static List<Block> activeBlocks = new ArrayList<Block>();
	private static List<Entity> activeEntities = new ArrayList<Entity>();
	
	public static void generateWorld(int xSize, int ySize) {
		System.out.println(Console.info(Console.WarningType.Info) + "Creating a new world...");
		
		worldWidth = xSize;
		worldHeight = ySize;
		
		for (int i = 0; i < worldWidth; i++) {
			for (int i2 = 4; i2 < worldHeight; i2++) {
				if (i2 == 4) {
					allBlocks.add(new BlockGrass(new BlockPos(i, i2)));
				} else if (i2 == 5) {
					if (new Random().nextBoolean() && new Random().nextBoolean()) {
						allBlocks.add(new BlockGrass(new BlockPos(i, i2)));
					} else {
						allBlocks.add(new BlockDirt(new BlockPos(i, i2)));
					}
				} else if (i2 == 6 || i2 == 7) {
					allBlocks.add(new BlockDirt(new BlockPos(i, i2)));
				} else if (i2 == 8) {
					if (new Random().nextBoolean() && new Random().nextBoolean()) {
						allBlocks.add(new BlockStone(new BlockPos(i, i2)));
					} else {
						allBlocks.add(new BlockDirt(new BlockPos(i, i2)));
					}
				} else if (i2 == 9) {
					if (new Random().nextBoolean()) {
						allBlocks.add(new BlockDirt(new BlockPos(i, i2)));
					} else {
						allBlocks.add(new BlockStone(new BlockPos(i, i2)));
					}
				} else if (i2 == 10) {
					if (new Random().nextBoolean() && new Random().nextBoolean()) {
						allBlocks.add(new BlockDirt(new BlockPos(i, i2)));
					} else {
						allBlocks.add(new BlockStone(new BlockPos(i, i2)));
					}
				} else if (i2 == 11) {
					if (new Random().nextBoolean() && new Random().nextBoolean() && new Random().nextBoolean()) {
						allBlocks.add(new BlockDirt(new BlockPos(i, i2)));
					} else {
						allBlocks.add(new BlockStone(new BlockPos(i, i2)));
					}
				} else {
					allBlocks.add(new BlockStone(new BlockPos(i, i2)));
				}
			}
		}
		
		System.out.println(Console.info(Console.WarningType.Info) + "Placing player!");
		allEntities.add(new EntityPlayer(((worldWidth/ 2) * Block.getBlockSize()) - 12, -44));
		
		redoActives();
		
		System.out.println(Console.info(Console.WarningType.Info) + "Finished creating the world!");
	}
	
	//private static final String LOC = System.getProperty("user.dir") + "\\run\\worlds\\";
	//private static final String TYPE = ".sav";
	
	public static void saveWorld(String name) {
		System.out.println(Console.info(Console.WarningType.Info) + "Saving the world...");
		
		System.out.println(Console.info(Console.WarningType.Info) + "Finished saving the world!");
	}
	
	public static void loadWorld(String name) {
		System.out.println(Console.info(Console.WarningType.Info) + "Loading the world...");
		
		System.out.println(Console.info(Console.WarningType.Info) + "Finished loading the world!");
	}
	
	public void tick() {
		for (int i = 0; i < activeBlocks.size(); i++) {
			Block obj = activeBlocks.get(i);
			
			if (obj != null && !obj.getIsActive()) {
				continue;
			} else if (obj == null) {
				return;
			}
			
			obj.tick();
		}
		
		for (int i = 0; i < activeEntities.size(); i++) {
			Entity obj = activeEntities.get(i);
			
			if (obj != null && !obj.getIsActive()) {
				continue;
			} else if (obj == null) {
				return;
			}
			
			obj.tick();
			if (obj instanceof EntityLiving) {
				((EntityLiving) obj).tickAlive();
			}
		}
	}
	
	public void gameTick() {
		for (int i = 0; i < activeBlocks.size(); i++) {
			Block obj = activeBlocks.get(i);
			
			if (obj != null && !obj.getIsActive()) {
				continue;
			} else if (obj == null) {
				return;
			}
			
			obj.gameTick();
		}
		
		for (int i = 0; i < activeEntities.size(); i++) {
			Entity obj = activeEntities.get(i);
			
			if (obj != null && !obj.getIsActive()) {
				continue;
			} else if (obj == null) {
				return;
			}
			
			obj.gameTick();
			if (obj instanceof EntityLiving) {
				((EntityLiving) obj).gameTickAlive();
			}
		}
	}
	
	public static void redoActives() {
		activeBlocks.clear();
		activeEntities.clear();
		
		Rectangle rect = new Rectangle((int) -Main.getCamera().getPositionX(), (int) -Main.getCamera().getPositionY(), Main.WIDTH, Main.HEIGHT);
		
		for (int i = 0; i < allBlocks.size(); i++) {
			Block obj = allBlocks.get(i);
			
			if (obj == null) {
				continue;
			}
			
			if (obj.getBoundsAll().intersects(rect)) {
				obj.setIsActive(true);
				activeBlocks.add(obj);
			} else {
				obj.setIsActive(false);
			}
		}
		
		for (int i = 0; i < allEntities.size(); i++) {
			Entity obj = allEntities.get(i);
			if (obj == null) {
				continue;
			}
			
			if (obj.getBoundsAll().intersects(rect) || obj.getEntityProperties().getAlwaysLoad()) {
				obj.setIsActive(true);
				activeEntities.add(obj);
			} else {
				obj.setIsActive(false);
			}
		}
	}
	
	public static void redoSpecificActiveBlock(Block obj) {
		if (obj == null) {
			return;
		}
		
		Rectangle rect = new Rectangle((int) -Main.getCamera().getPositionX(), (int) -Main.getCamera().getPositionY(), Main.WIDTH, Main.HEIGHT);
		
		activeBlocks.remove(obj);
		if (obj.getBoundsAll().intersects(rect) && !obj.getIsActive()) {
			obj.setIsActive(true);
			activeBlocks.add(obj);
		} else {
			obj.setIsActive(false);
		}
	}
	
	public static void redoSpecificActiveEntity(Entity obj) {
		if (obj == null) {
			return;
		}
		
		Rectangle rect = new Rectangle((int) -Main.getCamera().getPositionX(), (int) -Main.getCamera().getPositionY(), Main.WIDTH, Main.HEIGHT);
		
		activeEntities.remove(obj);
		if ((obj.getBoundsAll().intersects(rect) || ((Entity) obj).getEntityProperties().getAlwaysLoad()) && !obj.getIsActive()) {
			obj.setIsActive(true);
			activeEntities.add(obj);
		} else {
			obj.setIsActive(false);
		}
	}
	
	public static void addBlockAll(Block object) {
		if (object != null && !allBlocks.contains(object)) {
			allBlocks.add(object);
		} else {
			System.out.println(Console.info(Console.WarningType.Error) + "object is null or Object already exists!");
		}
	}
	
	public static void addEntityAll(Entity object) {
		if (object != null && !allEntities.contains(object)) {
			allEntities.add(object);
		} else {
			System.out.println(Console.info(Console.WarningType.Error) + "object is null or Object already exists!");
		}
	}
	
	public static void removeBlockAll(Block object) {
		if (object != null && allBlocks.contains(object)) {
			allBlocks.remove(object);
			redoSpecificActiveBlock(object);
		} else {
			System.out.println(Console.info(Console.WarningType.Error) + "object is null!");
		}
	}
	
	public static void removeEntityAll(Entity object) {
		if (object != null && allEntities.contains(object)) {
			allEntities.remove(object);
			redoSpecificActiveEntity(object);
		} else {
			System.out.println(Console.info(Console.WarningType.Error) + "object is null!");
		}
	}
	
	public static int findObject(GameObject obj) {
		for (int i = 0; i < allBlocks.size(); i++) {
			Block o = allBlocks.get(i);
			if (o == obj) {
				return i;
			}
		}
		for (int i = 0; i < allEntities.size(); i++) {
			Entity o = allEntities.get(i);
			if (o == obj) {
				return i;
			}
		}
		System.out.println(Console.info(Console.WarningType.Error) + "Cannot find object : " + obj.toString());
		return 0;
	}
	
	public static List<Block> getAllBlocks() {
		return allBlocks;
	}
	
	public static List<Entity> getAllEntities() {
		return allEntities;
	}
	
	public static List<Block> getActiveBlocks() {
		return activeBlocks;
	}
	
	public static List<Entity> getActiveEntities() {
		return activeEntities;
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