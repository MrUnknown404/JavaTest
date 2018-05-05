package main.java.javatest.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.java.javatest.Main;
import main.java.javatest.init.Items;
import main.java.javatest.util.Resource;

public class InventoryHud extends Canvas {

	private static final long serialVersionUID = 4432406462700067348L;
	private static final Font FONT = new Font("Font", Font.BOLD, 14);
	
	private final BufferedImage slot = Resource.getTexture(Resource.ResourceType.gui, "slot");
	private final BufferedImage slotSel = Resource.getTexture(Resource.ResourceType.gui, "slot_selected");
	private List<BufferedImage> imgs = new ArrayList<BufferedImage>();
	private List<String> keys = new ArrayList<String>();
	
	public void updateTextures() {
		for (int i = 1; i < Items.items.size(); i++) {
			imgs.add(Resource.getTexture(Resource.ResourceType.items, Items.items.get(i).getName()));
			keys.add(Items.items.get(i).getName().toString());
		}
	}
	
	public void draw(Graphics g) {
		if (Main.getWorldHandler().getWorld().getPlayer() != null) {
			g.setColor(Color.RED);
			g.setFont(FONT);
			
			for (int sx = 0; sx < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsX(); sx++) {
				if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() == sx) {
					g.drawImage(slotSel, 4 + ((sx * (slotSel.getWidth() * 2)) + (sx * 4)), 4, slotSel.getWidth() * 2, slotSel.getHeight() * 2, null);
				} else {
					g.drawImage(slot, 4 + ((sx * (slot.getWidth() * 2)) + (sx * 4)), 4, slot.getWidth() * 2, slot.getHeight() * 2, null);
				}
				
				for (int i2 = 0; i2 < Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().size(); i2++) {
					for (int i3 = 0; i3 < imgs.size(); i3++) {
						if (keys.get(i3).equals(Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i2).getItem().getName()) && i2 == sx) {
							g.drawImage(imgs.get(i3), 8 + ((sx * imgs.get(i3).getWidth()) + (sx * 12)), 8, imgs.get(i3).getWidth(), imgs.get(i3).getHeight(), null);
							g.drawString(String.valueOf(Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i2).getCount()), 10 + ((sx * imgs.get(i3).getWidth()) + (sx * 12)), 38);
						}
					}
				}
				
				if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getIsInventoryOpen()) {
					for (int sy = 1; sy < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsY(); sy++) {
						g.drawImage(slot, 4 + ((sx * (slot.getWidth() * 2)) + (sx * 4)), 4 + ((sy * (slot.getHeight() * 2)) + (sy * 4)), slot.getWidth() * 2, slot.getHeight() * 2, null);
						
						for (int i2 = 0; i2 < Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().size(); i2++) {
							for (int i3 = 0; i3 < imgs.size(); i3++) {
								if (keys.get(i3).equals(Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i2).getItem().getName()) && i2 == sx + (sy * 10)) {
									g.drawImage(imgs.get(i3), 8 + ((sx * imgs.get(i3).getWidth()) + (sx * 12)), 8 + ((sy * imgs.get(i3).getHeight()) + (sy * 12)), imgs.get(i3).getWidth(), imgs.get(i3).getHeight(), null);
									g.drawString(String.valueOf(Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i2).getCount()), 10 + ((sx * imgs.get(i3).getWidth()) + (sx * 12)), 38 + ((sy * imgs.get(i3).getHeight()) + (sy * 12)));
								}
							}
						}
					}
				}
			}
		}
	}
}
