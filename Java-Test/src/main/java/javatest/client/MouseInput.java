package main.java.javatest.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import main.java.javatest.Main;
import main.java.javatest.blocks.Block;
import main.java.javatest.client.gui.DebugHud;
import main.java.javatest.items.ItemStack;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2i;

public class MouseInput extends MouseAdapter {

	public static Vec2i vec = new Vec2i();
	public static boolean leftClick;
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (Main.getWorldHandler().getPlayer() != null) {
			if (e.getWheelRotation() == 1) {
				if (Main.getWorldHandler().getPlayer().getInventory().getSelectedSlot() != Main.getWorldHandler().getPlayer().getInventory().getSlotsX() - 1) {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(Main.getWorldHandler().getPlayer().getInventory().getSelectedSlot() + 1);
					Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem().setSwingAmount(90);
				} else {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(0);
					Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem().setSwingAmount(90);
				}
			} else {
				if (Main.getWorldHandler().getPlayer().getInventory().getSelectedSlot() != 0) {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(Main.getWorldHandler().getPlayer().getInventory().getSelectedSlot() - 1);
					Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem().setSwingAmount(90);
				} else {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(9);
					Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem().setSwingAmount(90);
				}
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		vec = new Vec2i(e.getX() / Main.scaleWidth, e.getY() / Main.scaleHeight);
		DebugHud.setMouseVec(new Vec2i(MathHelper.floor(((vec.x) - Main.getCamera().getPositionX()) / Block.getBlockSize()), MathHelper.floor(((vec.y) - Main.getCamera().getPositionY()) / Block.getBlockSize())));
		if (Main.getWorldHandler() != null && Main.getWorldHandler().getPlayer() != null) {
			if (vec.x < Main.WIDTH_DEF / 2) {
				Main.getWorldHandler().getPlayer().direction = -1;
			} else {
				Main.getWorldHandler().getPlayer().direction = 1;
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		vec = new Vec2i(e.getX() / Main.scaleWidth, e.getY() / Main.scaleHeight);
		DebugHud.setMouseVec(new Vec2i(MathHelper.floor(((vec.x) - Main.getCamera().getPositionX()) / Block.getBlockSize()), MathHelper.floor(((vec.y) - Main.getCamera().getPositionY()) / Block.getBlockSize())));
		if (Main.getWorldHandler().getPlayer() != null) {
			if (vec.x < Main.WIDTH_DEF / 2) {
				Main.getWorldHandler().getPlayer().direction = -1;
			} else {
				Main.getWorldHandler().getPlayer().direction = 1;
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (Main.getCommandConsole().isConsoleOpen) {
			return;
		}
		
		if (e.getButton() == 3) {
			if (Main.getWorldHandler().getPlayer() != null && Main.getWorldHandler().getPlayer().getInventory().getIsInventoryOpen()) {
				if (Main.getWorldHandler().getPlayer().getInventory().getItemInMouse() == null) {
					for (int i = 0; i < Main.getWorldHandler().getPlayer().getInventory().getSlots(); i++) {
						if (Main.getWorldHandler().getPlayer().getInventory().getSlotsList().get(i).getBoundsAll().intersects(vec.x, vec.y, 1, 1)) {
							if (!Main.getWorldHandler().getPlayer().getInventory().getItems().get(i).equals(ItemStack.EMPTY)) {
								int ic1 = Main.getWorldHandler().getPlayer().getInventory().getItems().get(i).getCount();
								if (ic1 > 1) {
									if ((ic1 & 1) == 0) {
										Main.getWorldHandler().getPlayer().getInventory().setItemInMouse(new ItemStack(ic1 / 2, Main.getWorldHandler().getPlayer().getInventory().getItems().get(i).getItem()));
										Main.getWorldHandler().getPlayer().getInventory().getItems().get(i).setCount(ic1 / 2);
									} else {
										Main.getWorldHandler().getPlayer().getInventory().setItemInMouse(new ItemStack(ic1 / 2 + 1, Main.getWorldHandler().getPlayer().getInventory().getItems().get(i).getItem()));
										Main.getWorldHandler().getPlayer().getInventory().getItems().get(i).setCount(ic1 / 2);
									}
								} else {
									Main.getWorldHandler().getPlayer().getInventory().setItemInMouse(Main.getWorldHandler().getPlayer().getInventory().getItems().get(i));
									Main.getWorldHandler().getPlayer().getInventory().getItems().get(i).setCount(0);
								}
							}
						}
					}
				} else {
					for (int i = 0; i < Main.getWorldHandler().getPlayer().getInventory().getSlots(); i++) {
						if (Main.getWorldHandler().getPlayer().getInventory().getSlotsList().get(i).getBoundsAll().intersects(vec.x, vec.y, 1, 1)) {
							if (Main.getWorldHandler().getPlayer().getInventory().getItems().get(i).equals(ItemStack.EMPTY)) {
								if (Main.getWorldHandler().getPlayer().getInventory().getItemInMouse() != null) {
									Main.getWorldHandler().getPlayer().getInventory().addItemTo(new ItemStack(1, Main.getWorldHandler().getPlayer().getInventory().getItemInMouse().getItem()), i);
									Main.getWorldHandler().getPlayer().getInventory().getItemInMouse().decreaseCount();
								}
							} else if (Main.getWorldHandler().getPlayer().getInventory().getItemInMouse() != null && Main.getWorldHandler().getPlayer().getInventory().getItems().get(i).equals(Main.getWorldHandler().getPlayer().getInventory().getItemInMouse())) {
								Main.getWorldHandler().getPlayer().getInventory().getItemInMouse().decreaseCount();
								Main.getWorldHandler().getPlayer().getInventory().getItems().get(i).increaseCount();
							}
						}
					}
				}
			} else if (Main.getWorldHandler().getPlayer() != null && !Main.getWorldHandler().getPlayer().getInventory().getIsInventoryOpen()) {
				for (int i = 0; i < Main.getWorldHandler().getPlayer().getInventory().getSlotsX(); i++) {
					if (Main.getWorldHandler().getPlayer().getInventory().getSlotsList().get(i).getBoundsAll().intersects(vec.x, vec.y, 1, 1)) {
						Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(i);
						Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem().setSwingAmount(90);
					}
				}
			}
		} else if (e.getButton() == 1) {
			if (Main.getWorldHandler().getPlayer() == null) {
				return;
			}
			
			boolean tb = false;
			if (Main.getWorldHandler().getPlayer().getInventory().getIsInventoryOpen()) {
				for (int i = 0; i < Main.getWorldHandler().getPlayer().getInventory().getSlots(); i++) {
					if (Main.getWorldHandler().getPlayer().getInventory().getSlotsList().get(i).getBoundsAll().intersects(vec.x, vec.y, 1, 1)) {
						tb = true;
						if (Main.getWorldHandler().getPlayer().getInventory().getItemInMouse() == null) {
							if (!Main.getWorldHandler().getPlayer().getInventory().getItems().get(i).equals(ItemStack.EMPTY)) {
								Main.getWorldHandler().getPlayer().getInventory().setItemInMouse(Main.getWorldHandler().getPlayer().getInventory().getItems().get(i));
								Main.getWorldHandler().getPlayer().getInventory().getItems().set(i, ItemStack.EMPTY);
							}
						} else {
							if (Main.getWorldHandler().getPlayer().getInventory().getItems().get(i).equals(ItemStack.EMPTY)) {
								Main.getWorldHandler().getPlayer().getInventory().addItemTo(Main.getWorldHandler().getPlayer().getInventory().getItemInMouse(), i);
								Main.getWorldHandler().getPlayer().getInventory().setItemInMouse(null);
							} else if (Main.getWorldHandler().getPlayer().getInventory().getItems().get(i).equals(Main.getWorldHandler().getPlayer().getInventory().getItemInMouse())) {
								Main.getWorldHandler().getPlayer().getInventory().combindItemTo(Main.getWorldHandler().getPlayer().getInventory().getItemInMouse(), i);
							} else {
								ItemStack ti = Main.getWorldHandler().getPlayer().getInventory().getItemInMouse();
								Main.getWorldHandler().getPlayer().getInventory().setItemInMouse(Main.getWorldHandler().getPlayer().getInventory().getItems().get(i));
								Main.getWorldHandler().getPlayer().getInventory().getItems().set(i, ti);
							}
						}
					}
				}
			} else if (Main.getWorldHandler().getPlayer() != null && !Main.getWorldHandler().getPlayer().getInventory().getIsInventoryOpen()) {
				for (int i = 0; i < Main.getWorldHandler().getPlayer().getInventory().getSlotsX(); i++) {
					if (Main.getWorldHandler().getPlayer().getInventory().getSlotsList().get(i).getBoundsAll().intersects(vec.x, vec.y, 1, 1)) {
						Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(i);
					}
				}
			}
			
			if (!tb) {
				leftClick = true;
				Main.getWorldHandler().getPlayer().attack();
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 1) {
			leftClick = false;
		}
	}
}
