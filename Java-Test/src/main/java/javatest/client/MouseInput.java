package main.java.javatest.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import main.java.javatest.Main;
import main.java.javatest.blocks.Block;
import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.client.gui.DebugHud;
import main.java.javatest.init.EnumBlocks;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2d;
import main.java.javatest.util.math.Vec2i;
import main.java.javatest.world.World;

public class MouseInput extends MouseAdapter {

	private Vec2i pos = new Vec2i();
	private Block block;
	private Camera camera = Main.getCamera();
	
	public static Vec2d vec = new Vec2d();
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (World.getPlayer() != null) {
			if (e.getWheelRotation() == 1) {
				if (World.getPlayer().getInventory().getSelectedSlot() != World.getPlayer().getInventory().getSlotsX() - 1) {
					World.getPlayer().getInventory().setSelectedSlot(World.getPlayer().getInventory().getSelectedSlot() + 1);
				} else {
					World.getPlayer().getInventory().setSelectedSlot(0);
				}
			} else {
				if (World.getPlayer().getInventory().getSelectedSlot() != 0) {
					World.getPlayer().getInventory().setSelectedSlot(World.getPlayer().getInventory().getSelectedSlot() - 1);
				} else {
					World.getPlayer().getInventory().setSelectedSlot(9);
				}
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		DebugHud.setMouseVec(new Vec2i(MathHelper.floor((e.getX() - camera.getPositionX()) / Block.getBlockSize()), MathHelper.floor((e.getY() - camera.getPositionY()) / Block.getBlockSize())));
		vec = new Vec2d(e.getX(), e.getY());
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (!World.doesWorldExist) {
			return;
		}
		if (e.getButton() == 3) {
			Vec2i vec = new Vec2i((e.getX() - camera.getPositionX()) / Block.getBlockSize(), (e.getY() - camera.getPositionY()) / Block.getBlockSize());
			
			for (int i = 0; i < World.getActiveBlocks().size(); i++) {
				Block b = World.getActiveBlocks().get(i);
				
				if (!b.getBlockPos().equals(new BlockPos(vec))) {
					pos = vec;
				} else if (b.getBlockPos().equals(new BlockPos(vec))) {
					pos = null;
					break;
				}
			}
			
			if (pos != null) {
				Block b = null;
				if (EnumBlocks.findItemWithString(World.getPlayer().getInventory().getItems().get(World.getPlayer().getInventory().getSelectedSlot()).getItem().getName()) == null) {
					return;
				} else if (World.getPlayer().getInventory().getItems().size() > World.getPlayer().getInventory().getSelectedSlot() && World.getPlayer().getInventory().getItems().get(World.getPlayer().getInventory().getSelectedSlot()).getCount() != 0) {
					b = new Block(new BlockPos(this.pos.getX(), this.pos.getY()), BlockProperties.findBlockPropertyWithName(World.getPlayer().getInventory().getItems().get(World.getPlayer().getInventory().getSelectedSlot()).getItem().getName()));
				}
				
				if (b == null) {
					return;
				}
				
				if (b.getBlockPosX() < 0 || b.getBlockPosX() + 1 > World.getWorldInfo().worldLength) {
					World.redoSpecificActiveBlock(b);
					pos = null;
					return;
				} else if (b.getBlockPosY() < 0 || b.getBlockPosY() > World.getWorldInfo().worldHeight * 2) {
					World.redoSpecificActiveBlock(b);
					pos = null;
					return;
				}
				
				World.getPlayer().getInventory().getItems().get(World.getPlayer().getInventory().getSelectedSlot()).decreaseCount();
				World.addBlockAll(b);
				pos = null;
			}
		} else if (e.getButton() == 1) {
			Vec2i pos = new Vec2i((e.getX() - camera.getPositionX()) / Block.getBlockSize(), (e.getY() - camera.getPositionY()) / Block.getBlockSize());
			
			for (int i = 0; i < World.getActiveBlocks().size(); i++) {
				Block b = World.getActiveBlocks().get(i);
				
				if (b.getBlockPos().equals(new BlockPos(pos))) {
					block = b;
					break;
				}
			}
			
			if (block != null) {
				World.removeBlockAll(block);
				block = null;
			}
		}
	}
}
