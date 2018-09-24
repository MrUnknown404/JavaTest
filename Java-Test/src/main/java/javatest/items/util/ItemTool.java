package main.java.javatest.items.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.java.javatest.Main;

public abstract class ItemTool extends Item {
	
	protected float speed;
	protected Item tool = this;
	
	public ItemTool(String name, int range, int critChance, float damage, float speed) {
		super(name, 1, range, critChance, damage);
		this.speed = speed;
	}
	
	private Timer useLeftT = new Timer(1000 / 60, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem() != tool) {
				useLeftT.stop();
			}
			useLeft();
		}
	});
	
	private Timer useRightT = new Timer(1000 / 60, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (Main.getWorldHandler().getPlayer().getInventory().getSelectedItem().getItem() != tool) {
				useRightT.stop();
			}
			useRight();
		}
	});
	
	protected void forceMaxStack(int maxStack) {
		this.maxStack = maxStack;
	}
	
	@Override
	public void onLeftClickPress() {
		if (!useLeftT.isRunning()) {
			useLeftT.start();
		}
	}
	
	@Override
	public void onRightClickPress() {
		if (!useRightT.isRunning()) {
			useRightT.start();
		}
	}
	
	@Override
	public void onLeftClickRelease() {
		if (useLeftT.isRunning()) {
			useLeftT.stop();
		}
	}
	
	@Override
	public void onRightClickRelease() {
		if (useRightT.isRunning()) {
			useRightT.stop();
		}
	}
	
	public abstract void useLeft();
	public abstract void useRight();
}
