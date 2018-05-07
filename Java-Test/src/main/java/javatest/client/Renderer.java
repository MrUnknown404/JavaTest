package main.java.javatest.client;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.javatest.Main;
import main.java.javatest.blocks.Block;
import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.entity.Entity;
import main.java.javatest.entity.EntityItem;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.init.Blocks;
import main.java.javatest.init.Blocks.EnumBlocks;
import main.java.javatest.init.Items;
import main.java.javatest.util.Console;
import main.java.javatest.util.Resource;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.Vec2i;

public class Renderer {

	private Map<String, BufferedImage> hashImages = new HashMap<String, BufferedImage>();
	private List<BufferedImage> valueList = new ArrayList<BufferedImage>();
	private List<String> keyList = new ArrayList<String>();
	
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
		Console.print("Finding all item textures...");
		for (int i = 1; i < Items.items.size(); i++) {
			hashImages.put(Items.items.get(i).getName().toString(), Resource.getTexture(Resource.ResourceType.items, Items.items.get(i).getName()));
		}
		Console.print("Found all item textures!");
		Console.print(Console.WarningType.Info, "-Found all Textures!");
		
		Console.print("Setting textures for use...");
		valueList = new ArrayList<BufferedImage>(hashImages.values());
		keyList = new ArrayList<String>(hashImages.keySet());
		Console.print("Finished setting textures!");
	}
	
	public void render(Graphics go) {
		Graphics2D g = (Graphics2D) go;
		if (Main.getWorldHandler().getWorld().getPlayer() != null) {
			for (int i2 = 0; i2 < valueList.size(); i2++) {
				if (keyList.get(i2).toString().equals(((Entity) Main.getWorldHandler().getWorld().getPlayer()).getEntityProperties().getEntityType().toString())) {
					g.drawImage(valueList.get(i2), (int) Main.getWorldHandler().getWorld().getPlayer().getPositionX(), (int) Main.getWorldHandler().getWorld().getPlayer().getPositionY(), Main.getWorldHandler().getWorld().getPlayer().getWidth(), Main.getWorldHandler().getWorld().getPlayer().getHeight(), null);
				}
			}
			
			Block b = null;
			Vec2i tPos = new Vec2i(((MouseInput.vec.x) - Main.getCamera().getPositionX()) / Block.getBlockSize(), ((MouseInput.vec.y) - Main.getCamera().getPositionY()) / Block.getBlockSize());
			if (Blocks.findBlock(Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot()).getItem().getName()) == null) {
			} else if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().size() > Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() && Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot()).getCount() != 0) {
				b = new Block(new BlockPos(tPos.getX(), tPos.getY()), BlockProperties.findBlockPropertyWithName(Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot()).getItem().getName()));
			}
			
			if (b != null && Main.getWorldHandler().getWorld().getPlayer().getInteractionBounds().intersects(b.getBoundsAll())) {
				for (int i2 = 0; i2 < valueList.size(); i2++) {
					if (keyList.get(i2).toString() == b.getBlockProperties().getBlockType().toString()) {
						g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
						g.drawImage(valueList.get(i2), (int) b.getPositionX(), (int) b.getPositionY(), Block.getBlockSize(), Block.getBlockSize(), null);
						g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
						g.setColor(new Color(0.1f, 0.1f, 0.1f));
						g.drawRect((int) b.getPositionX(), (int) b.getPositionY(), 15, 15);
					}
				}
			}
		}
		
		for (int i = 0; i < Main.getWorldHandler().getWorld().getActiveBlocks().size(); i++) {
			Block obj = Main.getWorldHandler().getWorld().getActiveBlocks().get(i);
			
			if (obj == null) {
				return;
			}
			
			for (int i2 = 0; i2 < valueList.size(); i2++) {
				if (keyList.get(i2).toString() == obj.getBlockProperties().getBlockType().toString()) {
					g.drawImage(valueList.get(i2), (int) obj.getPositionX(), (int) obj.getPositionY(), Block.getBlockSize(), Block.getBlockSize(), null);
				}
			}
			
			//temporary lighting system!
			if (obj.getLightLevel() != 0) {
				g.setColor(new Color(0, 0, 0, obj.getLightLevel()));
				g.fillRect((int) obj.getPositionX(), (int) obj.getPositionY(), obj.getWidth(), obj.getHeight());
			}
			
			if (obj.getBlockPos().equals(new BlockPos(((MouseInput.vec.x) - Main.getCamera().getPositionX()) / Block.getBlockSize(), ((MouseInput.vec.y) - Main.getCamera().getPositionY()) / Block.getBlockSize()))) {
				g.setColor(new Color(0.1f, 0.1f, 0.1f));
				g.drawRect((int) obj.getPositionX(), (int) obj.getPositionY(), 15, 15);
			}
		}
		
		for (int i = 0; i < Main.getWorldHandler().getWorld().getActiveItemEntities().size(); i++) {
			EntityItem obj = Main.getWorldHandler().getWorld().getActiveItemEntities().get(i);
			
			if (obj == null) {
				return;
			}
			
			for (int i2 = 0; i2 < valueList.size(); i2++) {
				if (keyList.get(i2).toString().equals(obj.getItemStack().getItem().getName())) {
					g.drawImage(valueList.get(i2), (int) obj.getPositionX(), (int) obj.getPositionY(), obj.getWidth(), obj.getHeight(), null);
				}
			}
		}
		
		for (int i = 0; i < Main.getWorldHandler().getWorld().getActiveEntities().size(); i++) {
			Entity obj = Main.getWorldHandler().getWorld().getActiveEntities().get(i);
			
			if (obj == null) {
				return;
			}
			
			for (int i2 = 0; i2 < valueList.size(); i2++) {
				if (keyList.get(i2).toString().equals(obj.getEntityProperties().getEntityType().toString())) {
					g.drawImage(valueList.get(i2), (int) obj.getPositionX(), (int) obj.getPositionY(), obj.getWidth(), obj.getHeight(), null);
				}
			}
		}
	}
}
