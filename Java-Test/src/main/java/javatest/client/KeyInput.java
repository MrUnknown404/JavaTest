package main.java.javatest.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import main.java.javatest.Main;
import main.java.javatest.commands.CommandException;
import main.java.javatest.entity.EntityItem;
import main.java.javatest.items.ItemStack;

public class KeyInput extends KeyAdapter {

	public static boolean[] keyDown = new boolean[4];
	private static final double MOVE_SPEED = 3;
	
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
			try {
				Main.getCommandConsole().finishCommand();
			} catch (CommandException e1) {
				e1.printStackTrace();
			}
		} else if (key == KeyEvent.VK_SLASH && !Main.getCommandConsole().isConsoleOpen) {
			Main.getCommandConsole().isConsoleOpen = true;
			Main.getCommandConsole().addKey(e.getKeyChar());
		}
		
		if (!Main.getCommandConsole().isConsoleOpen) {
			if (Main.getWorldHandler().getWorld().getPlayer() != null) {
				if (key == KeyEvent.VK_W && Main.getWorldHandler().getWorld().getPlayer().isGrounded() && !keyDown[0]) {
					Main.getWorldHandler().getWorld().getPlayer().setJumpY(-MOVE_SPEED * (MOVE_SPEED / 1.25));
					keyDown[0] = true;
				}
				
				if (key == KeyEvent.VK_A) {
					Main.getWorldHandler().getWorld().getPlayer().setMoveDirX(-MOVE_SPEED);
					keyDown[1] = true;
				} else if (key == KeyEvent.VK_D) {
					Main.getWorldHandler().getWorld().getPlayer().setMoveDirX(MOVE_SPEED);
					keyDown[2] = true;
				}
				
				if (key == KeyEvent.VK_CONTROL) {
					keyDown[3] = true;
				}
				
				if (key == KeyEvent.VK_E) {
					if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getIsInventoryOpen()) {
						EntityItem item = null;
						if (Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse != null) {
							if (MouseInput.vec.x > Main.WIDTH_DEF / 2) {
								item = new EntityItem(Main.getWorldHandler().getWorld().getPlayer().getPositionX() + Main.getWorldHandler().getWorld().getPlayer().getWidth(), Main.getWorldHandler().getWorld().getPlayer().getPositionY() + (Main.getWorldHandler().getWorld().getPlayer().getHeight() / 6), Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse);
								Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse = null;
								item.addVelocityX(5);
							} else {
								item = new EntityItem(Main.getWorldHandler().getWorld().getPlayer().getPositionX() - 12, Main.getWorldHandler().getWorld().getPlayer().getPositionY() + (Main.getWorldHandler().getWorld().getPlayer().getHeight() / 6), Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse);
								Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse = null;
								item.addVelocityX(-5);
							}
							Main.getWorldHandler().getWorld().addObjectAll(item);
						}
						
						Main.getWorldHandler().getWorld().getPlayer().getInventory().setIsInventoryOpen(false) ;
					} else if (!Main.getWorldHandler().getWorld().getPlayer().getInventory().getIsInventoryOpen()) {
						Main.getWorldHandler().getWorld().getPlayer().getInventory().setIsInventoryOpen(true) ;
					}
				}
				
				if (key == KeyEvent.VK_Q) {
					EntityItem item = null;
					if (!Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot()).equals(ItemStack.EMPTY) && Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse == null) {
						if (MouseInput.vec.x > Main.WIDTH_DEF / 2) {
							item = new EntityItem(Main.getWorldHandler().getWorld().getPlayer().getPositionX() + Main.getWorldHandler().getWorld().getPlayer().getWidth(), Main.getWorldHandler().getWorld().getPlayer().getPositionY() + (Main.getWorldHandler().getWorld().getPlayer().getHeight() / 6), new ItemStack(1 , Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot()).getItem()));
							item.addVelocityX(5);
						} else {
							item = new EntityItem(Main.getWorldHandler().getWorld().getPlayer().getPositionX() - 12, Main.getWorldHandler().getWorld().getPlayer().getPositionY() + (Main.getWorldHandler().getWorld().getPlayer().getHeight() / 6), new ItemStack(1 , Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot()).getItem()));
							item.addVelocityX(-5);
						}
						Main.getWorldHandler().getWorld().getPlayer().getInventory().getItems().get(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot()).decreaseCount();
					} else if (Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse != null) {
						if (MouseInput.vec.x > Main.WIDTH_DEF / 2) {
							item = new EntityItem(Main.getWorldHandler().getWorld().getPlayer().getPositionX() + Main.getWorldHandler().getWorld().getPlayer().getWidth(), Main.getWorldHandler().getWorld().getPlayer().getPositionY() + (Main.getWorldHandler().getWorld().getPlayer().getHeight() / 6), Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse);
							item.addVelocityX(5);
						} else {
							item = new EntityItem(Main.getWorldHandler().getWorld().getPlayer().getPositionX() - 12, Main.getWorldHandler().getWorld().getPlayer().getPositionY() + (Main.getWorldHandler().getWorld().getPlayer().getHeight() / 6), Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse);
							item.addVelocityX(-5);
						}
						Main.getWorldHandler().getWorld().getPlayer().getInventory().itemInMouse = null;
					}
					if (item != null) {
						Main.getWorldHandler().getWorld().addObjectAll(item);
					}
				}
				
				if (key == KeyEvent.VK_0) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(9);
				} else if (key == KeyEvent.VK_1) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(0);
				} else if (key == KeyEvent.VK_2) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(1);
				} else if (key == KeyEvent.VK_3) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(2);
				} else if (key == KeyEvent.VK_4) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(3);
				} else if (key == KeyEvent.VK_5) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(4);
				} else if (key == KeyEvent.VK_6) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(5);
				} else if (key == KeyEvent.VK_7) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(6);
				} else if (key == KeyEvent.VK_8) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(7);
				} else if (key == KeyEvent.VK_9) {
					Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(8);
				}
				
				if (key == KeyEvent.VK_EQUALS) {
					if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() != Main.getWorldHandler().getWorld().getPlayer().getInventory().getSlotsX() - 1) {
						Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() + 1);
					} else {
						Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(0);
					}
				} else if (key == KeyEvent.VK_MINUS) {
					if (Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() != 0) {
						Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(Main.getWorldHandler().getWorld().getPlayer().getInventory().getSelectedSlot() - 1);
					} else {
						Main.getWorldHandler().getWorld().getPlayer().getInventory().setSelectedSlot(9);
					}
				}
			}
			
			if (key == KeyEvent.VK_F1 && !Main.getWorldHandler().doesWorldExist) {
				Main.getWorldHandler().generateWorld(200, 100, new Random().nextInt());
			} else if (key == KeyEvent.VK_F2 && Main.getWorldHandler().doesWorldExist) {
				Main.getWorldHandler().saveWorld("world1");
			} else if (key == KeyEvent.VK_F3 && !Main.getWorldHandler().doesWorldExist) {
				Main.getWorldHandler().loadWorld("world1");
			} else if (key == KeyEvent.VK_F4 && Main.getWorldHandler().doesWorldExist) {
				Main.getWorldHandler().clearWorld();
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
		
		if (Main.getWorldHandler().getWorld().getPlayer() != null) {
			if (!keyDown[1] && !keyDown[2]) {
				Main.getWorldHandler().getWorld().getPlayer().setMoveDirX(0);
			}
			
			if (key == KeyEvent.VK_CONTROL) {
				keyDown[3] = false;
			}
		}
	}
}
