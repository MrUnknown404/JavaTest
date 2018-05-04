package main.java.javatest.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.javatest.blocks.Block;
import main.java.javatest.entity.Entity;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.init.EnumBlocks;
import main.java.javatest.util.Console;
import main.java.javatest.util.Resource;
import main.java.javatest.world.World;

public class Renderer {

	private Map<String, BufferedImage> hashImages = new HashMap<String, BufferedImage>();
	private List<BufferedImage> valueList = new ArrayList<BufferedImage>();
	private List<String> keyList = new ArrayList<String>();
	
	public void findTextures() {
		Console.print(Console.WarningType.Info, "-Finding all textures...");
		Console.print("Finding all block textures...");
		for (int i = 1; i < EnumBlocks.values().length; i++) {
			hashImages.put(EnumBlocks.getNumber(i).toString(), Resource.getTexture(Resource.ResourceType.blocks, EnumBlocks.getNumber(i)));
		}
		Console.print("Found all block textures!");
		
		Console.print("Finding all entity textures...");
		for (int i = 0; i < EntityProperties.EntityType.values().length; i++) {
			hashImages.put(EntityProperties.EntityType.getNumber(i).toString(), Resource.getTexture(Resource.ResourceType.entity, EntityProperties.EntityType.getNumber(i)));
		}
		Console.print("Found all entity textures!");
		Console.print(Console.WarningType.Info, "-Found all Textures!");
	
		Console.print("Setting textures for use...");
		valueList = new ArrayList<BufferedImage>(hashImages.values());
		keyList = new ArrayList<String>(hashImages.keySet());
		Console.print("Finished setting textures!");
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < World.getActiveBlocks().size(); i++) {
			Block obj = World.getActiveBlocks().get(i);
			
			if (obj == null) {
				return;
			}
			
			for (int i2 = 0; i2 < valueList.size(); i2++) {
				if (keyList.get(i2).toString() == ((Block) obj).getBlockProperties().getBlockType().toString()) {
					g.drawImage(valueList.get(i2), (int) obj.getPositionX(), (int) obj.getPositionY(), Block.getBlockSize(), Block.getBlockSize(), null);
				}
			}
			
			//temporary lighting system!
			if (obj.getLightLevel() != 0) {
				g.setColor(new Color(0, 0, 0, obj.getLightLevel()));
				g.fillRect((int) obj.getPositionX(), (int) obj.getPositionY(), obj.getWidth(), obj.getHeight());
			}
		}
		
		for (int i = 0; i < World.getActiveEntities().size(); i++) {
			Entity obj = World.getActiveEntities().get(i);
			
			if (obj == null) {
				return;
			}
			
			if (obj instanceof Entity) {
				for (int i2 = 0; i2 < valueList.size(); i2++) {
					if (keyList.get(i2).toString() == ((Entity) obj).getEntityProperties().getEntityType().toString()) {
						g.drawImage(valueList.get(i2), (int) obj.getPositionX(), (int) obj.getPositionY(), obj.getWidth(), obj.getHeight(), null);
					}
				}
			}
		}
	}
}
