package main.java.javatest.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.annotation.Nullable;

import main.java.javatest.client.JavaGameTest;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.math.BlockPos;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2d;

public class Block extends GameObject {

	public final static int SIZE = 16;
	protected boolean isActive = true;
	private Color color;
	private BlockPos pos;
	
	public Block(BlockPos pos, @Nullable Color color) {
		super(pos.x, pos.y, SIZE, SIZE);
		
		if (color == null) {
			color = new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat());
		}
		
		this.pos = pos;
		this.color = color;
		
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
	
	@Override
	public void render(Graphics g) {
		if (isActive) {
			g.setColor(color);
			g.fillRect(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width, height);
		}
		
		/* draw hitboxes
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.CYAN);
		g2d.draw(getBoundsAll());
		g.setColor(Color.RED);
		g2d.draw(getBoundsTop());
		g.setColor(Color.BLUE);
		g2d.draw(getBoundsBottom());
		g.setColor(Color.GREEN);
		g2d.draw(getBoundsLeft());
		g.setColor(Color.YELLOW);
		g2d.draw(getBoundsRight());
		//*/
	}
}
