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
import main.java.javatest.entity.EntityItem;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.init.Items;
import main.java.javatest.items.ItemStack;
import main.java.javatest.util.Console;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.world.util.WorldInfo;

public class World {
	
	List<Block> allBlocks = new ArrayList<Block>();
	List<Entity> allEntities = new ArrayList<Entity>();
	List<EntityItem> allItemEntities = new ArrayList<EntityItem>();
	List<Block> activeBlocks = new ArrayList<Block>();
	List<Entity> activeEntities = new ArrayList<Entity>();
	List<EntityItem> activeItemEntities = new ArrayList<EntityItem>();
	
	private WorldInfo worldInfo = new WorldInfo();
	private Random random;
	EntityPlayer player = null;;
	
	public World() {}
	
	public World(int xSize, int ySize, int seed, WorldHandler handler) {
		Console.print(Console.WarningType.Info, "-Creating a new ..");
		handler.isGeneratingWorld = true;
		
		worldInfo.worldLength = xSize;
		worldInfo.worldHeight = ySize;
		worldInfo.worldSeed = seed;
		
		random = new Random(seed);
		
		int tempInt = 0;
		int tempHeight = 0;
		
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
					addObjectAll(new BlockGrass(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
				} else if (height > 0 && height < 4) {
					if (random.nextInt(height * 2) == 0) {
						addObjectAll(new BlockGrass(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
					} else {
						addObjectAll(new BlockDirt(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
					}
				} else if (height > 3 && height < 9) {
					addObjectAll(new BlockDirt(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
				} else if (height > 8 && height < 12) {
					if (random.nextInt(height / 3) == 0) {
						addObjectAll(new BlockStone(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
					} else {
						addObjectAll(new BlockDirt(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
					}
				} else {
					addObjectAll(new BlockStone(new BlockPos(width, worldInfo.worldHeight + height - tempHeight)));
				}
			}
			
			handler.amountGenerated = MathHelper.percentage(width, worldInfo.worldLength);
		}
		
		Console.print("Finished placing blocks!");
		Console.print("Removing blocks...");
		int ti = 0;
		for (int i = 0; i < allBlocks.size(); i++) {
			Block b = allBlocks.get(i);
			if (b.getBlockPosY() > worldInfo.worldHeight * 2) {
				i--;
				removeObjectAll(b, false);
			} else {
				ti++;
			}
			
			handler.amountGenerated = MathHelper.percentage(ti, allBlocks.size());
		}
		Console.print("Finished removing blocks!");
		
		Console.print("Placing player!");
		player = new EntityPlayer(((worldInfo.worldLength  / 2) * Block.getBlockSize()) - 12, + -44 - ((-15 + worldInfo.worldHeight) * -Block.getBlockSize()));
		
		Console.print("Checking what block need to be active...");
		redoActives();
		Console.print("Finished checking what block need to be active!");
		
		handler.isGeneratingWorld = false;
		handler.doesWorldExist = true;
		Console.print(Console.WarningType.Info, "-Finished creating the world!");
	}
	
	public void redoActives() {
		activeBlocks.clear();
		activeEntities.clear();
		activeItemEntities.clear();
		
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
		
		for (int i = 0; i < allItemEntities.size(); i++) {
			EntityItem obj = allItemEntities.get(i);
			if (obj == null) {
				continue;
			}
			
			if (obj.getBoundsAll().intersects(rect) || obj.getEntityProperties().getAlwaysLoad()) {
				obj.setIsActive(true);
				activeItemEntities.add(obj);
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
	
	public void redoSpecificActiveObject(GameObject obj) {
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
		} else if (obj instanceof EntityItem) {
			activeItemEntities.remove(obj);
			if ((((obj.getBoundsAll().intersects(rect) && !obj.getIsActive()) || ((EntityItem) obj).getEntityProperties().getAlwaysLoad())) && allItemEntities.contains(obj)) {
				obj.setIsActive(true);
				activeItemEntities.add((EntityItem) obj);
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
	
	public void addObjectAll(GameObject obj) {
		if (obj == null) {
			Console.print(Console.WarningType.Error, "object is null!");
			return;
		}
		
		if (obj instanceof Block) {
			if (!allBlocks.contains(obj)) {
				allBlocks.add((Block) obj);
				redoSpecificActiveObject(obj);
			}
		} else if (obj instanceof EntityItem) {
			if (!allItemEntities.contains(obj)) {
				allItemEntities.add((EntityItem) obj);
				redoSpecificActiveObject(obj);
			}
		} else if (obj instanceof Entity) {
			if (!allEntities.contains(obj)) {
				allEntities.add((Entity) obj);
				redoSpecificActiveObject(obj);
			}
		}
	}
	
	public void removeObjectAll(GameObject obj, boolean didPlayerBreak) {
		if (obj == null) {
			Console.print(Console.WarningType.Error, "object is null!");
		}
		
		if (obj instanceof Block) {
			if (allBlocks.contains(obj)) {
				if (didPlayerBreak) {
					Main.getWorldHandler().getWorld().addObjectAll(new EntityItem(obj.getPositionX() + 2, obj.getPositionY() + 2, new ItemStack(1, Items.findItem(((Block) obj).getBlockProperties().getBlockType().toString()))));
				}
				allBlocks.remove(obj);
				redoSpecificActiveObject(obj);
				if (activeBlocks.contains(obj)) {
					activeBlocks.remove(obj);
				}
			}
		} else if (obj instanceof EntityItem) {
			if (allItemEntities.contains(obj)) {
				allItemEntities.remove(obj);
				redoSpecificActiveObject(obj);
				if (activeItemEntities.contains(obj)) {
					activeItemEntities.remove(obj);
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
	
	public int findObjectInt(GameObject obj) {
		if (obj == null) {
			Console.print(Console.WarningType.Error, "object is null!");
		}
		
		if (obj instanceof Block) {
			for (int i = 0; i < allBlocks.size(); i++) {
				Block o = allBlocks.get(i);
				if (o == obj) {
					return i;
				}
			}
		} else if (obj instanceof EntityItem) {
			for (int i = 0; i < allItemEntities.size(); i++) {
				Entity o = allItemEntities.get(i);
				if (o == obj) {
					return i;
				}
			}
		} else if (obj instanceof Entity) {
			for (int i = 0; i < allEntities.size(); i++) {
				Entity o = allEntities.get(i);
				if (o == obj) {
					return i;
				}
			}
		}
		Console.print(Console.WarningType.Error, "Cannot find object : " + obj.toString());
		return 0;
	}
	
	public List<Block> getAllBlocks() {
		return allBlocks;
	}
	
	public List<Entity> getAllEntities() {
		return allEntities;
	}
	
	public List<EntityItem> getAllItemEntities() {
		return allItemEntities;
	}
	
	public List<Block> getActiveBlocks() {
		return activeBlocks;
	}
	
	public List<Entity> getActiveEntities() {
		return activeEntities;
	}
	
	public List<EntityItem> getActiveItemEntities() {
		return activeItemEntities;
	}
	
	public WorldInfo getWorldInfo() {
		return worldInfo;
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
}
