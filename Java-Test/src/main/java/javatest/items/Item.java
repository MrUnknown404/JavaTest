package main.java.javatest.items;

import main.java.javatest.init.Items;
import main.java.javatest.util.math.MathHelper;

public class Item {
	protected String name;
	protected int maxStack, range, critChance;
	protected float damage, swingSpeed, swingAmount = 90;
	protected ItemType type;
	
	public Item(String name, int maxStack, int range, int critChance, float damage, float swingSpeed, ItemType type) {
		this.name = name;
		this.maxStack = maxStack;
		this.range = range;
		this.critChance = critChance;
		this.damage = damage;
		this.swingSpeed = swingSpeed;
		this.type = type;
		Items.items.add(this);
	}
	
	public Item(String name, int maxStack, int range, int critChance, double damage, double swingSpeed, ItemType type) {
		this.name = name;
		this.maxStack = maxStack;
		this.range = range;
		this.critChance = critChance;
		this.damage = (float) damage;
		this.swingSpeed = (float) swingSpeed;
		this.type = type;
		Items.items.add(this);
	}
	
	public void addSwingAmount() {
		swingAmount = MathHelper.clamp(swingAmount += swingSpeed, -45, 90);
	}
	
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
	
	public float getSwingSpeed() {
		return swingSpeed;
	}
	
	public float getSwingAmount() {
		return swingAmount;
	}
	
	public ItemType getItemType() {
		return type;
	}
	
	public void setSwingAmount(float i) {
		swingAmount = i;
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
	
	public enum ItemType {
		item (0),
		sword(1);
		
		private final int fId;
		
		private ItemType(int id) {
			fId = id;
		}
		
		public static ItemType getNumber(int id) {
			for (ItemType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
