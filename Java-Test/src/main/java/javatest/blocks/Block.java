package main.java.javatest.blocks;

import java.util.concurrent.ThreadLocalRandom;

import main.java.javatest.Main;
import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.init.Blocks;
import main.java.javatest.util.TickableGameObject;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.Vec2d;

public class Block extends TickableGameObject {
	protected BlockProperties type;
	private BlockPos bPos = new BlockPos();
	private final static int SIZE = 16;
	
	public Block(BlockPos bPos, BlockProperties type) {
		super(bPos.getX(), bPos.getY(), SIZE, SIZE);
		this.bPos = bPos;
		this.type = type;
		
		blockUpdate();
	}
	
	@Override
	public void tick() {
		
	}
	
	public void blockUpdate() {
		updatePosition();
		Main.getWorldHandler().redoSpecificActiveObject(this);
	}
	
	private void updatePosition() {
		if (!(getPosition().equals(new Vec2d(bPos.getX() * SIZE, bPos.getY() * SIZE)))) {
			setPosition(bPos.getX() * SIZE, bPos.getY() * SIZE);
		}
	}
	
	public BlockProperties getBlockProperties() {
		return type;
	}
	
	public BlockPos getBlockPos() {
		return bPos;
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
