package main.java.javatest.items;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.java.javatest.Main;
import main.java.javatest.blocks.util.Block;
import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.client.MouseInput;
import main.java.javatest.items.util.ItemTool;
import main.java.javatest.util.math.Vec2i;
import main.java.javatest.world.util.BlockLocationData;
import main.java.javatest.world.util.Chunk;

public class ItemBlock extends ItemTool {

	protected BlockProperties block;
	
	public ItemBlock(BlockProperties block) {
		super(block.getBlockType().toString(), 32, 0, 0, 0.65f);
		forceMaxStack(999);
		this.block = block;
	}
	
	@Override
	public void useLeft() {
		
	}
	
	private int tiMax = (int) (speed * 10), ti;
	
	@Override
	public void useRight() {
		if (ti != 0) {
			if (!tiMinusT.isRunning()) {
				tiMinusT.start();
			}
			return;
		}
		ti = tiMax;
		
		Chunk cnk = Main.getWorldHandler().getChunkAtMouse();
		Vec2i tPos = new Vec2i((MouseInput.vec.x - Main.getCamera().getPositionX()) / Block.getBlockSize(), (MouseInput.vec.y - Main.getCamera().getPositionY()) / Block.getBlockSize());
		
		if (cnk == null) {
			return;
		}
		
		for (int i = 0; i < cnk.getBlockLocationData().size(); i++) {
			BlockLocationData b = cnk.getBlockLocationData().get(i);
			
			if (!b.getBoundsAll().intersects(Main.getWorldHandler().getPlayer().getInteractionBounds())) {
				continue;
			}
			
			if (b.getBoundsAll().intersects(new Rectangle(tPos.x * Block.getBlockSize(), tPos.y * Block.getBlockSize(), 1, 1))) {
				if (cnk.getBlocks().get(i).getBlockProperties() == BlockProperties.AIR) {
					cnk.replaceBlock(i, block);
					Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().decreaseCount();
					break;
				}
			}
		}
	}
	
	@Override
	public void onRightClickRelease() {
		super.onRightClickRelease();
		if (!tiMinusT.isRunning()) {
			tiMinusT.start();
		}
	}
	
	private Timer tiMinusT = new Timer(1000 / 60, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (ti > 0) {
				ti--;
			} else {
				tiMinusT.stop();
			}
		}
	});
	
	public BlockProperties getBlock() {
		return block;
	}
}
