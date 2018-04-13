package main.java.javatest.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.handlers.ObjectHandler;

public class KeyInput extends KeyAdapter {

	private static final double MOVE_SPEED = 3;
	private boolean didPressJump = false;

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < ObjectHandler.objects.size(); i++) {
			GameObject tObj = ObjectHandler.objects.get(i);
			
			if (tObj instanceof EntityPlayer) {
				EntityPlayer obj = (EntityPlayer) tObj;
				
				if (key == KeyEvent.VK_K) {
					obj.killEntity();
				}
				
				if (key == KeyEvent.VK_W && obj.canJump && !didPressJump) {
					obj.addVelocityY(-MOVE_SPEED * 3);
					didPressJump = true;
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
		
		for (int i = 0; i < ObjectHandler.objects.size(); i++) {
			GameObject tObj = ObjectHandler.objects.get(i);
			
			if (tObj instanceof EntityPlayer) {
				EntityPlayer obj = (EntityPlayer) tObj;
				if (key == KeyEvent.VK_W) {
					didPressJump = false;
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
