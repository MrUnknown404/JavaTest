package main.java.javatest.blocks;

import java.util.concurrent.ThreadLocalRandom;

import main.java.javatest.Main;
import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.init.Blocks;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.Vec2d;

public class Block extends GameObject {
	protected BlockProperties type;
	private BlockPos bPos = new BlockPos();
	private final static int SIZE = 16;
	public float brokenness;
	
	public Block(BlockPos bPos, BlockProperties type) {
		super(bPos.x, bPos.y, SIZE, SIZE);
		this.bPos = bPos;
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
		Main.getWorldHandler().getWorld().redoSpecificActiveObject(this);
	}
	
	private void updatePosition() {
		if (!(getPosition().equals(new Vec2d(bPos.x * SIZE, bPos.y * SIZE)))) {
			setPosition(bPos.x * SIZE, bPos.y * SIZE);
		}
	}
	
	public BlockProperties getBlockProperties() {
		return type;
	}
	
	public void addBlockPos(BlockPos bPos) {
		this.bPos = bPos.add(bPos);
	}
	
	public void addBlockPos(int x, int y) {
		this.bPos = bPos.add(x, y);
	}
	
	public void addBlockPosX(int x) {
		bPos.x += x;
	}
	
	public void addBlockPosY(int y) {
		bPos.y += y;
	}
	
	public void setBlockPos(BlockPos bPos) {
		this.bPos = bPos;
	}
	
	public void setBlockPosX(int x) {
		bPos.x = x;
	}
	
	public void setBlockPosY(int y) {
		bPos.y = y;
	}
	
	public BlockPos getBlockPos() {
		return bPos;
	}
	
	public int getBlockPosX() {
		return bPos.x;
	}
	
	public int getBlockPosY() {
		return bPos.y;
	}
	
	public static int getBlockSize() {
		return SIZE;
	}
	
	public String getBlockName() {
		return "Block" + getBlockProperties().getBlockType().toString().replace(getBlockProperties().getBlockType().toString().substring(0, 1), getBlockProperties().getBlockType().toString().substring(0, 1).toUpperCase());
	}
	
	/** Returns a random block */
	public static Block getRandomBlock(int x, int y) {
		return new Block(new BlockPos(x, y), Blocks.getBlocks().get(ThreadLocalRandom.current().nextInt(1, Blocks.getBlocks().size() - 1)));
	}
	
	/** Returns a random block */
	public static Block getRandomBlock() {
		return new Block(new BlockPos(0, 0), Blocks.getBlocks().get(ThreadLocalRandom.current().nextInt(1, Blocks.getBlocks().size() - 1)));
	}
}
