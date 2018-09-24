package main.java.javatest.world.util;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import main.java.javatest.Main;
import main.java.javatest.blocks.BlockStoneBrick;
import main.java.javatest.blocks.util.Block;
import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.entity.EntityItem;
import main.java.javatest.init.Items;
import main.java.javatest.items.util.ItemStack;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.ChunkPos;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2d;

public class Chunk {

	private static final int size = 8;
	
	private List<Block> blocks = new ArrayList<Block>(size * size);
	private List<BlockLocationData> blockLoc = new ArrayList<BlockLocationData>(size * size);
	
	private ChunkPos pos = new ChunkPos();
	
	public Chunk(ChunkPos pos) {
		this.pos = pos;
		
		for (int i1 = 0; i1 < size; i1++) {
			for (int i2 = 0; i2 < size; i2++) {
				blocks.add(Block.AIR);
			}
		}
		
		for (int i = 0; i < size * size; i++) {
			blockLoc.add(new BlockLocationData((pos.getX() * size) + i - (((MathHelper.clamp(i / size, 0, size)) * 8)), (pos.getY() * size) + MathHelper.clamp(i / size, 0, size), i));
		}
	}
	
	public Chunk(int x, int y) {
		this.pos = new ChunkPos(x, y);
		
		for (int i1 = 0; i1 < size; i1++) {
			for (int i2 = 0; i2 < size; i2++) {
				blocks.add(Block.AIR);
			}
		}
		
		for (int i = 0; i < size * size; i++) {
			blockLoc.add(new BlockLocationData((pos.getX() * size) + i - (((MathHelper.clamp(i / size, 0, size)) * 8)), (pos.getY() * size) + MathHelper.clamp(i / size, 0, size), i));
		}
		
		//temp
		for (int i = 0; i < size * size; i++) {
			blocks.set(i, new BlockStoneBrick(new BlockPos((pos.getX() * size) + i - (((MathHelper.clamp(i / size, 0, size)) * 8)), (pos.getY() * size) + MathHelper.clamp(i / size, 0, size))));
		}
	}
	
	public void tick() {
		for (Block block : blocks) {
			block.tick();
		}
	}
	
	public void replaceBlock(int id, BlockProperties block) {
		BlockPos bp = new BlockPos((pos.getX() * size) + id - (((MathHelper.clamp(id / size, 0, size)) * 8)), (pos.getY() * size) + MathHelper.clamp(id / size, 0, size));
		blocks.set(id, new Block(bp, block));
	}
	
	public void removeBlock(int blockID) {
		Vec2d pos = new Vec2d(blocks.get(blockID).getBlockPos().getX() * Block.getBlockSize(), blocks.get(blockID).getBlockPos().getY() * Block.getBlockSize());
		Main.getWorldHandler().addObjectAll(new EntityItem(pos.x, pos.y, new ItemStack(Items.getItemFromBlock(blocks.get(blockID).getBlockProperties()))));
		blocks.set(blockID, Block.AIR);
	}
	
	public int getBlockID(Block block) {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).equals(block)) {
				return i;
			}
		}
		return 0;
	}
	
	public List<Block> getBlocks() {
		return blocks;
	}
	
	public List<BlockLocationData> getBlockLocationData() {
		return blockLoc;
	}
	
	public ChunkPos getChunkPos() {
		return pos;
	}
	
	public static int getSize() {
		return size;
	}
	
	public Rectangle getChunkBounds() {
		return new Rectangle(MathHelper.floor(pos.getX() * 128), MathHelper.floor(pos.getY() * 128), getSize() * 16, getSize() * 16);
	}
	
	public Rectangle getChunkActiveBounds() {
		return new Rectangle(MathHelper.floor((pos.getX() * 128) - 16), MathHelper.floor((pos.getY() * 128) - 16), (getSize() * 16) + 32, (getSize() * 16) + 32);
	}
}
