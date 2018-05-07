package main.java.javatest.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import main.java.javatest.Main;
import main.java.javatest.blocks.Block;
import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.client.gui.DebugHud;
import main.java.javatest.init.Blocks;
import main.java.javatest.items.ItemStack;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2i;

public class MouseInput extends MouseAdapter {

	private Vec2i pos = new Vec2i();
	private Block block;
	private Camera camera = Main.getCamera();
	
	public static Vec2i vec = new Vec2i();
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (Main.getWorldHandler().getWorld().getPlayer() != null) {
			if (e.getWheelRotation() == 1) {
				if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() != Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsX() - 1) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() + 1);
				} else {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(0);
				}
			} else {
				if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() != 0) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() - 1);
				} else {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(9);
				}
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		vec = new Vec2i(e.getX() / Main.scaleWidth, e.getY() / Main.scaleHeight);
		DebugHud.setMouseVec(new Vec2i(MathHelper.floor(((vec.x) - camera.getPositionX()) / Block.getBlockSize()), MathHelper.floor(((vec.y) - camera.getPositionY()) / Block.getBlockSize())));
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (!Main.getWorldHandler().doesWorldExist || Main.getCommandConsole().isConsoleOpen) {
			return;
		}
		
		if (e.getButton() == 3) {
			if (Main.getWorldHandler().getWorld().getPlayer() != null && Main.getWorldHandler().getWorld().getPlayer().getInventory().getIsInventoryOpen()) {
				if (Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse == null) {
					for (int i = 0; i < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlots(); i++) {
						if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsList().get(i).getBoundsAll().intersects(vec.x, vec.y, 1, 1)) {
							if (!Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).equals(ItemStack.EMPTY)) {
								int ic1 = Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).getCount();
								if (ic1 > 1) {
									if ((ic1 & 1) == 0) {
										Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse = new ItemStack(ic1 / 2, Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).getItem());
										Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).setCount(ic1 / 2);
									} else {
										Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse = new ItemStack(ic1 / 2 + 1, Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).getItem());
										Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).setCount(ic1 / 2);
									}
								} else {
									Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse = Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i);
									Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).setCount(0);
								}
							}
						}
					}
				} else {
					for (int i = 0; i < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlots(); i++) {
						if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsList().get(i).getBoundsAll().intersects(vec.x, vec.y, 1, 1)) {
							if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).equals(ItemStack.EMPTY)) {
								if (Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse != null) {
									Main.getWorldHandler().getWorld().getPlayer().getInventory().addItemTo(new ItemStack(1, Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse.getItem()), i);
									Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse.decreaseCount();
								}
							} else if (Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse != null && Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).equals(Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse)) {
								Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse.decreaseCount();
								Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).increaseCount();
							}
						}
					}
				}
			}
			
			Vec2i tPos = new Vec2i(((vec.x) - camera.getPositionX()) / Block.getBlockSize(), ((vec.y) - camera.getPositionY()) / Block.getBlockSize());
			
			for (int i = 0; i < Main.getWorldHandler().getWorld().getActiveBlocks().size(); i++) {
				Block b = Main.getWorldHandler().getWorld().getActiveBlocks().get(i);
				
				if (!b.getBlockPos().equals(new BlockPos(tPos))) {
					pos = tPos;
				} else if (b.getBlockPos().equals(new BlockPos(tPos))) {
					pos = null;
					break;
				}
			}
			
			if (pos != null) {
				Block b = null;
				if (Blocks.findBlock(Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot()).getItem().getName()) == null) {
					return;
				} else if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().size() > Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() && Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot()).getCount() != 0) {
					b = new Block(new BlockPos(this.pos.getX(), this.pos.getY()), BlockProperties.findBlockPropertyWithName(Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot()).getItem().getName()));
				}
				
				if (b == null) {
					return;
				}
				
				if (b.getBlockPosX() < 0 || b.getBlockPosX() + 1 > Main.getWorldHandler().getWorld().getWorldInfo().worldLength) {
					Main.getWorldHandler().getWorld().redoSpecificActiveObject(b);
					pos = null;
					return;
				} else if (b.getBlockPosY() < 0 || b.getBlockPosY() > Main.getWorldHandler().getWorld().getWorldInfo().worldHeight * 2) {
					Main.getWorldHandler().getWorld().redoSpecificActiveObject(b);
					pos = null;
					return;
				}
				
				if (Main.getWorldHandler().getWorld().getPlayer().getInteractionBounds().intersects(b.getBoundsAll())) {
					if (!Main.getWorldHandler().getWorld().getPlayer().getBoundsAll().intersects(b.getBoundsAll())) {
						Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot()).decreaseCount();
						Main.getWorldHandler().getWorld().addObjectAll(b);
					}
				}
				pos = null;
			}
		} else if (e.getButton() == 1) {
			if (Main.getWorldHandler().getWorld().getPlayer() != null && Main.getWorldHandler().getWorld().getPlayer().getInventory().getIsInventoryOpen()) {
				if (Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse == null) {
					for (int i = 0; i < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlots(); i++) {
						if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsList().get(i).getBoundsAll().intersects(vec.x, vec.y, 1, 1)) {
							if (!Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).equals(ItemStack.EMPTY)) {
								Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse = Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i);
								Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().set(i, ItemStack.EMPTY);
							}
						}
					}
				} else {
					for (int i = 0; i < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlots(); i++) {
						if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsList().get(i).getBoundsAll().intersects(vec.x, vec.y, 1, 1)) {
							if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).equals(ItemStack.EMPTY)) {
								Main.getWorldHandler().getWorld().getPlayer().getInventory().addItemTo(Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse, i);
								Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse = null;
							} else if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).equals(Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse)) {
								Main.getWorldHandler().getWorld().getPlayer().getInventory().combindItemTo(Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse, i);
							} else {
								ItemStack ti = Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse;
								Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse = Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i);
								Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().set(i, ti);
							}
						}
					}
				}
			} else if (Main.getWorldHandler().getWorld().getPlayer() != null && !Main.getWorldHandler().getWorld().getPlayer().getInventory().getIsInventoryOpen()) {
				for (int i = 0; i < Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlots(); i++) {
					if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsList().get(i).getBoundsAll().intersects(vec.x, vec.y, 1, 1)) {
						Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(i);
					}
				}
			}
			
			Vec2i tPos = new Vec2i(((vec.x) - camera.getPositionX()) / Block.getBlockSize(), ((vec.y) - camera.getPositionY()) / Block.getBlockSize());
			
			for (int i = 0; i < Main.getWorldHandler().getWorld().getActiveBlocks().size(); i++) {
				Block b = Main.getWorldHandler().getWorld().getActiveBlocks().get(i);
				
				if (b.getBlockPos().equals(new BlockPos(tPos))) {
					block = b;
					break;
				}
			}
			
			if (block != null) {
				if (Main.getWorldHandler().getWorld().getPlayer().getInteractionBounds().intersects(block.getBoundsAll())) {
					Main.getWorldHandler().getWorld().removeObjectAll(block, true);
				}
				block = null;
			}
		}
	}
}
