package main.java.javatest.items.util;

import main.java.javatest.init.Items;

public abstract class Item {
	protected String name;
	protected int maxStack, range, critChance;
	protected float damage;
	
	public Item(String name, int maxStack, int range, int critChance, float damage) {
		this.name = name;
		this.maxStack = maxStack;
		this.range = range;
		this.critChance = critChance;
		this.damage = damage;
		Items.items.add(this);
	}
	
	public abstract void onLeftClickPress();
	public abstract void onRightClickPress();
	
	public abstract void onLeftClickRelease();
	public abstract void onRightClickRelease();
	
	public String getName() {
		return name;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public int getMaxStack() {
		return maxStack;
	}
	
	public int getRange() {
		return range;
	}
	
	public int getCritChance() {
		return critChance;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			if (((Item) obj).name.equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + name + ")";
	}
}
