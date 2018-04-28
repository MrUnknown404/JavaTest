package main.java.javatest.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.util.EnumRenderKey;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.Resource;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.Vec2d;

public class Block extends GameObject {
	
	public final static int SIZE = 16;
	protected BlockProperties type;
	private BlockPos pos = new BlockPos();
	private final BufferedImage image;
	
	public Block(BlockPos pos, BlockProperties type) {
		super(pos.x, pos.y, SIZE, SIZE, EnumRenderKey.block);
		this.pos = pos;
		this.type = type;
		
		image = Resource.getTexture(Resource.ResourceType.blocks, type.getBlockType());
		
		updatePosition();
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	public void gameTick() {
		
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(image, (int) getPositionX(), (int) getPositionY(), SIZE, SIZE, null);
		if (getLightLevel() != 0) {
			g.setColor(new Color(0, 0, 0, getLightLevel()));
			g.fillRect((int) getPositionX(), (int) getPositionY(), SIZE, SIZE);
		}
	}
	
	private void updatePosition() {
		if (!(getPosition().equals(new Vec2d(pos.x * SIZE, pos.y * SIZE)))) {
			setPosition(pos.x * SIZE, pos.y * SIZE);
		}
	}
	
	public BlockProperties getBlockType() {
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
}
