package main.java.javatest.entity.util;

import main.java.javatest.util.Console;

public class EntityProperties {

	public static final EntityProperties PLAYER = new EntityProperties(EntityType.player);
	public static final EntityProperties ITEM = new EntityProperties(EntityType.item);
	public static final EntityProperties DUMMY = new EntityProperties(EntityType.dummy);
	
	private boolean doGravity;
	private boolean doCollision;
	private boolean alwaysLoad;
	private float knockback;
	private EntityType type;
	
	public EntityProperties(EntityType type) {
		this.type = type;
		
		switch (type) {
			case player:
				doGravity = true;
				doCollision = true;
				alwaysLoad = true;
				knockback = 2;
				break;
			case item:
				doGravity = true;
				doCollision = true;
				alwaysLoad = false;
				break;
			case dummy:
				doGravity = true;
				doCollision = true;
				alwaysLoad = false;
				knockback = 0;
				break;
			default:
				Console.print(Console.WarningType.Error, "Invalid type");
				break;
		}
	}
	
	public boolean getDoGravity() {
		return doGravity;
	}
	
	public boolean getDoCollision() {
		return doCollision;
	}
	
	public boolean getAlwaysLoad() {
		return alwaysLoad;
	}
	
	public float getKnockback() {
		return knockback;
	}
	
	public EntityType getEntityType() {
		return type;
	}
	
	public enum EntityType {
		player(0),
		item  (1),
		dummy (2);
		
		private final int fId;
		
		private EntityType(int id) {
			fId = id;
		}
		
		public static EntityType getNumber(int id) {
			for (EntityType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
