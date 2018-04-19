package main.java.javatest.blocks;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.javatest.blocks.util.BlockProperties;
import main.java.javatest.client.JavaGameTest;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.Vec2d;

public class Block extends GameObject {
	
	public final static int SIZE = 16;
	protected boolean isActive = true;
	protected BlockProperties type;
	private BlockPos pos;
	private BufferedImage image;
	
	public Block(BlockPos pos, BlockProperties type) {
		super(pos.x, pos.y, SIZE, SIZE);
		
		this.pos = pos;
		this.type = type;
		
		try {
			image = ImageIO.read(new File("C:\\Github\\JavaTest\\Java-Test\\src\\main\\resources\\javatest\\assets\\textures\\blocks\\" + type.getBlockType().toString().toLowerCase() + ".png"));//getBlockTexture(type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		updatePosition();
	}

	@Override
	public void tick() {
		activeTick();
	}

	@Override
	public void gameTick() {
		activeGameTick();
		updatePosition();
		updateActive();
	}
	
	@Override
	public void render(Graphics g) {
		if (isActive) {
			g.drawImage(image, getBlockPosX() * Block.SIZE, getBlockPosY() * Block.SIZE, width, height, null);
		}
	}
	
	protected void updateActive() {
		if (isActive && (getBlockPosX() < 0) || (getBlockPosX() > JavaGameTest.BLOCK_WIDTH)) {
			isActive = false;
		} else if (!isActive && getBlockPosX() > 0 && getBlockPosX() < JavaGameTest.BLOCK_WIDTH) {
			isActive = true;
		}
	}
	
	public void activeTick() {
		if (!isActive) {
			return;
		}
	}
	
	public void activeGameTick() {
		if (!isActive) {
			return;
		}
	}
	
	private void updatePosition() {
		if (!(getPosition().equals(new Vec2d(pos.x * width, pos.y * height)))) {
			setPosition(pos.x * width, pos.y *height);
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
	
	public boolean getIsActive() {
		return isActive;
	}
}
