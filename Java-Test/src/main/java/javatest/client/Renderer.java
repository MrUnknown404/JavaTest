package main.java.javatest.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import main.java.javatest.Main;
import main.java.javatest.blocks.Block;
import main.java.javatest.entity.Entity;
import main.java.javatest.entity.EntityItem;
import main.java.javatest.entity.entityliving.EntityDummy;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.init.Blocks;
import main.java.javatest.init.Blocks.EnumBlocks;
import main.java.javatest.items.ItemStack;
import main.java.javatest.util.Console;
import main.java.javatest.util.Resource;
import main.java.javatest.util.math.Vec2i;

public class Renderer {

	private static final Font FONT = new Font("Font", Font.BOLD, 14), FONT2 = new Font("Font2", Font.BOLD, 18);
	
	private Map<String, BufferedImage> hashImages = new HashMap<String, BufferedImage>();
	private List<BufferedImage> valueList = new ArrayList<BufferedImage>();
	private List<String> keyList = new ArrayList<String>();
	
	private List<BufferedImage> imgs = new ArrayList<BufferedImage>();
	private List<String> keys = new ArrayList<String>();
	
	public void setImgsAndKeys(List<BufferedImage> imgs, List<String> keys) {
		this.imgs = imgs;
		this.keys = keys;
	}
	
	public void findTextures() {
		Console.print(Console.WarningType.Info, "-Finding all textures...");
		Console.print("Finding all block textures...");
		for (int i = 1; i < Blocks.EnumBlocks.values().length; i++) {
			hashImages.put(Blocks.EnumBlocks.getNumber(i).toString(), Resource.getTexture(Resource.ResourceType.blocks, EnumBlocks.getNumber(i)));
		}
		Console.print("Found all block textures!");
		Console.print("Finding all entity textures...");
		for (int i = 0; i < EntityProperties.EntityType.values().length; i++) {
			if (EntityProperties.EntityType.getNumber(i) != EntityProperties.EntityType.item) {
				hashImages.put(EntityProperties.EntityType.getNumber(i).toString(), Resource.getTexture(Resource.ResourceType.entity, EntityProperties.EntityType.getNumber(i)));
			}
		}
		Console.print("Found all entity textures!");
		Console.print(Console.WarningType.Info, "-Found all Textures!");
		
		Console.print("Setting textures for use...");
		valueList = new ArrayList<BufferedImage>(hashImages.values());
		keyList = new ArrayList<String>(hashImages.keySet());
		Console.print("Finished setting textures!");
	}
	
	public void render(Graphics go) {
		Graphics2D g = (Graphics2D) go;
		
		for (int i = 0; i < Main.getWorldHandler().getActiveBlocks().size(); i++) {
			Block obj = Main.getWorldHandler().getActiveBlocks().get(i);
			
			if (obj == null) {
				continue;
			}
			
			for (int i2 = 0; i2 < valueList.size(); i2++) {
				if (keyList.get(i2).toString() == obj.getBlockProperties().getBlockType().toString()) {
					g.drawImage(valueList.get(i2), (int) obj.getPositionX(), (int) obj.getPositionY(), Block.getBlockSize(), Block.getBlockSize(), null);
				}
			}
		}
		
		for (int i = 0; i < Main.getWorldHandler().getActiveEntities().size(); i++) {
			Entity obj = Main.getWorldHandler().getActiveEntities().get(i);
			
			if (obj == null) {
				continue;
			}
			
			if (obj instanceof EntityItem) {
				for (int i2 = 0; i2 < imgs.size(); i2++) {
					if (keys.get(i2).toString().equals(((EntityItem) obj).getItemStack().getItem().getName())) {
						g.drawImage(imgs.get(i2), (int) obj.getPositionX(), (int) obj.getPositionY(), obj.getWidth(), obj.getHeight(), null);
					}
				}
			} else if (obj instanceof EntityDummy) {
				for (int i2 = 0; i2 < valueList.size(); i2++) {
					if (keyList.get(i2).toString().equals(obj.getEntityProperties().getEntityType().toString())) {
						g.drawImage(valueList.get(i2), (int) obj.getPositionX(), (int) obj.getPositionY(), obj.getWidth(), obj.getHeight(), null);
						
						if (((EntityDummy) obj).getDamage() != 0) {
							int w1 = 0, val = 0;
							g.setColor(Color.RED);
							if (((EntityDummy) obj).getIsCrit()) {
								g.setFont(FONT2);
								w1 = (int) obj.getPositionX() - (int) g.getFontMetrics().getStringBounds(String.valueOf(((EntityDummy) obj).getDamage()), g).getWidth() / 2;
								if (new Random().nextInt(3) == 0) {
									val = ThreadLocalRandom.current().nextInt(-5, 5);
								}
							} else {
								g.setFont(FONT);
								w1 = (int) obj.getPositionX() - (int) g.getFontMetrics().getStringBounds(String.valueOf(((EntityDummy) obj).getDamage()), g).getWidth() / 2;
							}
							
							if (((EntityDummy) obj).getIsCrit()) {
								g.rotate(Math.toRadians(val), (int) obj.getPositionX() + (obj.getWidth() / 2), (int) obj.getPositionY());
								g.drawString(String.valueOf(((EntityDummy) obj).getDamage()), w1 + 16, (int) obj.getPositionY() + 4);
								g.rotate(-Math.toRadians(val), (int) obj.getPositionX() + (obj.getWidth() / 2), (int) obj.getPositionY());
							} else {
								g.drawString(String.valueOf(((EntityDummy) obj).getDamage()), w1 + 16, (int) obj.getPositionY() + 4);
							}
						}
					}
				}
			} else {
				for (int i2 = 0; i2 < valueList.size(); i2++) {
					if (keyList.get(i2).toString().equals(obj.getEntityProperties().getEntityType().toString())) {
						g.drawImage(valueList.get(i2), (int) obj.getPositionX(), (int) obj.getPositionY(), obj.getWidth(), obj.getHeight(), null);
					}
				}
			}
		}
		
		if (Main.getWorldHandler().getPlayer() != null) {
			for (int i = 0; i < valueList.size(); i++) {
				if (keyList.get(i).toString().equals(((Entity) Main.getWorldHandler().getPlayer()).getEntityProperties().getEntityType().toString())) {
					g.setColor(Color.RED);
					if (Main.getWorldHandler().getPlayer().direction == 1) {
						g.drawImage(valueList.get(i), (int) Main.getWorldHandler().getPlayer().getPositionX(), (int) Main.getWorldHandler().getPlayer().getPositionY(), Main.getWorldHandler().getPlayer().getWidth(), Main.getWorldHandler().getPlayer().getHeight(), null);
					} else if (Main.getWorldHandler().getPlayer().direction == -1) {
						g.drawImage(valueList.get(i), (int) Main.getWorldHandler().getPlayer().getPositionX() + Main.getWorldHandler().getPlayer().getWidth(), (int) Main.getWorldHandler().getPlayer().getPositionY(), -Main.getWorldHandler().getPlayer().getWidth(), Main.getWorldHandler().getPlayer().getHeight(), null);
					}
				}
			}
			
			if (Main.getWorldHandler().getPlayer().getInventory().getSelectedItem() != ItemStack.EMPTY) {
				for (int i = 0; i < imgs.size(); i++) {
					if (keys.get(i).toString().equals((Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem().getName()))) {
						double rt = Math.toRadians(Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem().getSwingAmount());
						int intx = (int) Main.getWorldHandler().getPlayer().getPositionX();
						Vec2i vec = new Vec2i((int) Main.getWorldHandler().getPlayer().getPositionX() + Main.getWorldHandler().getPlayer().getWidth(), (int) Main.getWorldHandler().getPlayer().getPositionY() + 16);
						
						if (Main.getWorldHandler().getPlayer().direction == 1) {
							if (Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem().getSwingAmount() != 90) {
								g.rotate(rt, vec.x + 4, vec.y);
								g.drawImage(imgs.get(i), (int) Main.getWorldHandler().getPlayer().getPositionX() + Main.getWorldHandler().getPlayer().getWidth() - 2, (int) Main.getWorldHandler().getPlayer().getPositionY() - 6, imgs.get(i).getWidth(), imgs.get(i).getHeight(), null);
								g.rotate(-rt, vec.x + 4, vec.y);
							} else {
								g.drawImage(imgs.get(i), (int) Main.getWorldHandler().getPlayer().getPositionX() + Main.getWorldHandler().getPlayer().getWidth(), (int) Main.getWorldHandler().getPlayer().getPositionY() + 8, imgs.get(i).getWidth() / 2, imgs.get(i).getHeight() / 2, null);
							}
						} else if (Main.getWorldHandler().getPlayer().direction == -1) {
							if (Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem().getSwingAmount() != 90) {
								g.rotate(-rt, intx - 4, vec.y);
								g.drawImage(imgs.get(i), (int) Main.getWorldHandler().getPlayer().getPositionX() + 2, (int) Main.getWorldHandler().getPlayer().getPositionY() - 6, -imgs.get(i).getWidth(), imgs.get(i).getHeight(), null);
								g.rotate(rt, intx - 4, vec.y);
							} else {
								g.drawImage(imgs.get(i), (int) Main.getWorldHandler().getPlayer().getPositionX(), (int) Main.getWorldHandler().getPlayer().getPositionY() + 8, -imgs.get(i).getWidth() / 2, imgs.get(i).getHeight() / 2, null);
							}
						}
					}
				}
			}
		}
	}
}
