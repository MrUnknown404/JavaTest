package main.java.javatest.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.CreateTestLevel;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.handlers.ObjectHandler;

public class KeyInput extends KeyAdapter {

	private static final double MOVE_SPEED = 3;
	private boolean didPressJump = false;
	private boolean[] keyDown = new boolean[3];
	private EntityPlayer player;
	
	public KeyInput() {
		findPlayer();
		
		for (int i = 0; i < keyDown.length; i++) {
			keyDown[i] = false;
		}
	}
	
	private void findPlayer() {
		for (GameObject obj : ObjectHandler.objects) {
			
			if (obj instanceof EntityPlayer) {
				player = (EntityPlayer) obj;
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (player != null) {
			if (key == KeyEvent.VK_R) {
				ObjectHandler.clearObjects();
				
				CreateTestLevel.createLevel(JavaGameTest.BLOCK_WIDTH, JavaGameTest.BLOCK_HEIGHT, JavaGameTest.WIDTH, JavaGameTest.HEIGHT);
				findPlayer();
			}
			
			if (key == KeyEvent.VK_W && player.canJump && !didPressJump) {
				player.addVelocityY(-MOVE_SPEED * MOVE_SPEED);
				didPressJump = true;
				keyDown[0] = true;
			}
			
			if (key == KeyEvent.VK_A) {
				player.setMoveDirX(-MOVE_SPEED);
				keyDown[1] = true;
			} else if (key == KeyEvent.VK_D) {
				player.setMoveDirX(MOVE_SPEED);
				keyDown[2] = true;
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
					keyDown[0] = false;
					didPressJump = false;
				}
				
				if (key == KeyEvent.VK_A) {
					keyDown[1] = false;
				} else if (key == KeyEvent.VK_D) {
					keyDown[2] = false;
				}
				
				if (!keyDown[1] && !keyDown[2]) {
					obj.setMoveDirX(0);
				}
			}
		}
	}
}
