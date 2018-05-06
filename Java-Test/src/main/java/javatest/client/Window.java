package main.java.javatest.client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import main.java.javatest.Main;

public class Window {

	public Window(int width, int height, String title, Main game) {
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		
		Container pane = frame.getContentPane();
		pane.setBackground(Color.BLACK);
		
		frame.setContentPane(pane);
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(game);
		
		game.start();
	}
}
