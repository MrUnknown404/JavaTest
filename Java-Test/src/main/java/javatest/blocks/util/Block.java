package main.java.javatest.blocks.util;

import main.java.javatest.util.TickableGameObject;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.Vec2d;

public class Block extends TickableGameObject {
	protected BlockProperties type;
	private BlockPos bPos = new BlockPos();
	private final static int SIZE = 16;
	
	public float brokenness;
	
	public static final Block AIR = new BlockAir();
	
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
}
