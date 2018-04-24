package main.java.javatest.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.java.javatest.blocks.Block;
import main.java.javatest.blocks.BlockStone;
import main.java.javatest.client.gui.DebugHud;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.handlers.ObjectHandler;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2i;

public class MouseInput extends MouseAdapter {

	private Vec2i pos;
	private Block block;
	
	@Override
	public void mouseMoved(MouseEvent e) {
		DebugHud.setMouseVec(new Vec2i(MathHelper.floor(e.getX() / Block.SIZE), MathHelper.floor(e.getY() / Block.SIZE)));
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 3) {
			Vec2i vec = new Vec2i(MathHelper.floor(e.getX() / Block.SIZE), MathHelper.floor(e.getY() / Block.SIZE));
			
			int tempInt = 0;
			
			for (int i = 0; i < ObjectHandler.getObjectsActive().size(); i++) {
				GameObject o = ObjectHandler.getObjectsActive().get(i);
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
				ObjectHandler.addObjectAll(new BlockStone(new BlockPos(this.pos.getX(), this.pos.getY())));
				pos = null;
			}
		} else if (e.getButton() == 1) {
			Vec2i pos = new Vec2i(e.getX() / Block.SIZE, e.getY() / Block.SIZE);
			for (int i = 0; i < ObjectHandler.getObjectsActive().size(); i++) {
				GameObject o = ObjectHandler.getObjectsActive().get(i);
				if (o instanceof Block) {
					Block b = (Block) o;
					
					if (b.getBlockPosX() == pos.getX() && b.getBlockPosY() == pos.getY()) {
						block = b;
					}
				}
			}
			
			if (block != null) {
				ObjectHandler.removeObjectAll(block);
				block = null;
			}
		}
	}
}
