package main.java.javatest.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.javatest.Main;
import main.java.javatest.blocks.util.Block;
import main.java.javatest.client.MouseInput;
import main.java.javatest.client.gui.DebugHud;
import main.java.javatest.entity.Entity;
import main.java.javatest.entity.entityliving.EntityDummy;
import main.java.javatest.entity.entityliving.EntityLiving;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.Console;
import main.java.javatest.util.TickableGameObject;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2i;
import main.java.javatest.world.util.Chunk;
import main.java.javatest.world.util.WorldInfo;

public class WorldHandler {

	//private static final String SAVE_LOC = "C:/Users/" + System.getProperty("user.name") + "/Documents/My Games/JavaTest/worlds/";
	
	private List<Chunk> allChunks = new ArrayList<Chunk>();
	private List<Chunk> activeChunks = new ArrayList<Chunk>();
	
	private List<Entity> allEntities = new ArrayList<Entity>();
	private List<Entity> activeEntities = new ArrayList<Entity>();
	
	private boolean wasCreated;
	private WorldInfo worldInfo = new WorldInfo();
	private Random random;
	private EntityPlayer player = null;
	
	public void generateWorld(int xSize, int ySize, int seed) {
		Console.print(Console.WarningType.Info, "Creating a new World..");
		
		worldInfo.worldLength = xSize;
		worldInfo.worldHeight = ySize;
		worldInfo.worldSeed = seed;
		
		random = new Random(seed);
		
		Main.getCamera().setPosition(((worldInfo.worldLength / 2) * Block.getBlockSize()), -15 * -Block.getBlockSize());
		
		int ti = 0;
		
		Console.print("Placing chunks...");
		for (int height = 0; height < 200; height++) {
			for (int width = 0; width < 500; width++) {
				allChunks.add(new Chunk(width, height));
				
				ti++;
				DebugHud.genPerc = MathHelper.percentage(ti, 500 * 200);
			}
		}
		Console.print("Finished placing chunks!");
		
		ti = 0;
		
		Console.print("Placing player && dummy!");
		player = new EntityPlayer(worldInfo.worldLength    / 2 * Block.getBlockSize() - 12, -64);
		addObjectAll(new EntityDummy(worldInfo.worldLength / 2 * Block.getBlockSize() - 60, -64));
		addObjectAll(new EntityDummy(worldInfo.worldLength / 2 * Block.getBlockSize() - 12, -64));
		addObjectAll(new EntityDummy(worldInfo.worldLength / 2 * Block.getBlockSize() + 36, -64));
		
		Console.print("Checking what block need to be active...");
		redoActives();
		Console.print("Finished checking what block need to be active!");
		wasCreated = true;
		Console.print(Console.WarningType.Info, "Finished creating the world!");
	}
	
	/** delete this */
	public void clearWorld() {
		Console.print(Console.WarningType.Info, "Reseting the world...");
		Console.print("Reseting chunks...");
		allChunks.clear();
		activeChunks.clear();
		Console.print("Finished reseting chunks!");
		Console.print("Reseting entities...");
		allEntities.clear();
		activeEntities.clear();
		player = null;
		Console.print("Finished reseting entities!");
		Console.print(Console.WarningType.Info, "Finished reseting the world!");
	}
	
	public void tick() {
		if (getPlayer() != null) {
			getPlayer().tick();
			getPlayer().tickAlive();
		}
		
		for (int i = 0; i < activeChunks.size(); i++) {
			Chunk cnk = activeChunks.get(i);
			
			if (cnk == null) {
				return;
			}
			
			cnk.tick();
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
		activeChunks.clear();
		activeEntities.clear();
		
		Rectangle rect = new Rectangle((int) -Main.getCamera().getPositionX(), (int) -Main.getCamera().getPositionY(), Main.WIDTH_DEF, Main.HEIGHT_DEF);
		
		for (int i = 0; i < allChunks.size(); i++) {
			Chunk cnk = allChunks.get(i);
			
			if (cnk == null) {
				continue;
			}
			
			if (cnk.getChunkActiveBounds().intersects(rect)) {
				activeChunks.add(cnk);
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
		
		if (obj instanceof Entity) {
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
		
		if (obj instanceof Entity) {
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
		
		if (obj instanceof Entity) {
			if (allEntities.contains(obj)) {
				allEntities.remove(obj);
				redoSpecificActiveObject(obj);
				if (activeEntities.contains(obj)) {
					activeEntities.remove(obj);
				}
			}
		}
	}
	
	public List<Chunk> getChunksEntityIsIn(Entity entity) {
		List<Chunk> cnks = new ArrayList<Chunk>();
		for (int i = 0; i < activeChunks.size(); i++) {
			Chunk cnk = activeChunks.get(i);
			if (cnk.getChunkActiveBounds().intersects(entity.getBoundsAll())) {
				cnks.add(cnk);
			}
		}
		return cnks;
	}
	
	public Chunk getChunkAtMouse() {
		for (int i = 0; i < activeChunks.size(); i++) {
			Chunk cnk = activeChunks.get(i);
			
			Vec2i vec = new Vec2i(MouseInput.vec.x - Main.getCamera().getPositionX(), MouseInput.vec.y - Main.getCamera().getPositionY());
			
			if (cnk != null && cnk.getChunkBounds().intersects(new Rectangle(vec.x, vec.y, 1, 1))) {
				return cnk;
			}
		}
		return null;
	}
	
	public List<Chunk> getAllChunks() {
		return allChunks;
	}
	
	public List<Chunk> getActiveChunks() {
		return activeChunks;
	}
	
	public List<Entity> getAllEntities() {
		return allEntities;
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