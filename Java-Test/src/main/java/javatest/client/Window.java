package main.java.javatest.client;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import main.java.javatest.Main;

public class Window extends Canvas {

	private static final long serialVersionUID = 8102809856257657166L;

	public Window(int width, int height, String title, Main game) {
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		
		game.start();
	}
}
