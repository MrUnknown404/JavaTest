package main.java.javatest.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.java.javatest.blocks.Block;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.handlers.ObjectHandler;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.MathHelper;

public class MouseInput extends MouseAdapter {

	private BlockPos pos;
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 3) {
			BlockPos pos = new BlockPos(MathHelper.floor(e.getX() / Block.SIZE), MathHelper.floor(e.getY() / Block.SIZE));
			
			System.out.println("--M: " + pos.toString());
			
			for (int i = 0; i < ObjectHandler.objects.size(); i++) {
				GameObject o = ObjectHandler.objects.get(i);
				if (o instanceof Block) {
					Block b = (Block) o;
					
					if (b.getBlockPosX() != pos.x && b.getBlockPosY() != pos.y) {
						this.pos = pos;
					} else if (b.getBlockPosX() == pos.x && b.getBlockPosY() == pos.y) {
						this.pos = null;
					}
				}
			}
			
			if (this.pos != null) {
				ObjectHandler.addObject(new Block(new BlockPos(this.pos.x, this.pos.y)));
				this.pos = null;
			}
		} else if (e.getButton() == 1) {
			BlockPos pos = new BlockPos(e.getX() / Block.SIZE, e.getY() / Block.SIZE);
			for (int i = 0; i < ObjectHandler.objects.size(); i++) {
				GameObject o = ObjectHandler.objects.get(i);
				if (o instanceof Block) {
					Block b = (Block) o;
					
					if (b.getBlockPosX() == pos.x && b.getBlockPosY() == pos.y) {
						ObjectHandler.removeObject(b);
					}
				}
			}
		}
	}
}
