package main.java.javatest.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.java.javatest.Main;

public class ConsoleHud extends Canvas {

	private static final long serialVersionUID = 6314844238245057139L;
	private static final Font FONT = new Font("Font", Font.BOLD, 16);
	
	private boolean tb = false;
	private int ti = 0;
	
	public void draw(Graphics g) {
		g.setFont(FONT);
		
		if (Main.getCommandConsole().isConsoleOpen) {
			g.setColor(new Color(0.2f, 0.2f, 0.2f, 0.5f));
			g.fillRect(0, 0, Main.WIDTH_DEF, ((Main.getCommandConsole().getMaxLines() + 2) * 16) - 10);
			g.setColor(Color.GREEN);
			if (tb) {
				g.drawString(">: " + Main.getCommandConsole().input + ":", 2, ((Main.getCommandConsole().getMaxLines() + 2) * 16) - 12);
			} else {
				g.drawString(">: " + Main.getCommandConsole().input, 2, ((Main.getCommandConsole().getMaxLines() + 2) * 16) - 12);
			}
			
			if (!Main.getCommandConsole().lines.isEmpty()) {
				for (int i = 1; i < Main.getCommandConsole().lines.size(); i++) {
					if (Main.getCommandConsole().lines.get(i).startsWith("*")) {
						g.setColor(Color.RED);
					} else {
						g.setColor(Color.GREEN);
					}
					g.drawString("<: " + Main.getCommandConsole().lines.get(i), 2, ((Main.getCommandConsole().getMaxLines() + 1) * 16) - (i * 16));
				}
			}
		}
		
	}
	
	public void tick() {
		if (ti == 0) {
			ti = 50;
			if (tb) {
				tb = false;
			} else {
				tb = true;
			}
		}
		ti--;
	}
}
