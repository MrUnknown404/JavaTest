package main.java.javatest.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.handlers.ObjectHandler;
import main.java.javatest.util.math.Vec2d;

public class DebugHud extends Canvas {

	private static final long serialVersionUID = -4835862860408839539L;
	private static final Font FONT = new Font("Font", Font.BOLD, 16);
	
	private static Vec2d pos;
	private static String posString = "";
	
	public static void getPlayer() {
		for (int i = 0; i < ObjectHandler.objects.size(); i++) {
			GameObject o = ObjectHandler.objects.get(i);
			if (o instanceof EntityPlayer) {
				pos = o.getPosition();
				posString = pos.toStringInt();
			}
		}
	}
	
	public static void drawText(Graphics g, String string, int x, int y) {
		g.setColor(Color.GREEN);
		g.setFont(FONT);
		g.drawString(string, x, y);
		g.drawString("POS: " + posString, x, y += 16);
	}
}
