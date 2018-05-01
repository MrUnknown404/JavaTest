package main.java.javatest.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import main.java.javatest.world.World;

public class KeyInput extends KeyAdapter {

	private static final double MOVE_SPEED = 3;
	public static boolean[] keyDown = new boolean[4];
	
	public KeyInput() {
		for (int i = 0; i < keyDown.length; i++) {
			keyDown[i] = false;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (World.getPlayer() != null) {
			if (key == KeyEvent.VK_W && World.getPlayer().isGrounded() && !keyDown[0]) {
				World.getPlayer().setJumpY(-MOVE_SPEED * (MOVE_SPEED / 1.25));
				keyDown[0] = true;
			}
			
			if (key == KeyEvent.VK_A) {
				World.getPlayer().setMoveDirX(-MOVE_SPEED);
				keyDown[1] = true;
			} else if (key == KeyEvent.VK_D) {
				World.getPlayer().setMoveDirX(MOVE_SPEED);
				keyDown[2] = true;
			}
			
			if (key == KeyEvent.VK_CONTROL) {
				keyDown[3] = true;
			}
		}
		
		if (key == KeyEvent.VK_1 && !World.doesWorldExist) {
			World.generateWorld(200, 100, new Random().nextInt());
		} else if (key == KeyEvent.VK_3 && World.doesWorldExist) {
			World.clearWorld();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W) {
			keyDown[0] = false;
		}
		
		if (key == KeyEvent.VK_A) {
			keyDown[1] = false;
		} else if (key == KeyEvent.VK_D) {
			keyDown[2] = false;
		}
		
		if (World.getPlayer() != null) {
			if (!keyDown[1] && !keyDown[2]) {
				World.getPlayer().setMoveDirX(0);
			}
			
			if (key == KeyEvent.VK_CONTROL) {
				keyDown[3] = false;
			}
		}
	}
}
