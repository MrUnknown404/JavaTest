package main.java.javatest.blocks;

import java.util.concurrent.ThreadLocalRandom;

import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.ObjectHandler;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.Vec2d;

public class Block extends GameObject {
	
	protected BlockProperties type;
	private BlockPos pos = new BlockPos();
	private final static int SIZE = 16;
	
	public Block(BlockPos pos, BlockProperties type) {
		super(pos.x, pos.y, SIZE, SIZE);
		this.pos = pos;
		this.type = type;
		blockUpdate();
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	public void gameTick() {
		
	}
	
	public void blockUpdate() {
		updatePosition();
		ObjectHandler.redoSpecificActive(this);
	}
	
	private void updatePosition() {
		if (!(getPosition().equals(new Vec2d(pos.x * SIZE, pos.y * SIZE)))) {
			setPosition(pos.x * SIZE, pos.y * SIZE);
		}
	}
	
	public BlockProperties getBlockProperties() {
		return type;
	}
	
	public void addBlockPos(BlockPos pos) {
		this.pos = pos.add(pos);
	}
	
	public void addBlockPos(int x, int y) {
		this.pos = pos.add(x, y);
	}
	
	public void addBlockPosX(int x) {
		pos.x += x;
	}
	
	public void addBlockPosY(int y) {
		pos.y += y;
	}
	
	public void setBlockPos(BlockPos pos) {
		this.pos = pos;
	}
	
	public void setBlockPosX(int x) {
		pos.x = x;
	}
	
	public void setBlockPosY(int y) {
		pos.y = y;
	}
	
	public BlockPos getBlockPos() {
		return pos;
	}
	
	public int getBlockPosX() {
		return pos.x;
	}
	
	public int getBlockPosY() {
		return pos.y;
	}
	
	public static int getBlockSize() {
		return SIZE;
	}
	
	/** Returns a random block */
	public static Block getRandomBlock(int x, int y) {
		return new Block(new BlockPos(x, y), BlockProperties.getBlocks().get(ThreadLocalRandom.current().nextInt(1, BlockProperties.getBlocks().size())));
	}
	
	/** Returns a random block */
	public static Block getRandomBlock() {
		return new Block(new BlockPos(0, 0), BlockProperties.getBlocks().get(ThreadLocalRandom.current().nextInt(1, BlockProperties.getBlocks().size())));
	}
}
