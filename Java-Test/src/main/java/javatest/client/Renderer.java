package main.java.javatest.client;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import main.java.javatest.Main;
import main.java.javatest.blocks.util.Block;
import main.java.javatest.entity.Entity;
import main.java.javatest.entity.EntityItem;
import main.java.javatest.entity.entityliving.EntityDummy;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.init.Blocks;
import main.java.javatest.init.Blocks.EnumBlocks;
import main.java.javatest.items.ItemBlock;
import main.java.javatest.items.util.ItemStack;
import main.java.javatest.util.Console;
import main.java.javatest.util.Resource;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2i;
import main.java.javatest.world.util.Chunk;

public class Renderer {

	private static final Font FONT = new Font("Font", Font.BOLD, 14), FONT2 = new Font("Font2", Font.BOLD, 18);
	
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
		Console.print(Console.WarningType.Info, "Finding textures...");
		Console.print("Finding block textures...");
		for (int i = 1; i < Blocks.EnumBlocks.values().length; i++) {
			hashImages.put(Blocks.EnumBlocks.getNumber(i).toString(), Resource.getTexture(Resource.ResourceType.blocks, EnumBlocks.getNumber(i).name()));
		}
		
		for (int i = 1; i < 11; i++) {
			crackImgs.add(Resource.getTexture(Resource.ResourceType.blocks, "crack_" + i));
			crackKeys.add("crack_" + i);
		}
		Console.print("Found block textures!");
		Console.print("Finding entity textures...");
		for (int i = 0; i < EntityProperties.EntityType.values().length; i++) {
			if (EntityProperties.EntityType.getNumber(i) != EntityProperties.EntityType.item) {
				hashImages.put(EntityProperties.EntityType.getNumber(i).toString(), Resource.getTexture(Resource.ResourceType.entity, EntityProperties.EntityType.getNumber(i).name()));
			}
		}
		Console.print("Found entity textures!");
		Console.print(Console.WarningType.Info, "Found textures!");
		
		Console.print("Setting textures for use...");
		valueList = new ArrayList<BufferedImage>(hashImages.values());
		keyList = new ArrayList<String>(hashImages.keySet());
		Console.print("Finished setting textures!");
	}
	
	public void render(Graphics go) {
		Graphics2D g = (Graphics2D) go;
		
		if (!Main.getWorldHandler().getActiveChunks().isEmpty()) {
			for (int i = 0; i < Main.getWorldHandler().getActiveChunks().size(); i++) {
				Chunk cnk = Main.getWorldHandler().getActiveChunks().get(i);
				Vec2i tPos = new Vec2i((MouseInput.vec.x - Main.getCamera().getPositionX()) / Block.getBlockSize(), (MouseInput.vec.y - Main.getCamera().getPositionY()) / Block.getBlockSize());
				
				for (int i3 = 0; i3 < cnk.getBlocks().size(); i3++) {
					Block b = cnk.getBlocks().get(i3);
					for (int i2 = 0; i2 < valueList.size(); i2++) {
						if (keyList.get(i2).toString() == b.getBlockProperties().getBlockType().toString()) {
							g.drawImage(valueList.get(i2), (int) b.getPositionX(), (int) b.getPositionY(), Block.getBlockSize(), Block.getBlockSize(), null);
							
							if (cnk.getBlocks().get(i3).brokenness != 0) {
								g.drawImage(crackImgs.get((int) MathHelper.clamp(cnk.getBlocks().get(i3).brokenness * 10, 0, crackImgs.size() - 1)), (int) b.getPositionX(), (int) b.getPositionY(), Block.getBlockSize(), Block.getBlockSize(), null);
							}
						}
					}
					
					if (b.getBoundsAll().intersects(Main.getWorldHandler().getPlayer().getInteractionBounds())) {
						if (b.getBoundsAll().intersects(new Rectangle(tPos.x * Block.getBlockSize(), tPos.y * Block.getBlockSize(), 1, 1))) {
							g.drawImage(border, (int) b.getPositionX(), (int) b.getPositionY(), Block.getBlockSize(), Block.getBlockSize(), null);
						}
					}
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
				for (int i = 0; i < valueList.size(); i++) {
					if (Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem() instanceof ItemBlock) {
						if (keyList.get(i).toString() == ((ItemBlock) Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem()).getBlock().getBlockType().toString()) {
							Vec2i tPos = new Vec2i((MouseInput.vec.x - Main.getCamera().getPositionX()) / Block.getBlockSize(), (MouseInput.vec.y - Main.getCamera().getPositionY()) / Block.getBlockSize());
							
							if (Main.getWorldHandler().getPlayer().getInteractionBounds().intersects(new Rectangle(tPos.getX() * Block.getBlockSize(), tPos.getY() * Block.getBlockSize(), 1, 1))) {
								g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); 
								g.drawImage(valueList.get(i), (int) tPos.getX() * Block.getBlockSize(), (int) tPos.getY() * Block.getBlockSize(), Block.getBlockSize(), Block.getBlockSize(), null);
								g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); 
							}
						}
					}
				}
				
				for (int i = 0; i < imgs.size(); i++) {
					if (keys.get(i).toString().equals((Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem().getName()))) {
						if (Main.getWorldHandler().getPlayer().direction == 1) {
							g.drawImage(imgs.get(i), (int) Main.getWorldHandler().getPlayer().getPositionX() + Main.getWorldHandler().getPlayer().getWidth(), (int) Main.getWorldHandler().getPlayer().getPositionY() + 8, imgs.get(i).getWidth() / 2, imgs.get(i).getHeight() / 2, null);
						} else if (Main.getWorldHandler().getPlayer().direction == -1) {
							g.drawImage(imgs.get(i), (int) Main.getWorldHandler().getPlayer().getPositionX(), (int) Main.getWorldHandler().getPlayer().getPositionY() + 8, -imgs.get(i).getWidth() / 2, imgs.get(i).getHeight() / 2, null);
						}
					}
				}
			}
		}
	}
}
