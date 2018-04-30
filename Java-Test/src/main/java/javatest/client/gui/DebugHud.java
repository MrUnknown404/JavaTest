package main.java.javatest.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.java.javatest.blocks.Block;
import main.java.javatest.entity.Entity;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2d;
import main.java.javatest.util.math.Vec2i;
import main.java.javatest.world.World;

public class DebugHud extends Canvas {

	private static final long serialVersionUID = -4835862860408839539L;
	private final Font FONT = new Font("Font", Font.BOLD, 16);
	
	private static String mouseString = "";
	private static String posString = "";
	private static String blockPosString = "";
	private static int blockCountAll;
	private static int blockCountActive;
	private static double gravityY;
	
	public static void getInfo() {
		for (int i = 0; i < World.getActiveEntities().size(); i++) {
			GameObject o = World.getActiveEntities().get(i);
			
			if (o instanceof EntityPlayer) {
				Vec2d pos = new Vec2d(o.getPositionX(), o.getPositionY() + o.getHeight());
				posString = pos.toStringInt();
				blockPosString = new Vec2i(MathHelper.floor(o.getPositionX() / Block.getBlockSize()), MathHelper.floor((o.getPositionY() + o.getHeight()) / Block.getBlockSize())).toString();
				
				gravityY = MathHelper.roundTo(((Entity) o).getGravityY(), 3);
			}
		}
		blockCountAll = World.getAllBlocks().size();
		blockCountActive = World.getActiveBlocks().size();
	}
	
	public static void setMouseVec(Vec2i vec) {
		mouseString = vec.toString();
	}
	
	public void drawText(Graphics g, String fps, int x, int y) {
		g.setColor(Color.GREEN);
		g.setFont(FONT);
		g.drawString(fps, x, y);
		
		g.drawString("Mouse pos: " + mouseString, x, y += 30);
		g.drawString("Blocks active: " + blockCountActive, x, y += 15);
		g.drawString("Blocks all: " + blockCountAll, x, y += 15);
		
		g.drawString("Player pos: " + posString, x, y += 30);
		g.drawString("Player block pos: " + blockPosString, x, y += 15);
		g.drawString("Player Gravity: " + gravityY, x, y += 15);
	}
}
