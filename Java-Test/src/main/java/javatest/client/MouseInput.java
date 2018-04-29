package main.java.javatest.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.java.javatest.blocks.Block;
import main.java.javatest.client.gui.DebugHud;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.ObjectHandler;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2i;

public class MouseInput extends MouseAdapter {

	private Vec2i pos = new Vec2i();
	private Block block;
	private Camera camera;
	
	public MouseInput(Camera camera) {
		DebugHud.setMouseVec(new Vec2i(MathHelper.floor(pos.getX() / Block.getBlockSize()), MathHelper.floor(pos.getY() / Block.getBlockSize())));
		this.camera = camera;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		DebugHud.setMouseVec(new Vec2i(MathHelper.floor((e.getX() - camera.getPositionX()) / Block.getBlockSize()), MathHelper.floor((e.getY() - camera.getPositionY()) / Block.getBlockSize())));
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 3) {
			Vec2i vec = new Vec2i((e.getX() - camera.getPositionX()) / Block.getBlockSize(), (e.getY() - camera.getPositionY()) / Block.getBlockSize());
			
			int tempInt = 0;
			
			for (int i = 0; i < ObjectHandler.getObjectsAll().size(); i++) {
				GameObject o = ObjectHandler.getObjectsAll().get(i);
				if (o instanceof Block) {
					Block b = (Block) o;
					tempInt += 1;
					
					if (b.getBlockPosX() != vec.getX() && b.getBlockPosY() != vec.getY()) {
						pos = vec;
					} else if (b.getBlockPosX() == vec.getX() && b.getBlockPosY() == vec.getY()) {
						pos = null;
						break;
					}
				}
				if (tempInt == 0) {
					pos = vec;
				}
			}
			
			if (pos != null) {
				Renderer.i--;
				ObjectHandler.addObjectAll(Block.getRandomBlock(this.pos.getX(), this.pos.getY()));
				pos = null;
			}
		} else if (e.getButton() == 1) {
			Vec2i pos = new Vec2i((e.getX() - camera.getPositionX()) / Block.getBlockSize(), (e.getY() - camera.getPositionY()) / Block.getBlockSize());
			for (int i = 0; i < ObjectHandler.getObjectsAll().size(); i++) {
				GameObject o = ObjectHandler.getObjectsAll().get(i);
				if (o instanceof Block) {
					Block b = (Block) o;
					
					if (b.getBlockPosX() == pos.getX() && b.getBlockPosY() == pos.getY()) {
						block = b;
					}
				}
			}
			
			if (block != null) {
				Renderer.i--;
				ObjectHandler.removeObjectAll(block);
				block = null;
			}
		}
	}
}
