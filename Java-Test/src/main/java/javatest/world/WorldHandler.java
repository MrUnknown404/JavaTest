package main.java.javatest.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.javatest.Main;
import main.java.javatest.blocks.Block;
import main.java.javatest.blocks.BlockStoneBrick;
import main.java.javatest.entity.Entity;
import main.java.javatest.entity.EntityItem;
import main.java.javatest.entity.entityliving.EntityDummy;
import main.java.javatest.entity.entityliving.EntityLiving;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.init.Items;
import main.java.javatest.items.ItemStack;
import main.java.javatest.util.Console;
import main.java.javatest.util.TickableGameObject;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.world.util.WorldInfo;

public class WorldHandler {

	//private static final String SAVE_LOC = "C:/Users/" + System.getProperty("user.name") + "/Documents/My Games/JavaTest/worlds/";
	
	private List<Block> allBlocks = new ArrayList<Block>();
	private List<Entity> allEntities = new ArrayList<Entity>();
	private List<Block> activeBlocks = new ArrayList<Block>();
	private List<Entity> activeEntities = new ArrayList<Entity>();
	
	private boolean wasCreated;
	private WorldInfo worldInfo = new WorldInfo();
	private Random random;
	private EntityPlayer player = null;;
	
	public void generateWorld(int xSize, int ySize, int seed) {
		Console.print(Console.WarningType.Info, "-Creating a new World..");
		
		worldInfo.worldLength = xSize;
		worldInfo.worldHeight = ySize;
		worldInfo.worldSeed = seed;
		
		random = new Random(seed);
		
		Main.getCamera().setPosition(((worldInfo.worldLength / 2) * Block.getBlockSize()), -15 * -Block.getBlockSize());
		
		Console.print("Placing blocks...");
		for (int width = 0; width < worldInfo.worldLength; width++) {
			for (int height = 0; height < worldInfo.worldHeight; height++) {
				addObjectAll(new BlockStoneBrick(new BlockPos(width, height)));
			}
		}
		Console.print("Finished placing blocks!");
		
		Console.print("Placing player && dummy!");
		player = new EntityPlayer(worldInfo.worldLength  / 2 * Block.getBlockSize() - 12, -64);
		addObjectAll(new EntityDummy(worldInfo.worldLength  / 2 * Block.getBlockSize() - 60, -64));
		addObjectAll(new EntityDummy(worldInfo.worldLength  / 2 * Block.getBlockSize() - 12, -64));
		addObjectAll(new EntityDummy(worldInfo.worldLength  / 2 * Block.getBlockSize() + 36, -64));
		
		Console.print("Checking what block need to be active...");
		redoActives();
		Console.print("Finished checking what block need to be active!");
		wasCreated = true;
		Console.print(Console.WarningType.Info, "-Finished creating the world!");
	}
	
	/** delete this */
	public void clearWorld() {
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
		Console.print(Console.WarningType.Info, "-Finished reseting the world!");
	}
	
	public void tick() {
		if (getPlayer() != null) {
			getPlayer().tick();
			getPlayer().tickAlive();
		}
		
		for (int i = 0; i < activeBlocks.size(); i++) {
			Block obj = activeBlocks.get(i);
			
			if (obj == null) {
				return;
			}
			
			obj.tick();
		}
		
		for (int i = 0; i < activeEntities.size(); i++) {
			Entity obj = activeEntities.get(i);
			
			if (obj == null) {
				return;
			}
			
			obj.tick();
			if (obj instanceof EntityLiving) {
				((EntityLiving) obj).tickAlive();
			}
		}
	}
	
	public void redoActives() {
		activeBlocks.clear();
		activeEntities.clear();
		
		Rectangle rect = new Rectangle((int) -Main.getCamera().getPositionX(), (int) -Main.getCamera().getPositionY(), Main.WIDTH_DEF, Main.HEIGHT_DEF);
		
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
	
	public void redoSpecificActiveObject(TickableGameObject obj) {
		if (obj == null) {
			Console.print(Console.WarningType.Error, "object is null!");
			return;
		}
		
		Rectangle rect = new Rectangle((int) -Main.getCamera().getPositionX(), (int) -Main.getCamera().getPositionY(), Main.WIDTH_DEF, Main.HEIGHT_DEF);
		
		if (obj instanceof Block) {
			activeBlocks.remove(obj);
			if (obj.getBoundsAll().intersects(rect) && !obj.getIsActive() && allBlocks.contains(obj)) {
				obj.setIsActive(true);
				activeBlocks.add((Block) obj);
			} else {
				obj.setIsActive(false);
			}
		} else if (obj instanceof Entity) {
			activeEntities.remove(obj);
			if ((((obj.getBoundsAll().intersects(rect) && !obj.getIsActive()) || ((Entity) obj).getEntityProperties().getAlwaysLoad())) && allEntities.contains(obj)) {
				obj.setIsActive(true);
				activeEntities.add((Entity) obj);
			} else {
				obj.setIsActive(false);
			}
		}
	}
	
	public void addObjectAll(TickableGameObject obj) {
		if (obj == null) {
			Console.print(Console.WarningType.Error, "object is null!");
			return;
		}
		
		if (obj instanceof Block) {
			if (!allBlocks.contains(obj)) {
				allBlocks.add((Block) obj);
				redoSpecificActiveObject(obj);
			}
		} else if (obj instanceof Entity) {
			if (!allEntities.contains(obj)) {
				allEntities.add((Entity) obj);
				redoSpecificActiveObject(obj);
			}
		}
	}
	
	public void removeObjectAll(TickableGameObject obj, boolean shouldDrop) {
		if (obj == null) {
			Console.print(Console.WarningType.Error, "object is null!");
		}
		
		if (obj instanceof Block) {
			if (allBlocks.contains(obj)) {
				if (shouldDrop) {
					Main.getWorldHandler().addObjectAll(new EntityItem(obj.getPositionX() + 2, obj.getPositionY() + 2, new ItemStack(1, Items.findItem(((Block) obj).getBlockProperties().getBlockType().toString()))));
				}
				allBlocks.remove(obj);
				redoSpecificActiveObject(obj);
				if (activeBlocks.contains(obj)) {
					activeBlocks.remove(obj);
				}
			}
		} else if (obj instanceof Entity) {
			if (allEntities.contains(obj)) {
				allEntities.remove(obj);
				redoSpecificActiveObject(obj);
				if (activeEntities.contains(obj)) {
					activeEntities.remove(obj);
				}
			}
		}
	}
	
	public List<Block> getAllBlocks() {
		return allBlocks;
	}
	
	public List<Entity> getAllEntities() {
		return allEntities;
	}
	
	public List<Block> getActiveBlocks() {
		return activeBlocks;
	}
	
	public List<Entity> getActiveEntities() {
		return activeEntities;
	}
	
	public WorldInfo getWorldInfo() {
		return worldInfo;
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public Random getSeededRandom() {
		return random;
	}
	
	public boolean getWasCreated() {
		return wasCreated;
	}
}