package main.java.javatest.entity.util;

import main.java.javatest.util.Console;

public class EntityProperties {

	public static final EntityProperties PLAYER = new EntityProperties(EntityType.player);
	
	private boolean hasCollision;
	private boolean doGravity;
	private boolean doCollision;
	private boolean alwaysLoad;
	private EntityType type;
	
	public EntityProperties(EntityType type) {
		this.type = type;
		
		switch (type) {
			case player:
				setHasCollision(false);
				setDoGravity(true);
				setDoCollision(true);
				setAlwaysLoad(true);
				break;
			default:
				System.out.println(Console.info(Console.WarningType.Error) + "Invalid type");
				break;
		}
	}
	
	public boolean getHasCollision() {
		return hasCollision;
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
	
	public EntityProperties setHasCollision(boolean bool) {
		hasCollision = bool;
		return this;
	}
	
	public EntityProperties setDoGravity(boolean bool) {
		doGravity = bool;
		return this;
	}
	
	public EntityProperties setDoCollision(boolean bool) {
		doCollision = bool;
		return this;
	}
	
	public EntityProperties setAlwaysLoad(boolean bool) {
		alwaysLoad = bool;
		return this;
	}
	
	public EntityType getEntityType() {
		return type;
	}
	
	public enum EntityType {
		player(0);
		
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
