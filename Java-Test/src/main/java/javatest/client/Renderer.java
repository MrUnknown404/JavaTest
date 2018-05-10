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
import main.java.javatest.items.ItemStack;
import main.java.javatest.util.Console;
import main.java.javatest.util.Resource;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2i;

public class Renderer {

	private final BufferedImage border = Resource.getTexture(Resource.ResourceType.blocks, "border");
	private Map<String, BufferedImage> hashImages = new HashMap<String, BufferedImage>();
	private List<BufferedImage> valueList = new ArrayList<BufferedImage>();
	private List<String> keyList = new ArrayList<String>();
	
	private List<BufferedImage> imgs = new ArrayList<BufferedImage>();
	private List<String> keys = new ArrayList<String>();
	
	private List<BufferedImage> crackImgs = new ArrayList<BufferedImage>();
	private List<String> crackKeys = new ArrayList<String>();
	
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
		for (int i = 1; i < 11; i++) {
			crackImgs.add(Resource.getTexture(Resource.ResourceType.blocks, "crack_" + i));
			crackKeys.add("crack_" + i);
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
	
	public static int i;
	
	public void render(Graphics go) {
		Graphics2D g = (Graphics2D) go;
		if (Main.getWorldHandler().getWorld().getPlayer() != null) {
			for (int i = 0; i < valueList.size(); i++) {
				if (keyList.get(i).toString().equals(((Entity) Main.getWorldHandler().getWorld().getPlayer()).getEntityProperties().getEntityType().toString())) {
					g.drawImage(valueList.get(i), (int) Main.getWorldHandler().getWorld().getPlayer().getPositionX(), (int) Main.getWorldHandler().getWorld().getPlayer().getPositionY(), Main.getWorldHandler().getWorld().getPlayer().getWidth(), Main.getWorldHandler().getWorld().getPlayer().getHeight(), null);
				}
			}
			
			if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedItem() != ItemStack.EMPTY) {
				for (int i = 0; i < imgs.size(); i++) {
					if (keys.get(i).toString().equals((Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedItem().getItem().getName()))) {
						if (Main.getWorldHandler().getWorld().getPlayer().direction == 1) {
							g.drawImage(imgs.get(i), (int) Main.getWorldHandler().getWorld().getPlayer().getPositionX() + Main.getWorldHandler().getWorld().getPlayer().getWidth(), (int) Main.getWorldHandler().getWorld().getPlayer().getPositionY() + 4, imgs.get(i).getWidth() / 2, imgs.get(i).getHeight() / 2, null);
						} else if (Main.getWorldHandler().getWorld().getPlayer().direction == -1) {
							g.drawImage(imgs.get(i), (int) Main.getWorldHandler().getWorld().getPlayer().getPositionX(), (int) Main.getWorldHandler().getWorld().getPlayer().getPositionY() + 4, -imgs.get(i).getWidth() / 2, imgs.get(i).getHeight() / 2, null);
						}
					}
				}
			}
			
			Block b = null;
			Vec2i tPos = new Vec2i(((MouseInput.vec.x) - Main.getCamera().getPositionX()) / Block.getBlockSize(), ((MouseInput.vec.y) - Main.getCamera().getPositionY()) / Block.getBlockSize());
			if (Blocks.findBlock(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedItem().getItem().getName()) == null) {
			} else if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().size() > Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() && Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedItem().getCount() != 0) {
				b = new Block(new BlockPos(tPos.getX(), tPos.getY()), BlockProperties.findBlockPropertyWithName(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedItem().getItem().getName()));
			}
			
			if (b != null && Main.getWorldHandler().getWorld().getPlayer().getInteractionBounds().intersects(b.getBoundsAll())) {
				for (int i = 0; i < valueList.size(); i++) {
					if (keyList.get(i).toString() == b.getBlockProperties().getBlockType().toString()) {
						g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
						g.drawImage(valueList.get(i), (int) b.getPositionX(), (int) b.getPositionY(), Block.getBlockSize(), Block.getBlockSize(), null);
						g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
						g.drawImage(border, (int) b.getPositionX(), (int) b.getPositionY(), border.getWidth(), border.getHeight(), null);
					}
				}
			}
		}
		
		for (i = 0; i < Main.getWorldHandler().getWorld().getActiveBlocks().size(); i++) {
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
			
			if (obj.getBlockPos().equals(new BlockPos(((MouseInput.vec.x) - Main.getCamera().getPositionX()) / Block.getBlockSize(), ((MouseInput.vec.y) - Main.getCamera().getPositionY()) / Block.getBlockSize())) && Main.getWorldHandler().getWorld().getPlayer().getInteractionBounds().intersects(obj.getBoundsAll())) {
				g.drawImage(border, (int) obj.getPositionX(), (int) obj.getPositionY(), border.getWidth(), border.getHeight(), null);
			}
			
			if (obj.brokenness != 0) {
				g.drawImage(crackImgs.get((int) MathHelper.clamp(obj.brokenness * 10, 0, crackImgs.size() - 1)), (int) obj.getPositionX(), (int) obj.getPositionY(), 16, 16, null);
			}
		}
		
		for (i = 0; i < Main.getWorldHandler().getWorld().getActiveItemEntities().size(); i++) {
			EntityItem obj = Main.getWorldHandler().getWorld().getActiveItemEntities().get(i);
			
			if (obj == null) {
				return;
			}
			
			for (int i2 = 0; i2 < imgs.size(); i2++) {
				if (keys.get(i2).toString().equals(obj.getItemStack().getItem().getName())) {
					g.drawImage(imgs.get(i2), (int) obj.getPositionX(), (int) obj.getPositionY(), obj.getWidth(), obj.getHeight(), null);
				}
			}
		}
		
		for (i = 0; i < Main.getWorldHandler().getWorld().getActiveEntities().size(); i++) {
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
