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
import main.java.javatest.items.Slot;
import main.java.javatest.util.Resource;

public class InventoryHud extends Canvas {

	private static final long serialVersionUID = 4432406462700067348L;
	private static final Font FONT = new Font("Font", Font.BOLD, 14);
	
	private final BufferedImage slot = Resource.getTexture(Resource.ResourceType.gui, "slot");
	private final BufferedImage slotSel = Resource.getTexture(Resource.ResourceType.gui, "slot_selected");
	private List<BufferedImage> imgs = new ArrayList<BufferedImage>();
	private List<String> keys = new ArrayList<String>();
	private static List<Slot> slots = new ArrayList<Slot>();
	
	public void updateTextures() {
		imgs.clear();
		keys.clear();
		
		for (int i = 1; i < Items.items.size(); i++) {
			imgs.add(Resource.getTexture(Resource.ResourceType.items, Items.items.get(i).getName()));
			keys.add(Items.items.get(i).getName().toString());
		}	
	}
	
	public void draw(Graphics g) {
		if (Main.getWorldHandler().getWorld().getPlayer() != null) {
			g.setColor(Color.RED);
			g.setFont(FONT);
			
			if (slots.isEmpty()) {
				for (int sy = 0; sy < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsY(); sy++) {
					for (int sx = 0; sx < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsX(); sx++) {
						if (sy == 0) {
							slots.add(new Slot(4 + ((sx * 32) + (sx * 12)), 4 + ((sy * 32) + (sy * 12)), sx + (sy * 10)));
						} else {
							slots.add(new Slot(4 + ((sx * 32) + (sx * 12)), 8 + ((sy * 32) + (sy * 12)), sx + (sy * 10)));
						}
					}
				}
			}
			
			for (int i = 0; i < slots.size() - (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsX() * (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsY() - 1)); i++) {
				if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() == slots.get(i).slotID) {
					g.drawImage(slotSel, slots.get(i).x, slots.get(i).y, slotSel.getWidth() * 2, slotSel.getHeight() * 2, null);
				} else {
					g.drawImage(slot, slots.get(i).x, slots.get(i).y, slot.getWidth() * 2, slot.getHeight() * 2, null);
				}
				for (int i2 = 0; i2 < Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().size(); i2++) {
					for (int i3 = 0; i3 < imgs.size(); i3++) {
						if (keys.get(i3).equals(Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i2).getItem().getName()) && i2 == i) {
							g.drawImage(imgs.get(i3), slots.get(i).x + 4, slots.get(i).y + 4, imgs.get(i3).getWidth(), imgs.get(i3).getHeight(), null);
							g.drawString(String.valueOf(Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i2).getCount()), slots.get(i).x + 6, slots.get(i).y + 34);
						}
					}
				}
			}
			
			
			if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getIsInventoryOpen()) {
				for (int i = Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsX(); i < slots.size(); i++) {
					g.drawImage(slot, slots.get(i).x, slots.get(i).y, slot.getWidth() * 2, slot.getHeight() * 2, null);
					for (int i2 = 0; i2 < Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().size(); i2++) {
						for (int i3 = 0; i3 < imgs.size(); i3++) {
							if (keys.get(i3).equals(Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i2).getItem().getName()) && i2 == i) {
								g.drawImage(imgs.get(i3), slots.get(i).x + 4, slots.get(i).y + 4, imgs.get(i3).getWidth(), imgs.get(i3).getHeight(), null);
								g.drawString(String.valueOf(Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i2).getCount()), slots.get(i).x + 6, slots.get(i).y + 34);
							}
						}
					}
				}
			}
		}
	}
}
