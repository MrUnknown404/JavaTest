package main.java.javatest.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.javatest.blocks.Block;
import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.entity.Entity;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.util.Console;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.ObjectHandler;
import main.java.javatest.util.Resource;

public class Renderer {

	private Map<String, BufferedImage> hashImages = new HashMap<String, BufferedImage>();
	private List<BufferedImage> valueList = new ArrayList<BufferedImage>();
	private List<String> keyList = new ArrayList<String>();
	
	public void findTextures() {
		System.out.println(Console.info(Console.WarningType.Info) + "-Finding all textures...");
		System.out.println(Console.info(Console.WarningType.Info) + "Finding all block textures...");
		for (int i = 1; i < BlockProperties.BlockType.values().length; i++) {
			hashImages.put(BlockProperties.BlockType.getNumber(i).toString(), Resource.getTexture(Resource.ResourceType.blocks, BlockProperties.BlockType.getNumber(i)));
		}
		System.out.println(Console.info(Console.WarningType.Info) + "Found all block textures!");
		
		System.out.println(Console.info(Console.WarningType.Info) + "Finding all entity textures...");
		for (int i = 0; i < EntityProperties.EntityType.values().length; i++) {
			hashImages.put(EntityProperties.EntityType.getNumber(i).toString(), Resource.getTexture(Resource.ResourceType.entity, EntityProperties.EntityType.getNumber(i)));
		}
		System.out.println(Console.info(Console.WarningType.Info) + "Found all entity textures!");
		System.out.println(Console.info(Console.WarningType.Info) + "-Found all Textures!");
	
		System.out.println(Console.info(Console.WarningType.Info) + "Setting textures for use...");
		valueList = new ArrayList<BufferedImage>(hashImages.values());
		keyList = new ArrayList<String>(hashImages.keySet());
		System.out.println(Console.info(Console.WarningType.Info) + "Finished setting textures!");
	}
	
	static int i;
	
	public void render(Graphics g) {
		for (i = 0; i < ObjectHandler.getObjectsAll().size(); i++) {
			GameObject obj = ObjectHandler.getObjectsAll().get(i);
			
			if (obj != null && !obj.getIsActive()) {
				continue;
			} else if (obj == null) {
				return;
			}
			
			if (obj instanceof Block) {
				for (int i2 = 0; i2 < valueList.size(); i2++) {
					if (keyList.get(i2).toString() == ((Block) obj).getBlockProperties().getBlockType().toString()) {
						g.drawImage(valueList.get(i2), (int) obj.getPositionX(), (int) obj.getPositionY(), Block.getBlockSize(), Block.getBlockSize(), null);
					}
				}
			} else if (obj instanceof EntityPlayer) {
				for (int i2 = 0; i2 < valueList.size(); i2++) {
					if (keyList.get(i2).toString() == ((Entity) obj).getEntityProperties().getEntityType().toString()) {
						g.drawImage(valueList.get(i2), (int) obj.getPositionX(), (int) obj.getPositionY(), obj.getWidth(), obj.getHeight(), null);
					}
				}
			}
			
			//temporary lighting system!
			if (obj.getLightLevel() != 0) {
				g.setColor(new Color(0, 0, 0, obj.getLightLevel()));
				g.fillRect((int) obj.getPositionX(), (int) obj.getPositionY(), obj.getWidth(), obj.getHeight());
			}
		}
	}
}
