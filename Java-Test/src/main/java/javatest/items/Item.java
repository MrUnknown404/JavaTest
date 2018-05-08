package main.java.javatest.items;

import main.java.javatest.init.Items;

public class Item {
	protected String name;
	protected int maxStack;
	protected float speed;
	protected ItemType type;
	
	public Item(String name, int maxStack, float speed, ItemType type) {
		this.name = name;
		this.maxStack = maxStack;
		this.speed = speed;
		this.type = type;
		Items.items.add(this);
	}
	
	public String getName() {
		return name;
	}
	
	public int getMaxStack() {
		return maxStack;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public ItemType getItemType() {
		return type;
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
		block(1),
		tool (2);
		
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
