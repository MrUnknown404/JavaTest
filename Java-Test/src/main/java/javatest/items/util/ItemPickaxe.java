package main.java.javatest.items.util;

import java.awt.Rectangle;

import main.java.javatest.Main;
import main.java.javatest.blocks.util.Block;
import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.client.MouseInput;
import main.java.javatest.util.math.Vec2i;
import main.java.javatest.world.util.BlockLocationData;
import main.java.javatest.world.util.Chunk;

public class ItemPickaxe extends ItemTool {

	protected Block block = null;
	
	public ItemPickaxe(String name, int range, int critChance, float damage, float speed) {
		super(name, range, critChance, damage, speed);
	}
	
	@Override
	public void onLeftClickRelease() {
		super.onLeftClickRelease();
		if (block != null) {
			block.brokenness = 0;
			block = null;
		}
	}
	
	@Override
	public void useLeft() {
		Chunk cnk = Main.getWorldHandler().getChunkAtMouse();
		Vec2i tPos = new Vec2i((MouseInput.vec.x - Main.getCamera().getPositionX()) / Block.getBlockSize(), (MouseInput.vec.y - Main.getCamera().getPositionY()) / Block.getBlockSize());
		
		if (cnk == null) {
			if (block != null) {
				block.brokenness = 0;
				block = null;
			}
			return;
		}
		
		boolean tb = false;
		for (int i = 0; i < cnk.getBlockLocationData().size(); i++) {
			BlockLocationData b = cnk.getBlockLocationData().get(i);
			
			if (!b.getBoundsAll().intersects(Main.getWorldHandler().getPlayer().getInteractionBounds())) {
				continue;
			}
			
			if (b.getBoundsAll().intersects(new Rectangle(tPos.x * Block.getBlockSize(), tPos.y * Block.getBlockSize(), 1, 1))) {
				if (cnk.getBlocks().get(i).getBlockProperties() != BlockProperties.AIR) {
					int it = cnk.getBlocks().get(i).getBlockProperties().getHardness();
					
					cnk.getBlocks().get(i).brokenness += (speed / it) / 10;
					
					if (cnk.getBlocks().get(i).brokenness >= 1) {
						cnk.removeBlock(cnk.getBlockID(cnk.getBlocks().get(i)));
					}
					
					if (block != null && block != cnk.getBlocks().get(i)) {
						block.brokenness = 0;
						block = null;
					}
					
					block = cnk.getBlocks().get(i);
					
					tb = true;
					break;
				}
			}
		}
		
		if (!tb) {
			if (block != null) {
				block.brokenness = 0;
				block = null;
			}
		}
	}
	
	@Override
	public void useRight() {
		
	}
}
