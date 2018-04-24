package main.java.javatest.util.handlers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import main.java.javatest.entity.entityliving.EntityLiving;
import main.java.javatest.util.Console;
import main.java.javatest.util.GameObject;

public class ObjectHandler {

	private static List<GameObject> objectsAll = new ArrayList<GameObject>();
	private static List<GameObject> objectsActive = new ArrayList<GameObject>();
	
	public void tick() {
		for (int i = 0; i < objectsActive.size(); i++) {
			GameObject obj = objectsActive.get(i);
			
			if (objectsActive.contains(obj) && obj != null) {
				obj.tick();
				if (obj instanceof EntityLiving) {
					EntityLiving eObj = (EntityLiving) obj;
					eObj.tickAlive();
				}
			}
		}
	}
	
	public void gameTick() {
		for (int i = 0; i < objectsActive.size(); i++) {
			GameObject obj = objectsActive.get(i);
			
			if (objectsActive.contains(obj) && obj != null) {
				obj.gameTick();
				if (obj instanceof EntityLiving) {
					EntityLiving eObj = (EntityLiving) obj;
					eObj.gameTickAlive();
				}
			}
		}
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < objectsActive.size(); i++) {
			GameObject obj = objectsActive.get(i);
			
			if (objectsActive.contains(obj) && obj != null) {
				obj.render(g);
			}
		}
	}
	
	/** don't use */
	public static void clearObjectsAll() {
		objectsAll.clear();
		objectsActive.clear();
	}
	
	public static void addObjectAll(GameObject object) {
		if (object != null) {
			objectsAll.add(object);
		} else {
			System.out.println(Console.info(Console.EnumWarningType.Error) + "entity is null!");
		}
	}
	
	public static void removeObjectAll(GameObject object) {
		if (objectsAll.contains(object)) {
			objectsAll.remove(object);
			if (objectsActive.contains(object)) {
				objectsActive.remove(object);
			}
		} else {
			System.out.println(Console.info(Console.EnumWarningType.Error) + "entity is null!");
		}
	}
	
	public static List<GameObject> getObjectsAll() {
		return objectsAll;
	}
	
	public static void addObjectActive(GameObject object) {
		if (object != null) {
			objectsActive.add(object);
		} else {
			System.out.println(Console.info(Console.EnumWarningType.Error) + "entity is null!");
		}
	}
	
	public static void removeObjectActive(GameObject object) {
		if (objectsActive.contains(object)) {
			objectsActive.remove(object);
		} else {
			System.out.println(Console.info(Console.EnumWarningType.Error) + "entity is null!");
		}
	}
	
	public static List<GameObject> getObjectsActive() {
		return objectsActive;
	}
}
