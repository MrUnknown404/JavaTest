package main.java.javatest.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.java.javatest.Main;
import main.java.javatest.blocks.Block;
import main.java.javatest.client.MouseInput;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2d;
import main.java.javatest.util.math.Vec2i;
import main.java.javatest.world.World;

public class DebugHud extends Canvas {

	private static final long serialVersionUID = -4835862860408839539L;
	private final Font FONT = new Font("Font", Font.BOLD, 16);
	
	private static String mouseString = "(0, 0)";
	private static String mouseWorldString = "(0, 0)";
	private static String mouseWorldBlockString = "(0, 0)";
	private static String posString = "";
	private static String blockPosString = "";
	private static int blockCountAll;
	private static int blockCountActive;
	private static double gravityY;
	private static String generating = "";
	
	private static final String PRESS1 = "Press 1 to generate a new world!";
	private static final String PRESS2 = "Press 2 to save the world!";
	private static final String PRESS3 = "Press 3 to load a world!";
	private static final String PRESS4 = "Press 4 to reset the world!";
	private static final String SAVING = "Saving the world...";
	private static final String LOADING = "Loading the world...";
	
	public void getInfo() {
		if (World.getPlayer() != null) {
			Vec2d pos = new Vec2d(World.getPlayer().getPositionX(), World.getPlayer().getPositionY() + World.getPlayer().getHeight());
			posString = pos.toStringInt();
			blockPosString = new Vec2i(MathHelper.floor(World.getPlayer().getPositionX() / Block.getBlockSize()), MathHelper.floor((World.getPlayer().getPositionY() + World.getPlayer().getHeight()) / Block.getBlockSize())).toString();
			
			gravityY = MathHelper.roundTo((World.getPlayer()).getGravityY(), 3);
		}
		generating = "Generating a new world... " + MathHelper.roundTo(World.getAmountGenerated(), 1) + "%";
		blockCountAll = World.getAllBlocks().size();
		blockCountActive = World.getActiveBlocks().size();
	}
	
	public static void setMouseVec(Vec2i vec) {
		mouseString = vec.toString();
		if (World.getPlayer() != null) {
			mouseWorldString = new Vec2i(MouseInput.vec.x - Main.getCamera().getPositionX(), MouseInput.vec.y - Main.getCamera().getPositionY()).toString();
			mouseWorldBlockString = new Vec2i((MouseInput.vec.x - Main.getCamera().getPositionX()) / Block.getBlockSize(), (MouseInput.vec.y - Main.getCamera().getPositionY()) / Block.getBlockSize()).toString();
		}
	}
	
	public void drawText(Graphics g, String fps) {
		int x = 1, y = 15;
		g.setColor(Color.GREEN);
		g.setFont(FONT);
		g.drawString(fps, x, y);
		
		if (!World.doesWorldExist && !World.isGeneratingWorld && !World.isSaving && !World.isLoading) {
			final int w1 = Main.WIDTH / 2 - (int) g.getFontMetrics().getStringBounds(PRESS1, g).getWidth() / 2;
			final int h1 = Main.HEIGHT / 2 - (int) g.getFontMetrics().getStringBounds(PRESS1, g).getHeight() / 2;
			final int w2 = Main.WIDTH / 2 - (int) g.getFontMetrics().getStringBounds(PRESS3, g).getWidth() / 2;
			final int h2 = Main.HEIGHT / 2 - (int) g.getFontMetrics().getStringBounds(PRESS3, g).getHeight() / 2;
			
			g.drawString(PRESS1, w1, h1);
			g.drawString(PRESS3, w2, h2 + 16);
		} else if (World.isGeneratingWorld && !World.isSaving && !World.isLoading) {
			final int w = Main.WIDTH / 2 - (int) g.getFontMetrics().getStringBounds(generating, g).getWidth() / 2;
			final int h = Main.HEIGHT / 2 - (int) g.getFontMetrics().getStringBounds(generating, g).getHeight() / 2;
			
			g.drawString(generating, w, h);
		} else if (World.doesWorldExist && !World.isGeneratingWorld && !World.isSaving && !World.isLoading) {
			final int w1 = Main.WIDTH / 2 - (int) g.getFontMetrics().getStringBounds(PRESS4, g).getWidth() / 2;
			final int w2 = Main.WIDTH / 2 - (int) g.getFontMetrics().getStringBounds(PRESS2, g).getWidth() / 2;
			
			g.drawString(PRESS4, w1, 16);
			g.drawString(PRESS2, w2, 32);
			
			g.drawString("Mouse pos: " + mouseString, x, y += 32);
			g.drawString("Mouse world pos: " + mouseWorldString, x, y += 16);
			g.drawString("Mouse world block pos: " + mouseWorldBlockString, x, y += 16);
			g.drawString("Blocks active: " + blockCountActive, x, y += 16);
			g.drawString("Blocks all: " + blockCountAll, x, y += 16);
			
			g.drawString("Player pos: " + posString, x, y += 32);
			g.drawString("Player block pos: " + blockPosString, x, y += 16);
			g.drawString("Player Gravity: " + gravityY, x, y += 16);
		} else if (!World.isLoading && World.isSaving && World.doesWorldExist && !World.isGeneratingWorld) {
			final int w = Main.WIDTH / 2 - (int) g.getFontMetrics().getStringBounds(SAVING, g).getWidth() / 2;
			final int h = Main.HEIGHT / 2 - (int) g.getFontMetrics().getStringBounds(SAVING, g).getHeight() / 2;
			
			g.drawString(SAVING, w, h);
		} else if (!World.isSaving && World.isLoading && !World.doesWorldExist && !World.isGeneratingWorld) {
			final int w = Main.WIDTH / 2 - (int) g.getFontMetrics().getStringBounds(LOADING, g).getWidth() / 2;
			final int h = Main.HEIGHT / 2 - (int) g.getFontMetrics().getStringBounds(LOADING, g).getHeight() / 2;
			
			g.drawString(LOADING, w, h);
		}
	}
}
