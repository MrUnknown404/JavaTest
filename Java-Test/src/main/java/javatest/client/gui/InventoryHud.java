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
import main.java.javatest.items.Item;
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
		
		for (int i = 0; i < Items.items.size(); i++) {
			imgs.add(Resource.getTexture(Resource.ResourceType.items, Items.items.get(i).getName()));
			keys.add(Items.items.get(i).getName().toString());
		}
	}
	
	public void draw(Graphics g) {
		if (Main.getWorldHandler().getPlayer() != null) {
			g.setColor(Color.RED);
			g.setFont(FONT);
			
			if (!Main.getWorldHandler().getPlayer().getInventory().getSlotsList().isEmpty()) {
				List<Slot> slots = Main.getWorldHandler().getPlayer().getInventory().getSlotsList();
				for (int i = 0; i < slots.size() - (Main.getWorldHandler().getPlayer().getInventory().getSlotsX() * (Main.getWorldHandler().getPlayer().getInventory().getSlotsY() - 1)); i++) {
					if (Main.getWorldHandler().getPlayer().getInventory().getSelectedSlot() == slots.get(i).getSlotID()) {
						g.drawImage(slotSel, slots.get(i).getX(), slots.get(i).getY(), slotSel.getWidth(), slotSel.getHeight(), null);
					} else {
						g.drawImage(slot, slots.get(i).getX(), slots.get(i).getY(), slot.getWidth(), slot.getHeight(), null);
					}
					for (int i2 = 0; i2 < Main.getWorldHandler().getPlayer().getInventory().getItems().size(); i2++) {
						for (int i3 = 0; i3 < imgs.size(); i3++) {
							if (keys.get(i3).equals(Main.getWorldHandler().getPlayer().getInventory().getItems().get(i2).getItem().getName()) && i2 == i) {
								g.drawImage(imgs.get(i3), slots.get(i).getX() + 4, slots.get(i).getY() + 4, imgs.get(i3).getWidth(), imgs.get(i3).getHeight(), null);
								if (Main.getWorldHandler().getPlayer().getInventory().getItems().get(i2).getItem().getItemType() == Item.ItemType.item) {
									g.drawString(String.valueOf(Main.getWorldHandler().getPlayer().getInventory().getItems().get(i2).getCount()), slots.get(i).getX() + 6, slots.get(i).getY() + 34);
								}
							}
						}
					}
				}
				
				
				if (Main.getWorldHandler().getPlayer().getInventory().getIsInventoryOpen()) {
					for (int i = Main.getWorldHandler().getPlayer().getInventory().getSlotsX(); i < slots.size(); i++) {
						g.drawImage(slot, slots.get(i).getX(), slots.get(i).getY(), slot.getWidth(), slot.getHeight(), null);
						for (int i2 = 0; i2 < Main.getWorldHandler().getPlayer().getInventory().getItems().size(); i2++) {
							for (int i3 = 0; i3 < imgs.size(); i3++) {
								if (keys.get(i3).equals(Main.getWorldHandler().getPlayer().getInventory().getItems().get(i2).getItem().getName()) && i2 == i) {
									g.drawImage(imgs.get(i3), slots.get(i).getX() + 4, slots.get(i).getY() + 4, imgs.get(i3).getWidth(), imgs.get(i3).getHeight(), null);
									if (Main.getWorldHandler().getPlayer().getInventory().getItems().get(i2).getItem().getItemType() == Item.ItemType.item) {
										g.drawString(String.valueOf(Main.getWorldHandler().getPlayer().getInventory().getItems().get(i2).getCount()), slots.get(i).getX() + 6, slots.get(i).getY() + 34);
									}
								}
							}
						}
					}
				}
			}
			
			if (Main.getWorldHandler().getPlayer().getInventory().getItemInMouse() != null) {
				for (int i = 0; i < imgs.size(); i++) {
					if (keys.get(i).equals(Main.getWorldHandler().getPlayer().getInventory().getItemInMouse().getItem().getName())) {
						g.drawImage(imgs.get(i), MouseInput.vec.x - 16, MouseInput.vec.y - 16, imgs.get(i).getWidth(), imgs.get(i).getHeight(), null);
						if (Main.getWorldHandler().getPlayer().getInventory().getItemInMouse().getItem().getItemType() == Item.ItemType.item) {
							g.drawString(String.valueOf(Main.getWorldHandler().getPlayer().getInventory().getItemInMouse().getCount()), MouseInput.vec.x - 14, MouseInput.vec.y + 14);
						}
					}
				}
			}
		}
	}
	
	public List<BufferedImage> getImgs() {
		return imgs;
	}
	
	public List<String> getKeys() {
		return keys;
	}
}
