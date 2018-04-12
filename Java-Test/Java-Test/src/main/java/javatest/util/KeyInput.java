package main.java.javatest.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.handlers.ObjectHandler;

public class KeyInput extends KeyAdapter {

	private ObjectHandler handler;
	
	public KeyInput(ObjectHandler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tObj = handler.object.get(i);
			
			if (tObj instanceof EntityPlayer) {
				EntityPlayer obj = (EntityPlayer) tObj;
				if (key == KeyEvent.VK_W) {
					obj.setMoveDirY(-1);
				} else if (key == KeyEvent.VK_S) {
					obj.setMoveDirY(1);
				}
				
				if (key == KeyEvent.VK_A) {
					obj.setMoveDirX(-1);
				} else if (key == KeyEvent.VK_D) {
					obj.setMoveDirX(1);
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tObj = handler.object.get(i);
			
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
