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
			
			if (key == KeyEvent.VK_E) {
				if (World.getPlayer().getInventory().getIsInventoryOpen()) {
					World.getPlayer().getInventory().setIsInventoryOpen(false) ;
				} else if (!World.getPlayer().getInventory().getIsInventoryOpen()) {
					World.getPlayer().getInventory().setIsInventoryOpen(true) ;
				}
			}
			
			if (key == KeyEvent.VK_0) {
				World.getPlayer().getInventory().setSelectedSlot(9);
			} else if (key == KeyEvent.VK_1) {
				World.getPlayer().getInventory().setSelectedSlot(0);
			} else if (key == KeyEvent.VK_2) {
				World.getPlayer().getInventory().setSelectedSlot(1);
			} else if (key == KeyEvent.VK_3) {
				World.getPlayer().getInventory().setSelectedSlot(2);
			} else if (key == KeyEvent.VK_4) {
				World.getPlayer().getInventory().setSelectedSlot(3);
			} else if (key == KeyEvent.VK_5) {
				World.getPlayer().getInventory().setSelectedSlot(4);
			} else if (key == KeyEvent.VK_6) {
				World.getPlayer().getInventory().setSelectedSlot(5);
			} else if (key == KeyEvent.VK_7) {
				World.getPlayer().getInventory().setSelectedSlot(6);
			} else if (key == KeyEvent.VK_8) {
				World.getPlayer().getInventory().setSelectedSlot(7);
			} else if (key == KeyEvent.VK_9) {
				World.getPlayer().getInventory().setSelectedSlot(8);
			}
			
			if (key == KeyEvent.VK_EQUALS) {
				if (World.getPlayer().getInventory().getSelectedSlot() != World.getPlayer().getInventory().getSlotsX() - 1) {
					World.getPlayer().getInventory().setSelectedSlot(World.getPlayer().getInventory().getSelectedSlot() + 1);
				} else {
					World.getPlayer().getInventory().setSelectedSlot(0);
				}
			} else if (key == KeyEvent.VK_MINUS) {
				if (World.getPlayer().getInventory().getSelectedSlot() != 0) {
					World.getPlayer().getInventory().setSelectedSlot(World.getPlayer().getInventory().getSelectedSlot() - 1);
				} else {
					World.getPlayer().getInventory().setSelectedSlot(9);
				}
			}
		}
		
		if (key == KeyEvent.VK_F1 && !World.doesWorldExist) {
			World.generateWorld(200, 100, new Random().nextInt());
		} else if (key == KeyEvent.VK_F2 && World.doesWorldExist) {
			World.saveWorld("world1");
		} else if (key == KeyEvent.VK_F3 && !World.doesWorldExist) {
			World.loadWorld("world1");
		} else if (key == KeyEvent.VK_F4 && World.doesWorldExist) {
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
