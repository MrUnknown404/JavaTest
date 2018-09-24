package main.java.javatest.items.util;

public class ItemSword extends ItemTool {

	public ItemSword(String name, int range, int critChance, float damage, float speed) {
		super(name, range, critChance, damage, speed);
	}
	
	@Override
	public void useLeft() {
		//swing
	}
	
	@Override
	public void useRight() {
		
	}
}
