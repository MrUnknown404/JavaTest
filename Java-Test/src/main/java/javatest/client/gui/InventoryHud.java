package main.java.javatest.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.java.javatest.Main;
import main.java.javatest.client.MouseInput;
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
			
			if (!Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsList().isEmpty()) {
				List<Slot> slots = Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsList();
				for (int i = 0; i < slots.size() - (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsX() * (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsY() - 1)); i++) {
					if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() == slots.get(i).slotID) {
						g.drawImage(slotSel, slots.get(i).x, slots.get(i).y, slotSel.getWidth(), slotSel.getHeight(), null);
					} else {
						g.drawImage(slot, slots.get(i).x, slots.get(i).y, slot.getWidth(), slot.getHeight(), null);
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
						g.drawImage(slot, slots.get(i).x, slots.get(i).y, slot.getWidth(), slot.getHeight(), null);
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
			
			if (Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse != null) {
				for (int i = 0; i < imgs.size(); i++) {
					if (keys.get(i).equals(Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse.getItem().getName())) {
						g.drawImage(imgs.get(i), MouseInput.vec.x, MouseInput.vec.y , imgs.get(i).getWidth(), imgs.get(i).getHeight(), null);
						g.drawString(String.valueOf(Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse.getCount()), MouseInput.vec.x + 6, MouseInput.vec.y + 34);
					}
				}
			}
		}
	}
}
