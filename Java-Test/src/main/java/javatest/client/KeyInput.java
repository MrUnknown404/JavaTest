package main.java.javatest.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.CreateLevel;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.handlers.ObjectHandler;

public class KeyInput extends KeyAdapter {

	private static final double MOVE_SPEED = 3;
	private boolean didPressJump = false;
	private EntityPlayer player;
	
	public KeyInput() {
		findPlayer();
	}
	
	private void findPlayer() {
		for (int i = 0; i < ObjectHandler.objects.size(); i++) {
			GameObject tObj = ObjectHandler.objects.get(i);
			
			if (tObj instanceof EntityPlayer) {
				player = (EntityPlayer) tObj;
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (player != null) {
			if (key == KeyEvent.VK_R) {
				LinkedList<GameObject> os = new LinkedList<GameObject>();
				
				for (GameObject o : ObjectHandler.objects) {
					os.add(o);
				}
				
				for (GameObject o : os) {
					ObjectHandler.removeObject(o);
				}
				
				CreateLevel.createLevel(JavaGameTest.BLOCK_WIDTH, JavaGameTest.BLOCK_HEIGHT, JavaGameTest.WIDTH, JavaGameTest.HEIGHT);
				findPlayer();
			}
			
			if (key == KeyEvent.VK_W && player.canJump && !didPressJump) {
				player.addVelocityY(-MOVE_SPEED * MOVE_SPEED);
				didPressJump = true;
			}
			
			if (key == KeyEvent.VK_A) {
				player.setMoveDirX(-MOVE_SPEED);
			} else if (key == KeyEvent.VK_D) {
				player.setMoveDirX(MOVE_SPEED);
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
