package main.java.javatest.entity.entityliving;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.java.javatest.Main;
import main.java.javatest.client.MouseInput;
import main.java.javatest.entity.Entity;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.inventory.PlayerInventory;
import main.java.javatest.items.Item;
import main.java.javatest.items.ItemStack;
import main.java.javatest.util.math.MathHelper;

public class EntityPlayer extends EntityLiving {
	
	private PlayerInventory inventory = new PlayerInventory();
	private final double speed = 3;
	private int tiMax = 50, ti = tiMax;
	
	private Timer atkT = new Timer(1000 / 600, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent ee) {
			getInventory().getSelectedItem().getItem().addSwingAmount();
			if (getInventory().getSelectedItem().getItem().getSwingAmount() == 90) {
				if (ti == tiMax) {
					getInventory().getSelectedItem().getItem().setSwingAmount(-45);
					
					if (Main.getWorldHandler().getActiveEntities().size() != 0 && !getInventory().getSelectedItem().equals(ItemStack.EMPTY)) {
						for (int i = 0; i < Main.getWorldHandler().getActiveEntities().size(); i++) {
							Entity e = Main.getWorldHandler().getActiveEntities().get(i);
							
							if (e.getBoundsAll().intersects(getSwingBounds(getInventory().getSelectedItem().getItem(), direction))) {
								if (e instanceof EntityLiving) {
									((EntityLiving) e).wasHit = true;
									((EntityLiving) e).hit(getInventory().getSelectedItem().getItem().getDamage(), getInventory().getSelectedItem().getItem().getCritChance(), Main.getWorldHandler().getPlayer());
								}
							}
						}
					}
				}
				
				if (ti == 0) {
					getInventory().getSelectedItem().getItem().setSwingAmount(90);
					ti = tiMax;
					atkT.stop();
				} else {
					ti--;
				}
			}
		}
	});
	
	public EntityPlayer(double x, double y) {
		super(x, y, 24, 44, 30, EntityProperties.PLAYER);
	}
	
	@Override
	public void tick() {
		super.tick();
		if (MouseInput.leftClick) {
			attack();
		}
	}
	
	@Override
	public void onHit(float damage, boolean isCrit) {
		
	}
	
	public void attack() {
		if (!atkT.isRunning()) {
			atkT.start();
		}
	}
	
	public double getSpeed() {
		return speed;
	}
	
	/** Returns the player's inventory */
	public PlayerInventory getInventory() {
		return inventory;
	}
	
	/** Returns the player's magnet bounds */
	public Rectangle getMagnetBounds() {
		return new Rectangle(MathHelper.floor(getPositionX()) - 32, MathHelper.floor(getPositionY()) - 32, width + 64, height + 64);
	}
	
	/** Returns the player's pickup bounds */
	public Rectangle getPickupBounds() {
		return new Rectangle(MathHelper.floor(getPositionX()) - 4, MathHelper.floor(getPositionY()) - 4, width + 8, height + 8);
	}
	
	/** Returns the player's interaction bounds */
	public Rectangle getInteractionBounds() {
		return new Rectangle(MathHelper.floor(getPositionX()) - 64, MathHelper.floor(getPositionY()) - 64, width + 128, height + 128);
	}
	
	/** Returns the player's swing bounds */
	public Rectangle getSwingBounds(Item item, int dir) {
		if (dir == 1) {
			return new Rectangle(MathHelper.floor(getPositionX() + width), MathHelper.floor(getPositionY() - 12), item.getRange(), item.getRange() + 20);
		} else if (dir == -1) {
			return new Rectangle(MathHelper.floor(getPositionX()) - item.getRange(), MathHelper.floor(getPositionY() - 12), item.getRange(), item.getRange() + 20);
		}
		return null;
	}
}
