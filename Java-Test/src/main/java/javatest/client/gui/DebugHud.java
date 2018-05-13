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
	private static String gravityY;
	
	private static final String PRESS1 = "Press F1 to start!";
	
	public void getInfo() {
		if (Main.getWorldHandler().getPlayer() != null) {
			Vec2d pos = new Vec2d(Main.getWorldHandler().getPlayer().getPositionX(), Main.getWorldHandler().getPlayer().getPositionY() + Main.getWorldHandler().getPlayer().getHeight());
			posString = pos.toStringInt();
			blockPosString = new Vec2i(MathHelper.floor(Main.getWorldHandler().getPlayer().getPositionX() / Block.getBlockSize()), MathHelper.floor((Main.getWorldHandler().getPlayer().getPositionY() + Main.getWorldHandler().getPlayer().getHeight()) / Block.getBlockSize())).toString();
			
			gravityY = String.valueOf(MathHelper.roundTo((Main.getWorldHandler().getPlayer()).getGravityY(), 3));
		}
		blockCountAll = String.valueOf(Main.getWorldHandler().getAllBlocks().size());
		blockCountActive = String.valueOf(Main.getWorldHandler().getActiveBlocks().size());
		entityCountAll = String.valueOf(Main.getWorldHandler().getAllEntities().size());
		entityCountActive = String.valueOf(Main.getWorldHandler().getActiveEntities().size());
	}
	
	public static void setMouseVec(Vec2i vec) {
		mouseString = vec.toString();
		if (Main.getWorldHandler() != null && Main.getWorldHandler().getPlayer() != null) {
			mouseWorldString = new Vec2i(MouseInput.vec.x - Main.getCamera().getPositionX(), MouseInput.vec.y - Main.getCamera().getPositionY()).toString();
			mouseWorldBlockString = new Vec2i((MouseInput.vec.x - Main.getCamera().getPositionX()) / Block.getBlockSize(), (MouseInput.vec.y - Main.getCamera().getPositionY()) / Block.getBlockSize()).toString();
		}
	}
	
	public void drawText(Graphics g, String fps) {
		int y = 15;
		g.setColor(Color.GREEN);
		g.setFont(FONT);
		if (Main.getWorldHandler().getPlayer() != null) {
			if (Main.getWorldHandler().getPlayer().getInventory().getIsInventoryOpen()) {
				g.drawString(fps, 1, y += 134);
			} else {
				g.drawString(fps, 1, y += 46);
			}
		} else {
			g.drawString(fps, 1, y);
		}
		
		if (!Main.getCommandConsole().isConsoleOpen && !Main.getWorldHandler().getWasCreated()) {
			final int w = Main.WIDTH_DEF / 2 - (int) g.getFontMetrics().getStringBounds(PRESS1, g).getWidth() / 2;
			final int h = Main.HEIGHT_DEF / 2 - (int) g.getFontMetrics().getStringBounds(PRESS1, g).getHeight() / 2;
			
			g.drawString(PRESS1, w, h);
		} else if (!Main.getCommandConsole().isConsoleOpen) {
			g.drawString("Mouse pos: " + mouseString, 1, y += 32);
			g.drawString("Mouse world pos: " + mouseWorldString, 1, y += 16);
			g.drawString("Mouse world block pos: " + mouseWorldBlockString, 1, y += 16);
			g.drawString("Blocks active: " + blockCountActive, 1, y += 16);
			g.drawString("Blocks all: " + blockCountAll, 1, y += 16);
			g.drawString("Entities active: " + entityCountActive, 1, y += 16);
			g.drawString("Entities all: " + entityCountAll, 1, y += 16);
			
			g.drawString("Player pos: " + posString, 1, y += 32);
			g.drawString("Player block pos: " + blockPosString, 1, y += 16);
			g.drawString("Player Gravity: " + gravityY, 1, y += 16);
		}
	}
}
