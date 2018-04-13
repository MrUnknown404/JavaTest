package main.java.javatest.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.handlers.ObjectHandler;

public class KeyInput extends KeyAdapter {

	private static final double MOVE_SPEED = 2.5;

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < ObjectHandler.object.size(); i++) {
			GameObject tObj = ObjectHandler.object.get(i);
			
			if (tObj instanceof EntityPlayer) {
				EntityPlayer obj = (EntityPlayer) tObj;
				
				if (key == KeyEvent.VK_K) {
					obj.killEntity();
				}
				
				if (key == KeyEvent.VK_W) {
					obj.setMoveDirY(-MOVE_SPEED);
				} else if (key == KeyEvent.VK_S) {
					obj.setMoveDirY(MOVE_SPEED);
				}
				
				if (key == KeyEvent.VK_A) {
					obj.setMoveDirX(-MOVE_SPEED);
				} else if (key == KeyEvent.VK_D) {
					obj.setMoveDirX(MOVE_SPEED);
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		for (int i = 0; i < ObjectHandler.object.size(); i++) {
			GameObject tObj = ObjectHandler.object.get(i);
			
			if (tObj instanceof EntityPlayer) {
				EntityPlayer obj = (EntityPlayer) tObj;
				if (key == KeyEvent.VK_W) {
					obj.setMoveDirY(0);
				} else if (key == KeyEvent.VK_S) {
					obj.setMoveDirY(0);
				}
				
				if (key == KeyEvent.VK_A) {
					obj.setMoveDirX(0);
				} else if (key == KeyEvent.VK_D) {
					obj.setMoveDirX(0);
				}
			}
		}
	}
}
