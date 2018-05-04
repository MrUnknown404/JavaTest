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
import main.java.javatest.world.util.WorldInfo;

public class World {

	public static boolean isGeneratingWorld, doesWorldExist, isSaving, isLoading;
	private static final String LOC = "C:/Users/" + System.getProperty("user.name") + "/Documents/My Games/JavaTest/worlds/";
	private static final String TYPE = ".sav", TYPE2 = ".properties";//, TYPE3 = ".config";
	
	private static float amountGenerated, amountLoaded, amountSaved;
	private static Random random;
	private static WorldInfo worldInfo = new WorldInfo();
	
	private static List<Block> allBlocks = new ArrayList<Block>();
	private static List<Entity> allEntities = new ArrayList<Entity>();
	private static List<Block> activeBlocks = new ArrayList<Block>();
	private static List<Entity> activeEntities = new ArrayList<Entity>();
	private static EntityPlayer player;
	
	public static void generateWorld(int xSize, int ySize, int seed) {
		Console.print(Console.WarningType.Info, "-Creating a new world...");
		isGeneratingWorld = true;
		
		worldInfo.worldLength = xSize;
		worldInfo.worldHeight = ySize;
		worldInfo.worldSeed = seed;
		
		random = new Random(seed);
		
		int tempInt = 0;
		int tempHeight = 0;
		
		Console.print("Moving Camera!");
		Main.getCamera().setPosition(((worldInfo.worldLength / 2) * Block.getBlockSize()), -15 * -Block.getBlockSize());
		
		Console.print("Placing blocks...");
		for (int width = 0; width < worldInfo.worldLength; width++) {
			for (int height = 0; height < worldInfo.worldHeight + 15; height++) {
				if (height == 0) {
					if (tempInt != 0) {
						tempInt--;
					} else if (tempHeight == 15) {
						tempHeight--;
						tempInt += 3;
					} else if (tempHeight == -15) {
						tempHeight++;
						tempInt += 3;
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
					}
					addBlockAll(new BlockGrass(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
				} else if (height > 0 && height < 4) {
					if (random.nextInt(height * 2) == 0) {
						addBlockAll(new BlockGrass(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
					} else {
						addBlockAll(new BlockDirt(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
					}
				} else if (height > 3 && height < 9) {
					addBlockAll(new BlockDirt(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
				} else if (height > 8 && height < 12) {
					if (random.nextInt(height / 3) == 0) {
						addBlockAll(new BlockStone(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
					} else {
						addBlockAll(new BlockDirt(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
					}
				} else {
					addBlockAll(new BlockStone(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
				}
			}
			
			amountGenerated = MathHelper.percentage(width, worldInfo.worldLength);
		}
		
		Console.print("Finished placing blocks!");
		Console.print("Removing blocks...");
		int ti = 0;
		for (int i = 0; i < allBlocks.size(); i++) {
			Block b = allBlocks.get(i);
			if (b.getBlockPosY() > worldInfo.worldHeight * 2) {
				i--;
				removeBlockAll(b);
			} else {
				ti++;
			}
			
			amountGenerated = MathHelper.percentage(ti, allBlocks.size());
		}
		Console.print("Finished removing blocks!");
		
		Console.print("Placing player!");
		player = new EntityPlayer(((worldInfo.worldLength  / 2) * Block.getBlockSize()) - 12, + -44 - ((-15 + worldInfo.worldHeight) * -Block.getBlockSize()));
		allEntities.add(player);
		
		Console.print("Checking what block need to be active...");
		redoActives();
		Console.print("Finished checking what block need to be active!");
		
		isGeneratingWorld = false;
		doesWorldExist = true;
		Console.print(Console.WarningType.Info, "-Finished creating the world!");
	}
	
	public static void clearWorld() {
		Console.print(Console.WarningType.Info, "-Reseting the world...");
		Console.print("Reseting blocks...");
		allBlocks.clear();
		activeBlocks.clear();
		Console.print("Finished reseting blocks!");
		Console.print("Reseting entities...");
		allEntities.clear();
		activeEntities.clear();
		player = null;
		Console.print("Finished reseting entities!");
		doesWorldExist = false;
		Console.print(Console.WarningType.Info, "-Finished reseting the world!");
	}
	
	public static void saveWorld(String name) {
		Console.print(Console.WarningType.Info, "-Saving the world...");
		isSaving = true;
		Gson g = new Gson().newBuilder().setPrettyPrinting().create();
		FileWriter fileBlock = null, fileEntity = null, filePlayer = null, fileWorld = null;
		Entity[] es = new Entity[allEntities.size() - 1];
		Block[] bs = new Block[allBlocks.size()];
		
		if (!new File(LOC + name).exists()) {
			new File(LOC + name).mkdirs();
		}
		
		try {
			Console.print("Writing files...");
			fileBlock = new FileWriter(new File(LOC + name + "/blocks" + TYPE));
			fileEntity = new FileWriter(new File(LOC + name + "/entities" + TYPE));
			filePlayer = new FileWriter(new File(LOC + name + "/player" + TYPE));
			fileWorld = new FileWriter(new File(LOC + name + "/world" + TYPE2));
			
			if (allEntities.size() - 1 != 0) {
				for (int i = 0; i < allEntities.size(); i++) {
					if (!(es[i] instanceof EntityPlayer)) {
						amountSaved = MathHelper.percentage(i, allEntities.size());
						es[i] = allEntities.get(i);
					}
				}
			}
			
			for (int i = 0; i < allBlocks.size(); i++) {
				amountSaved = MathHelper.percentage(i, allBlocks.size());
				bs[i] = allBlocks.get(i);
			}
			
			g.toJson(bs, fileBlock);
			g.toJson(es, fileEntity);
			g.toJson(player, filePlayer);
			g.toJson(worldInfo, fileWorld);
			
			fileBlock.flush();
			fileEntity.flush();
			filePlayer.flush();
			fileWorld.flush();
			Console.print("Finished writing files!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		isSaving = false;
		Console.print("-Finished saving the world!");
	}
	
	public static void loadWorld(String name) {
		Console.print(Console.WarningType.Info, "-Loading the world...");
		isLoading = true;
		FileReader fileBlock = null, fileEntity = null, filePlayer = null, fileWorld = null;
		Gson g = new Gson();
		
		if (!new File(LOC + name).exists()) {
			if (!new File(LOC).exists()) {
				new File(LOC).mkdirs();
			}
			Console.print(Console.WarningType.Error, "Cannot find world at : " + LOC +  name + "!");
			isLoading = false;
			return;
		} else if (!(new File(LOC + name + "/blocks" + TYPE).exists())) {
			Console.print(Console.WarningType.Error, "Cannot find : " + LOC + name + "/blocks" + TYPE + "!");
			isLoading = false;
			return;
		} else if (!(new File(LOC + name + "/entities" + TYPE).exists())) {
			Console.print(Console.WarningType.Error, "Cannot find : " + LOC + name + "/entities" + TYPE + "!");
			isLoading = false;
			return;
		} else if (!(new File(LOC + name + "/player" + TYPE).exists())) {
			Console.print(Console.WarningType.Error, "Cannot find : " + LOC + name + "/player" + TYPE + "!");
			isLoading = false;
			return;
		} else if (!(new File(LOC + name + "/world" + TYPE2).exists())) {
			Console.print(Console.WarningType.Error, "Cannot find : " + LOC + name + "/world" + TYPE2 + "!");
			isLoading = false;
			return;
		}
		
		try {
			Console.print("Reading files...");
			fileBlock = new FileReader(new File(LOC + name + "/blocks" + TYPE));
			fileEntity = new FileReader(new File(LOC + name + "/entities" + TYPE));
			filePlayer = new FileReader(new File(LOC + name + "/player" + TYPE));
			fileWorld = new FileReader(new File(LOC + name + "/world" + TYPE2));
			
			Block[] bs = g.fromJson(g.newJsonReader(fileBlock), Block[].class);
			Entity[] es = g.fromJson(g.newJsonReader(fileEntity), Entity[].class);
			WorldInfo wi = g.fromJson(g.newJsonReader(fileWorld), WorldInfo.class);
			Console.print("Finished Reading files!");
			
			Console.print("Creating Blocks...");
			for (int i = 0; i < bs.length; i++) {
				amountLoaded = MathHelper.percentage(i, bs.length);
				addBlockAll(bs[i]);
			}
			Console.print("Finished creating blocks!");
			Console.print("Creating entities...");
			for (int i = 0; i < es.length; i++) {
				amountLoaded = MathHelper.percentage(i, es.length);
				addEntityAll(es[i]);
			}
			Console.print("Creating player...");
			player = g.fromJson(g.newJsonReader(filePlayer), EntityPlayer.class);
			addEntityAll(player);
			Main.getCamera().setPosition(((worldInfo.worldLength / 2) * Block.getBlockSize()) - 12, -44 - (-15 * -Block.getBlockSize()));
			Console.print("Finished creating player!");
			redoActives();
			Console.print("Finished creating entites!");
			
			Console.print("Setting settings...");
			worldInfo = wi;
			Console.print("Finished setting settings!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		isLoading = false;
		doesWorldExist = true;
		Console.print(Console.WarningType.Info, "-Finished loading the world!");
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
			Console.print(Console.WarningType.Error, "object is null or Object already exists!");
		}
	}
	
	public static void addEntityAll(Entity object) {
		if (object != null && !allEntities.contains(object)) {
			allEntities.add(object);
		} else {
			Console.print(Console.WarningType.Error, "object is null or Object already exists!");
		}
	}
	
	public static void removeBlockAll(Block object) {
		if (object != null && allBlocks.contains(object)) {
			allBlocks.remove(object);
			redoSpecificActiveBlock(object);
		} else {
			Console.print(Console.WarningType.Error, "object is null!");
		}
	}
	
	public static void removeEntityAll(Entity object) {
		if (object != null && allEntities.contains(object)) {
			allEntities.remove(object);
			redoSpecificActiveEntity(object);
		} else {
			Console.print(Console.WarningType.Error, "object is null!");
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
		Console.print(Console.WarningType.Error, "Cannot find object : " + obj.toString());
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
	
	public static WorldInfo getWorldInfo() {
		return worldInfo;
	}
	
	public static float getAmountGenerated() {
		return amountGenerated;
	}
	
	public static float getAmountLoaded() {
		return amountLoaded;
	}
	
	public static float getAmountSaved() {
		return amountSaved;
	}
	
	public static EntityPlayer getPlayer() {
		return player;
	}
}