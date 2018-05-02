package main.java.javatest.world;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

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
import main.java.javatest.util.math.MathHelper;

public class World {

	public static boolean isGeneratingWorld, doesWorldExist, isSaving, isLoading;
	private static final String LOC = System.getProperty("user.dir") + "\\run\\worlds\\";
	private static final String TYPE = ".sav", TYPE2 = ".properties";
	
	private static final double FRICTION = 0.2;
	private static final double GRAVITY = 1.75;
	private static final double MAX_FALL_SPEED = (GRAVITY * GRAVITY) * ((GRAVITY * GRAVITY) * (GRAVITY * GRAVITY));
	
	private static float amountGenerated;
	private static int worldLength, worldHeight, worldSeed;
	private static Random random;
	
	private static List<Block> allBlocks = new ArrayList<Block>();
	private static List<Entity> allEntities = new ArrayList<Entity>();
	private static List<Block> activeBlocks = new ArrayList<Block>();
	private static List<Entity> activeEntities = new ArrayList<Entity>();
	private static EntityPlayer player;
	
	public static void generateWorld(int xSize, int ySize, int seed) {
		System.out.println(Console.info(Console.WarningType.Info) + "-Creating a new world...");
		isGeneratingWorld = true;
		
		worldLength = xSize;
		worldHeight = ySize;
		worldSeed = seed;
		random = new Random(seed);
		
		int tempInt = 0;
		int tempHeight = 0;
		
		System.out.println(Console.info() + "Moving Camera!");
		Main.getCamera().setPosition(((worldLength / 2) * Block.getBlockSize()), -15 * -Block.getBlockSize());
		
		System.out.println(Console.info() + "Placing blocks...");
		for (int width = 0; width < worldLength; width++) {
			for (int height = 0; height < worldHeight + 15; height++) {
				if (height == 0) {
					if (tempInt != 0) {
						tempInt--;
						addBlockAll(new BlockGrass(new BlockPos(width, worldHeight + height - tempHeight)));
					} else if (tempHeight == 15) {
						tempHeight--;
						tempInt += 3;
						addBlockAll(new BlockGrass(new BlockPos(width, worldHeight + height - tempHeight)));
					} else if (tempHeight == -15) {
						tempHeight++;
						tempInt += 3;
						addBlockAll(new BlockGrass(new BlockPos(width, worldHeight + height - tempHeight)));
					} else if (random.nextInt(4) == 0) {
						if (random.nextBoolean()) {
							tempHeight++;
							tempHeight = MathHelper.clamp(tempHeight, -15, 15);
							tempInt += 2;
						} else {
							tempHeight--;
							tempHeight = MathHelper.clamp(tempHeight, -15, 15);
							tempInt += 2;
						}
						addBlockAll(new BlockGrass(new BlockPos(width, worldHeight + height - tempHeight)));
					} else {
						addBlockAll(new BlockGrass(new BlockPos(width, worldHeight + height - tempHeight)));
					}
				} else if (height > 0 && height < 4) {
					if (random.nextInt(height * 2) == 0) {
						addBlockAll(new BlockGrass(new BlockPos(width, worldHeight + height - tempHeight)));
					} else {
						addBlockAll(new BlockDirt(new BlockPos(width, worldHeight + height - tempHeight)));
					}
				} else if (height > 3 && height < 9) {
					addBlockAll(new BlockDirt(new BlockPos(width, worldHeight + height - tempHeight)));
				} else if (height > 8 && height < 12) {
					if (random.nextInt(height / 3) == 0) {
						addBlockAll(new BlockStone(new BlockPos(width, worldHeight + height - tempHeight)));
					} else {
						addBlockAll(new BlockDirt(new BlockPos(width, worldHeight + height - tempHeight)));
					}
				} else {
					addBlockAll(new BlockStone(new BlockPos(width, worldHeight + height - tempHeight)));
				}
			}
			
			amountGenerated = MathHelper.percentage(width, worldLength);
		}
		
		System.out.println(Console.info() + "Finished placing blocks!");
		System.out.println(Console.info() + "Removing blocks...");
		int ti = 0;
		for (int i = 0; i < allBlocks.size(); i++) {
			Block b = allBlocks.get(i);
			if (b.getBlockPosY() > getWorldHeight() * 2) {
				i--;
				removeBlockAll(b);
			} else {
				ti++;
			}
			
			amountGenerated = MathHelper.percentage(ti, allBlocks.size());
		}
		System.out.println(Console.info() + "Finished removing blocks!");
		
		System.out.println(Console.info() + "Placing player!");
		player = new EntityPlayer(((worldLength  / 2) * Block.getBlockSize()) - 12, + -44 - ((-15 + worldHeight) * -Block.getBlockSize()));
		allEntities.add(player);
		
		System.out.println(Console.info() + "Checking what block need to be active...");
		redoActives();
		System.out.println(Console.info() + "Finished checking what block need to be active!");
		
		isGeneratingWorld = false;
		doesWorldExist = true;
		System.out.println(Console.info(Console.WarningType.Info) + "-Finished creating the world!");
	}
	
	public static void clearWorld() {
		System.out.println(Console.info(Console.WarningType.Info) + "-Reseting the world...");
		System.out.println(Console.info() + "Reseting blocks...");
		allBlocks.clear();
		activeBlocks.clear();
		System.out.println(Console.info() + "Finished reseting blocks!");
		System.out.println(Console.info() + "Reseting entities...");
		allEntities.clear();
		activeEntities.clear();
		player = null;
		System.out.println(Console.info() + "Finished reseting entities!");
		doesWorldExist = false;
		System.out.println(Console.info(Console.WarningType.Info) + "-Finished reseting the world!");
	}
	
	public static void saveWorld(String name) {
		System.out.println(Console.info(Console.WarningType.Info) + "-Saving the world...");
		isSaving = true;
		Gson g = new Gson().newBuilder().create();
		FileWriter fileBlock = null, fileEntity = null, filePlayer = null, fileWorld = null;
		Entity[] es = new Entity[allEntities.size() - 1];
		int[] values = new int[3];
		
		if (!new File(LOC + name).exists()) {
			new File(LOC + name).mkdirs();
		}
		
		try {
			System.out.println(Console.info() + "Writing files...");
			fileBlock = new FileWriter(new File(LOC + name + "\\blocks" + TYPE));
			fileEntity = new FileWriter(new File(LOC + name + "\\entities" + TYPE));
			filePlayer = new FileWriter(new File(LOC + name + "\\player" + TYPE));
			fileWorld = new FileWriter(new File(LOC + name + "\\world" + TYPE2));
			
			values[0] = worldLength;
			values[1] = worldHeight;
			values[2] = worldSeed;
			
			if (allEntities.size() - 1 != 0) {
				for (int i = 0; i < allEntities.size(); i++) {
					if (!(es[i] instanceof EntityPlayer)) {
						es[i] = allEntities.get(i);
					}
				}
			}
			
			g.toJson(allBlocks, fileBlock);
			g.toJson(es, fileEntity);
			g.toJson(player, filePlayer);
			g.toJson(values ,fileWorld);
			
			fileBlock.flush();
			fileEntity.flush();
			filePlayer.flush();
			fileWorld.flush();
			System.out.println(Console.info() + "Finished writing files!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		isSaving = false;
		System.out.println(Console.info() + "-Finished saving the world!");
	}
	
	public static void loadWorld(String name) {
		System.out.println(Console.info(Console.WarningType.Info) + "-Loading the world...");
		isLoading = true;
		FileReader fileBlock = null, fileEntity = null, filePlayer = null, fileWorld = null;
		Gson g = new Gson();
		
		if (!new File(LOC + name).exists()) {
			if (!new File(LOC).exists()) {
				new File(LOC).mkdirs();
			}
			System.err.println(Console.info(Console.WarningType.Error) + "Cannot find world at : " + LOC +  name + "!");
			isLoading = false;
			return;
		} else if (!(new File(LOC + name + "\\blocks" + TYPE).exists())) {
			System.err.println(Console.info(Console.WarningType.Error) + "Cannot find : " + LOC + name + "\\blocks" + TYPE + "!");
			isLoading = false;
			return;
		} else if (!(new File(LOC + name + "\\entities" + TYPE).exists())) {
			System.err.println(Console.info(Console.WarningType.Error) + "Cannot find : " + LOC + name + "\\entities" + TYPE + "!");
			isLoading = false;
			return;
		} else if (!(new File(LOC + name + "\\player" + TYPE).exists())) {
			System.err.println(Console.info(Console.WarningType.Error) + "Cannot find : " + LOC + name + "\\player" + TYPE + "!");
			isLoading = false;
			return;
		} else if (!(new File(LOC + name + "\\world" + TYPE2).exists())) {
			System.err.println(Console.info(Console.WarningType.Error) + "Cannot find : " + LOC + name + "\\world" + TYPE2 + "!");
			isLoading = false;
			return;
		}
		
		try {
			System.out.println(Console.info() + "Reading files...");
			fileBlock = new FileReader(new File(LOC + name + "\\blocks" + TYPE));
			fileEntity = new FileReader(new File(LOC + name + "\\entities" + TYPE));
			filePlayer = new FileReader(new File(LOC + name + "\\player" + TYPE));
			fileWorld = new FileReader(new File(LOC + name + "\\world" + TYPE2));
			
			Block[] bs = g.fromJson(g.newJsonReader(fileBlock), Block[].class);
			Entity[] es = g.fromJson(g.newJsonReader(fileEntity), Entity[].class);
			int[] values = g.fromJson(g.newJsonReader(fileWorld), int[].class);
			System.out.println(Console.info() + "Finished Reading files!");
			
			System.out.println(Console.info() + "Setting settings...");
			worldLength = values[0];
			worldHeight = values[1];
			worldSeed = values[2];
			System.out.println(Console.info() + "Finished setting settings!");
			System.out.println(Console.info() + "Creating Blocks...");
			for (int i = 0; i < bs.length; i++) {
				addBlockAll(bs[i]);
			}
			System.out.println(Console.info() + "Finished creating blocks!");
			System.out.println(Console.info() + "Creating entities...");
			for (int i = 0; i < es.length; i++) {
				addEntityAll(es[i]);
				if (es[i] instanceof EntityPlayer) {
					System.err.println("player found");
					
				}
			}
			player = g.fromJson(g.newJsonReader(filePlayer), EntityPlayer.class);
			addEntityAll(player);
			Main.getCamera().setPosition(((worldLength / 2) * Block.getBlockSize()) - 12, -44 - (-15 * -Block.getBlockSize()));
			System.out.println(Console.info() + "Finished creating entites!");
			
			redoActives();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		isLoading = false;
		doesWorldExist = true;
		System.out.println(Console.info(Console.WarningType.Info) + "-Finished loading the world!");
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
	
	public static int findObjectInt(GameObject obj) {
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

	public static int getWorldLength() {
		return worldLength;
	}

	public static int getWorldHeight() {
		return worldHeight;
	}
	
	public static int getWorldSeed() {
		return worldSeed;
	}
	
	public static float getAmountGenerated() {
		return amountGenerated;
	}
	
	public static EntityPlayer getPlayer() {
		return player;
	}
}