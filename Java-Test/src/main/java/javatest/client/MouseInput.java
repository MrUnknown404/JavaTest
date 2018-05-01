package main.java.javatest.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.java.javatest.Main;
import main.java.javatest.blocks.Block;
import main.java.javatest.client.gui.DebugHud;
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
				} else if (b.getBlockPosX() == vec.getX() && b.getBlockPosY() == vec.getY()) {
					pos = null;
					break;
				}
			}
			
			if (pos != null) {
				Renderer.i--;
				World.addBlockAll(Block.getRandomBlock(this.pos.getX(), this.pos.getY()));
				pos = null;
			}
		} else if (e.getButton() == 1) {
			Vec2i pos = new Vec2i((e.getX() - camera.getPositionX()) / Block.getBlockSize(), (e.getY() - camera.getPositionY()) / Block.getBlockSize());
			
			for (int i = 0; i < World.getActiveBlocks().size(); i++) {
				Block b = World.getActiveBlocks().get(i);
					
				if (b.getBlockPos().equals(new BlockPos(pos))) {
					block = b;
				}
			}
			
			if (block != null) {
				Renderer.i--;
				World.removeBlockAll(block);
				block = null;
			}
		}
	}
}
