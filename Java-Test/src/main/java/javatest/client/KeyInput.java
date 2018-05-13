package main.java.javatest.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import main.java.javatest.Main;
import main.java.javatest.entity.EntityItem;
import main.java.javatest.items.ItemStack;

public class KeyInput extends KeyAdapter {

	public static boolean[] keyDown = new boolean[4];
	
	public KeyInput() {
		for (int i = 0; i < keyDown.length; i++) {
			keyDown[i] = false;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (Main.getCommandConsole().isConsoleOpen) {
			if (key == KeyEvent.VK_ESCAPE) {
				Main.getCommandConsole().clearInput();
				Main.getCommandConsole().isConsoleOpen = false;
			} else if (key != KeyEvent.VK_BACK_SPACE) {
				if (e.getKeyChar() != '\uFFFF' && e.getKeyCode() != KeyEvent.VK_DELETE) {
					Main.getCommandConsole().addKey(e.getKeyChar());
				}
			} else {
				if (!Main.getCommandConsole().input.isEmpty()) {
					Main.getCommandConsole().removeKey();
				}
			}
		}
		
		if (key == KeyEvent.VK_BACK_QUOTE && !Main.getCommandConsole().isConsoleOpen) {
			Main.getCommandConsole().isConsoleOpen = true;
		} else if (key == KeyEvent.VK_ENTER && Main.getCommandConsole().isConsoleOpen) {
			Main.getCommandConsole().finishCommand();
		} else if (key == KeyEvent.VK_SLASH && !Main.getCommandConsole().isConsoleOpen) {
			Main.getCommandConsole().isConsoleOpen = true;
			Main.getCommandConsole().addKey(e.getKeyChar());
		}
		
		if (!Main.getCommandConsole().isConsoleOpen) {
			if (Main.getWorldHandler().getPlayer() != null) {
				if (key == KeyEvent.VK_W && Main.getWorldHandler().getPlayer().isGrounded() && !keyDown[0]) {
					Main.getWorldHandler().getPlayer().setJumpY(-Main.getWorldHandler().getPlayer().getSpeed() * (Main.getWorldHandler().getPlayer().getSpeed() / 1.25));
					keyDown[0] = true;
				}
				
				if (key == KeyEvent.VK_A) {
					Main.getWorldHandler().getPlayer().setMoveDirX(-Main.getWorldHandler().getPlayer().getSpeed());
					keyDown[1] = true;
				} else if (key == KeyEvent.VK_D) {
					Main.getWorldHandler().getPlayer().setMoveDirX(Main.getWorldHandler().getPlayer().getSpeed());
					keyDown[2] = true;
				}
				
				if (key == KeyEvent.VK_CONTROL) {
					keyDown[3] = true;
				}
				
				if (key == KeyEvent.VK_E) {
					if (Main.getWorldHandler().getPlayer().getInventory().getIsInventoryOpen()) {
						EntityItem item = null;
						if (Main.getWorldHandler().getPlayer().getInventory().getItemInMouse() != null) {
							if (Main.getWorldHandler().getPlayer().direction == 1) {
								item = new EntityItem(Main.getWorldHandler().getPlayer().getPositionX() + Main.getWorldHandler().getPlayer().getWidth(), Main.getWorldHandler().getPlayer().getPositionY() + (Main.getWorldHandler().getPlayer().getHeight() / 6), Main.getWorldHandler().getPlayer().getInventory().getItemInMouse());
								Main.getWorldHandler().getPlayer().getInventory().setItemInMouse(null);
								item.addVelocityX(5);
								item.addVelocityY(-2);
							} else if (Main.getWorldHandler().getPlayer().direction == -1) {
								item = new EntityItem(Main.getWorldHandler().getPlayer().getPositionX() - 12, Main.getWorldHandler().getPlayer().getPositionY() + (Main.getWorldHandler().getPlayer().getHeight() / 6), Main.getWorldHandler().getPlayer().getInventory().getItemInMouse());
								Main.getWorldHandler().getPlayer().getInventory().setItemInMouse(null);
								item.addVelocityX(-5);
								item.addVelocityY(-2);
							}
							Main.getWorldHandler().addObjectAll(item);
						}
						
						Main.getWorldHandler().getPlayer().getInventory().setIsInventoryOpen(false) ;
					} else if (!Main.getWorldHandler().getPlayer().getInventory().getIsInventoryOpen()) {
						Main.getWorldHandler().getPlayer().getInventory().setIsInventoryOpen(true) ;
					}
				}
				
				if (key == KeyEvent.VK_Q) {
					EntityItem item = null;
					if (!Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().equals(ItemStack.EMPTY) && Main.getWorldHandler().getPlayer().getInventory().getItemInMouse() == null) {
						if (Main.getWorldHandler().getPlayer().direction == 1) {
							item = new EntityItem(Main.getWorldHandler().getPlayer().getPositionX() + Main.getWorldHandler().getPlayer().getWidth(), Main.getWorldHandler().getPlayer().getPositionY() + (Main.getWorldHandler().getPlayer().getHeight() / 6), new ItemStack(1 , Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem()));
							item.addVelocityX(5);
							item.addVelocityY(-2);
						} else if (Main.getWorldHandler().getPlayer().direction == -1) {
							item = new EntityItem(Main.getWorldHandler().getPlayer().getPositionX() - 12, Main.getWorldHandler().getPlayer().getPositionY() + (Main.getWorldHandler().getPlayer().getHeight() / 6), new ItemStack(1 , Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem()));
							item.addVelocityX(-5);
							item.addVelocityY(-2);
						}
						Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().decreaseCount();
					} else if (Main.getWorldHandler().getPlayer().getInventory().getItemInMouse() != null) {
						if (Main.getWorldHandler().getPlayer().direction == 1) {
							item = new EntityItem(Main.getWorldHandler().getPlayer().getPositionX() + Main.getWorldHandler().getPlayer().getWidth(), Main.getWorldHandler().getPlayer().getPositionY() + (Main.getWorldHandler().getPlayer().getHeight() / 6), Main.getWorldHandler().getPlayer().getInventory().getItemInMouse());
							item.addVelocityX(5);
							item.addVelocityY(-2);
						} else if (Main.getWorldHandler().getPlayer().direction == -1) {
							item = new EntityItem(Main.getWorldHandler().getPlayer().getPositionX() - 12, Main.getWorldHandler().getPlayer().getPositionY() + (Main.getWorldHandler().getPlayer().getHeight() / 6), Main.getWorldHandler().getPlayer().getInventory().getItemInMouse());
							item.addVelocityX(-5);
							item.addVelocityY(-2);
						}
						Main.getWorldHandler().getPlayer().getInventory().setItemInMouse(null);
					}
					if (item != null) {
						Main.getWorldHandler().addObjectAll(item);
					}
				}
				
				if (key == KeyEvent.VK_0) {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(9);
				} else if (key == KeyEvent.VK_1) {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(0);
				} else if (key == KeyEvent.VK_2) {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(1);
				} else if (key == KeyEvent.VK_3) {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(2);
				} else if (key == KeyEvent.VK_4) {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(3);
				} else if (key == KeyEvent.VK_5) {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(4);
				} else if (key == KeyEvent.VK_6) {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(5);
				} else if (key == KeyEvent.VK_7) {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(6);
				} else if (key == KeyEvent.VK_8) {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(7);
				} else if (key == KeyEvent.VK_9) {
					Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(8);
				}
				
				if (key == KeyEvent.VK_EQUALS) {
					if (Main.getWorldHandler().getPlayer().getInventory().getSelectedSlot() != Main.getWorldHandler().getPlayer().getInventory().getSlotsX() - 1) {
						Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(Main.getWorldHandler().getPlayer().getInventory().getSelectedSlot() + 1);
					} else {
						Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(0);
					}
				} else if (key == KeyEvent.VK_MINUS) {
					if (Main.getWorldHandler().getPlayer().getInventory().getSelectedSlot() != 0) {
						Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(Main.getWorldHandler().getPlayer().getInventory().getSelectedSlot() - 1);
					} else {
						Main.getWorldHandler().getPlayer().getInventory().setSelectedSlot(9);
					}
				}
			}
			
			if (key == KeyEvent.VK_F1) {
				Main.getWorldHandler().generateWorld(100, 50, new Random().nextInt());
			}
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
		
		if (Main.getWorldHandler().getPlayer() != null) {
			if (!keyDown[1] && !keyDown[2]) {
				Main.getWorldHandler().getPlayer().setMoveDirX(0);
			}
			
			if (key == KeyEvent.VK_CONTROL) {
				keyDown[3] = false;
			}
		}
	}
}
