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

public class DebugHud extends Canvas {

	private static final long serialVersionUID = -4835862860408839539L;
	private static final Font FONT = new Font("Font", Font.BOLD, 16);
	
	private static String mouseString = "(0, 0)";
	private static String mouseWorldString = "(0, 0)";
	private static String mouseWorldBlockString = "(0, 0)";
	private static String posString = "(0, 0)";
	private static String blockPosString = "(0, 0)";
	private static String blockCountAll;
	private static String blockCountActive;
	private static String entityCountAll;
	private static String entityCountActive;
	private static String entityItemCountAll;
	private static String entityItemCountActive;
	private static String gravityY;
	private static String generating = "";
	private static String loading = "";
	private static String saving = "";
	
	private static final String PRESS1 = "Press F1 to generate a new world!";
	private static final String PRESS2 = "Press F2 to save the world!";
	private static final String PRESS3 = "Press F3 to load a world!";
	private static final String PRESS4 = "Press F4 to reset the world!";
	
	public void getInfo() {
		if (Main.getWorldHandler().getWorld().getPlayer() != null) {
			Vec2d pos = new Vec2d(Main.getWorldHandler().getWorld().getPlayer().getPositionX(), Main.getWorldHandler().getWorld().getPlayer().getPositionY() + Main.getWorldHandler().getWorld().getPlayer().getHeight());
			posString = pos.toStringInt();
			blockPosString = new Vec2i(MathHelper.floor(Main.getWorldHandler().getWorld().getPlayer().getPositionX() / Block.getBlockSize()), MathHelper.floor((Main.getWorldHandler().getWorld().getPlayer().getPositionY() + Main.getWorldHandler().getWorld().getPlayer().getHeight()) / Block.getBlockSize())).toString();
			
			gravityY = String.valueOf(MathHelper.roundTo((Main.getWorldHandler().getWorld().getPlayer()).getGravityY(), 3));
		}
		generating = "Generating a new world... " + MathHelper.roundTo(Main.getWorldHandler().getAmountGenerated(), 1) + "%";
		loading = "Loading the world... " + MathHelper.roundTo(Main.getWorldHandler().getAmountLoaded(), 1) + "%";
		saving = "Saving the world... " + MathHelper.roundTo(Main.getWorldHandler().getAmountSaved(), 1) + "%";
		blockCountAll = String.valueOf(Main.getWorldHandler().getWorld().getAllBlocks().size());
		blockCountActive = String.valueOf(Main.getWorldHandler().getWorld().getActiveBlocks().size());
		entityCountAll = String.valueOf(Main.getWorldHandler().getWorld().getAllEntities().size());
		entityCountActive = String.valueOf(Main.getWorldHandler().getWorld().getActiveEntities().size());
		entityItemCountAll = String.valueOf(Main.getWorldHandler().getWorld().getAllItemEntities().size());
		entityItemCountActive = String.valueOf(Main.getWorldHandler().getWorld().getActiveItemEntities().size());
	}
	
	public static void setMouseVec(Vec2i vec) {
		mouseString = vec.toString();
		if (Main.getWorldHandler().getWorld().getPlayer() != null) {
			mouseWorldString = new Vec2i(MouseInput.vec.x - Main.getCamera().getPositionX(), MouseInput.vec.y - Main.getCamera().getPositionY()).toString();
			mouseWorldBlockString = new Vec2i((MouseInput.vec.x - Main.getCamera().getPositionX()) / Block.getBlockSize(), (MouseInput.vec.y - Main.getCamera().getPositionY()) / Block.getBlockSize()).toString();
		}
	}
	
	public void drawText(Graphics g, String fps) {
		int y = 15;
		g.setColor(Color.GREEN);
		g.setFont(FONT);
		if (Main.getWorldHandler().getWorld().getPlayer() != null) {
			if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getIsInventoryOpen()) {
				g.drawString(fps, 1, y += 134);
			} else {
				g.drawString(fps, 1, y += 46);
			}
		} else {
			g.drawString(fps, 1, y);
		}
		
		if (!Main.getWorldHandler().doesWorldExist && !Main.getWorldHandler().isGeneratingWorld && !Main.getWorldHandler().isSaving && !Main.getWorldHandler().isLoading) {
			final int w1 = Main.WIDTH_DEF / 2 - (int) g.getFontMetrics().getStringBounds(PRESS1, g).getWidth() / 2;
			final int h1 = Main.HEIGHT_DEF / 2 - (int) g.getFontMetrics().getStringBounds(PRESS1, g).getHeight() / 2;
			final int w2 = Main.WIDTH_DEF / 2 - (int) g.getFontMetrics().getStringBounds(PRESS3, g).getWidth() / 2;
			final int h2 = Main.HEIGHT_DEF / 2 - (int) g.getFontMetrics().getStringBounds(PRESS3, g).getHeight() / 2;
			
			g.drawString(PRESS1, w1, h1);
			g.drawString(PRESS3, w2, h2 + 16);
		} else if (Main.getWorldHandler().isGeneratingWorld && !Main.getWorldHandler().isSaving && !Main.getWorldHandler().isLoading) {
			final int w = Main.WIDTH_DEF / 2 - (int) g.getFontMetrics().getStringBounds(generating, g).getWidth() / 2;
			final int h = Main.HEIGHT_DEF / 2 - (int) g.getFontMetrics().getStringBounds(generating, g).getHeight() / 2;
			
			g.drawString(generating, w, h);
		} else if (Main.getWorldHandler().doesWorldExist && !Main.getWorldHandler().isGeneratingWorld && !Main.getWorldHandler().isSaving && !Main.getWorldHandler().isLoading) {
			final int w1 = Main.WIDTH_DEF / 2 - (int) g.getFontMetrics().getStringBounds(PRESS4, g).getWidth() / 2;
			final int w2 = Main.WIDTH_DEF / 2 - (int) g.getFontMetrics().getStringBounds(PRESS2, g).getWidth() / 2;
			
			g.drawString(PRESS4, w1, Main.HEIGHT_DEF - 42);
			g.drawString(PRESS2, w2, Main.HEIGHT_DEF - 58);
			
			g.drawString("Mouse pos: " + mouseString, 1, y += 32);
			g.drawString("Mouse world pos: " + mouseWorldString, 1, y += 16);
			g.drawString("Mouse world block pos: " + mouseWorldBlockString, 1, y += 16);
			g.drawString("Blocks active: " + blockCountActive, 1, y += 16);
			g.drawString("Blocks all: " + blockCountAll, 1, y += 16);
			g.drawString("Entities active: " + entityCountActive, 1, y += 16);
			g.drawString("Entities all: " + entityCountAll, 1, y += 16);
			g.drawString("Item Entities active: " + entityItemCountActive, 1, y += 16);
			g.drawString("Item Entities all: " + entityItemCountAll, 1, y += 16);
			
			g.drawString("Player pos: " + posString, 1, y += 32);
			g.drawString("Player block pos: " + blockPosString, 1, y += 16);
			g.drawString("Player Gravity: " + gravityY, 1, y += 16);
		} else if (!Main.getWorldHandler().isLoading && Main.getWorldHandler().isSaving && Main.getWorldHandler().doesWorldExist && !Main.getWorldHandler().isGeneratingWorld) {
			final int w = Main.WIDTH_DEF / 2 - (int) g.getFontMetrics().getStringBounds(saving, g).getWidth() / 2;
			final int h = Main.HEIGHT_DEF / 2 - (int) g.getFontMetrics().getStringBounds(saving, g).getHeight() / 2;
			
			g.drawString(saving, w, h);
		} else if (!Main.getWorldHandler().isSaving && Main.getWorldHandler().isLoading && !Main.getWorldHandler().doesWorldExist && !Main.getWorldHandler().isGeneratingWorld) {
			final int w = Main.WIDTH_DEF / 2 - (int) g.getFontMetrics().getStringBounds(loading, g).getWidth() / 2;
			final int h = Main.HEIGHT_DEF / 2 - (int) g.getFontMetrics().getStringBounds(loading, g).getHeight() / 2;
			
			g.drawString(loading, w, h);
		}
	}
}
