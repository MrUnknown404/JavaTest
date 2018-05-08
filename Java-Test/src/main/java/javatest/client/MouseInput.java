package main.java.javatest.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.Timer;

import main.java.javatest.Main;
import main.java.javatest.blocks.Block;
import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.client.gui.DebugHud;
import main.java.javatest.items.Item;
import main.java.javatest.items.ItemStack;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2i;

public class MouseInput extends MouseAdapter {

	private Vec2i pos = new Vec2i();
	private Block block;
	private Camera camera = Main.getCamera();
	
	public static Vec2i vec = new Vec2i();
	
	private boolean leftClick;
	private int it = -2;
	
	private Timer t = new Timer(1000 / 60, new ActionListener () {
		@Override
		public void actionPerformed (ActionEvent e) {
			if (block == null) {
				t.stop();
				return;
			} else if (!block.getBlockPos().equals(new BlockPos(((vec.x) - camera.getPositionX()) / Block.getBlockSize(), ((vec.y) - camera.getPositionY()) / Block.getBlockSize()))) {
				it = -2;
				block.brokenness = 0;
				block = null;
				t.stop();
				return;
			} else if (!(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedItem().getItem().getItemType() == Item.ItemType.tool)) {
				it = -2;
				block.brokenness = 0;
				block = null;
				t.stop();
				return;
			} else if (it == -2) {
				it = (int) (block.getBlockProperties().getHardness() / Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedItem().getItem().getSpeed());
			}
			if (it == -1) {
				return;
			} else if (it != 0) {
				it--;
				block.brokenness = MathHelper.normalize(it, block.getBlockProperties().getHardness() / Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedItem().getItem().getSpeed());
				return;
			}
			
			it = -2;
			Main.getWorldHandler().getWorld().removeObjectAll(block, true);
			block = null;
			t.stop();
		}
	});
	
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
		if (Main.getWorldHandler().getWorld().getPlayer() != null) {
			if (vec.x < Main.WIDTH_DEF / 2) {
				Main.getWorldHandler().getWorld().getPlayer().direction = -1;
			} else {
				Main.getWorldHandler().getWorld().getPlayer().direction = 1;
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		vec = new Vec2i(e.getX() / Main.scaleWidth, e.getY() / Main.scaleHeight);
		if (leftClick) {
			if (block != null) {
				if (!block.getBlockPos().equals(new BlockPos(((vec.x) - camera.getPositionX()) / Block.getBlockSize(), ((vec.y) - camera.getPositionY()) / Block.getBlockSize()))) {
					getBlock();
				}
			} else {
				getBlock();
			}
		}
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
										System.out.println("1");
										Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse = new ItemStack(ic1 / 2, Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).getItem());
										Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).setCount(ic1 / 2);
									} else {
										System.out.println("2");
										Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse = new ItemStack(ic1 / 2 + 1, Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).getItem());
										Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(i).setCount(ic1 / 2);
									}
								} else {
									System.out.println("3");
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
				if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedItem() == ItemStack.EMPTY) {
					return;
				} else if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedItem().getCount() != 0) {
					b = new Block(new BlockPos(this.pos.getX(), this.pos.getY()), BlockProperties.findBlockPropertyWithName(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedItem().getItem().getName()));
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
						Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedItem().decreaseCount();
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
			getBlock();
			leftClick = true;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		it = -2;
		if (block != null) {
			block.brokenness = 0;
			block = null;
		}
		leftClick = false;
		if (t.isRunning()) {
			t.stop();
		}
	}
	
	public void getBlock() {
		if (t.isRunning()) {
			block.brokenness = 0;
			it = -2;
			t.stop();
		}
		
		if (block != null) {
			block.brokenness = 0;
		}
		block = null;
		it = -2;
		
		Vec2i tPos = new Vec2i(((vec.x) - camera.getPositionX()) / Block.getBlockSize(), ((vec.y) - camera.getPositionY()) / Block.getBlockSize());
		
		if (!(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedItem().getItem().getItemType() == Item.ItemType.tool)) {
			return;
		}
		
		for (int i = 0; i < Main.getWorldHandler().getWorld().getActiveBlocks().size(); i++) {
			Block b = Main.getWorldHandler().getWorld().getActiveBlocks().get(i);
			if (b == null) {
				return;
			}
			
			if (b.getBlockPos().equals(new BlockPos(tPos))) {
				block = b;
				break;
			}
		}
	}
	
	public void tick() {
		if (leftClick && !t.isRunning()) {
			if (block != null) {
				if (Main.getWorldHandler().getWorld().getPlayer().getInteractionBounds().intersects(block.getBoundsAll())) {
					t.setInitialDelay(0);
					t.start();
				}
			} else {
				getBlock();
				it = -2;
			}
		}
	}
}
