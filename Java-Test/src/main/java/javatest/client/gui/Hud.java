package main.java.javatest.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Hud extends Canvas {

	private static final long serialVersionUID = -4835862860408839539L;
	private static final Font FONT = new Font("Font", Font.BOLD, 16);
	
	public static void drawText(Graphics g, String string, int x, int y) {
		g.setColor(Color.GREEN);
		g.setFont(FONT);
		g.drawString(string, x, y);
	}
}
