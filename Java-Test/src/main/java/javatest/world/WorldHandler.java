package main.java.javatest.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import main.java.javatest.blocks.Block;
import main.java.javatest.entity.Entity;
import main.java.javatest.entity.EntityItem;
import main.java.javatest.entity.entityliving.EntityLiving;
import main.java.javatest.util.Console;

public class WorldHandler {

	private static final String LOC = "C:/Users/" + System.getProperty("user.name") + "/Documents/My Games/JavaTest/worlds/";
	private static final String TYPE = ".sav";//, TYPE2 = ".properties";//, TYPE3 = ".config";
	
	public boolean isGeneratingWorld, doesWorldExist, isSaving, isLoading;
	private World world = new World();
	float amountGenerated, amountLoaded, amountSaved;
	
	public void generateWorld(int xSize, int ySize, int seed) {
		world = new World(xSize, ySize, seed, this);
	}
	
	public void saveWorld(String name) {
		isSaving = true;
		Gson g  = new Gson().newBuilder().create();
		FileWriter fileWorld = null;
		
		if (!(new File(LOC + name).exists())) {
			new File(LOC + name).mkdirs();
		}
		
		try {
			fileWorld = new FileWriter(new File(LOC + name + "/world" + TYPE));
			
			g.toJson(world, fileWorld);
			
			fileWorld.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		isSaving = false;
	}
	
	public void loadWorld(String name) {
		isLoading = true;
		Gson g = new Gson();
		FileReader fileWorld = null;
		
		if (!(new File(LOC + name).exists())) {
			new File(LOC + name).mkdirs();
			Console.print(Console.WarningType.Error, "World at " + LOC + name + " does not exist!");
			return;
		} else if (!(new File(LOC + name + "/world" + TYPE).exists())) {
			Console.print(Console.WarningType.Error, "World at " + LOC + name + " does not exist!");
			return;
		}
		
		try {
			fileWorld = new FileReader(new File(LOC + name + "/world" + TYPE));
			
			world = g.fromJson(fileWorld, World.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		doesWorldExist = true;
		isLoading = false;
	}
	
	public void clearWorld() {
		Console.print(Console.WarningType.Info, "-Reseting the world...");
		Console.print("Reseting blocks...");
		world.allBlocks.clear();
		world.activeBlocks.clear();
		Console.print("Finished reseting blocks!");
		Console.print("Reseting entities...");
		world.allEntities.clear();
		world.activeEntities.clear();
		world.allItemEntities.clear();
		world.activeItemEntities.clear();
		world.player = null;
		Console.print("Finished reseting entities!");
		doesWorldExist = false;
		Console.print(Console.WarningType.Info, "-Finished reseting the world!");
	}
	
	public void tick() {
		if (world.getPlayer() != null) {
			world.getPlayer().tick();
			world.getPlayer().tickAlive();
		}
		
		for (int i = 0; i < world.activeBlocks.size(); i++) {
			Block obj = world.activeBlocks.get(i);
			
			if (obj == null) {
				return;
			}
			
			obj.tick();
		}
		
		for (int i = 0; i < world.activeItemEntities.size(); i++) {
			EntityItem obj = world.activeItemEntities.get(i);
			
			if (obj == null) {
				return;
			}
			
			obj.tick();
		}
		
		for (int i = 0; i < world.activeEntities.size(); i++) {
			Entity obj = world.activeEntities.get(i);
			
			if (obj == null) {
				return;
			}
			
			obj.tick();
			if (obj instanceof EntityLiving) {
				((EntityLiving) obj).tickAlive();
			}
		}
	}
	
	public void gameTick() {
		if (world.getPlayer() != null) {
			world.getPlayer().gameTick();
			world.getPlayer().gameTickAlive();
		}
		
		for (int i = 0; i < world.activeBlocks.size(); i++) {
			Block obj = world.activeBlocks.get(i);
			
			if (obj != null && !obj.getIsActive()) {
				continue;
			} else if (obj == null) {
				return;
			}
			
			obj.gameTick();
		}
		
		for (int i = 0; i < world.activeEntities.size(); i++) {
			Entity obj = world.activeEntities.get(i);
			
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
	
	public float getAmountGenerated() {
		return amountGenerated;
	}
	
	public float getAmountLoaded() {
		return amountLoaded;
	}
	
	public float getAmountSaved() {
		return amountSaved;
	}
	
	public World getWorld() {
		return world;
	}
}