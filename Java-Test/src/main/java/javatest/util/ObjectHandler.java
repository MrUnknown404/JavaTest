package main.java.javatest.util;

import java.util.ArrayList;
import java.util.List;

import main.java.javatest.entity.entityliving.EntityLiving;

public class ObjectHandler {

	private static List<GameObject> objectsAll = new ArrayList<GameObject>();
	
	public void tick() {
		for (int i = 0; i < objectsAll.size(); i++) {
			GameObject obj = objectsAll.get(i);
			
			if (obj != null && !obj.getIsActive()) {
				continue;
			}
			
			obj.tick();
			if (obj instanceof EntityLiving) {
				((EntityLiving) obj).tickAlive();
			}
		}
	}
	
	public void gameTick() {
		for (int i = 0; i < objectsAll.size(); i++) {
			GameObject obj = objectsAll.get(i);
			
			if (obj != null && !obj.getIsActive()) {
				continue;
			}
			
			obj.gameTick();
			if (obj instanceof EntityLiving) {
				((EntityLiving) obj).gameTickAlive();
			}
		}
	}
	
	/** don't use */
	public static void clearObjectsAll() {
		objectsAll.clear();
	}
	
	public static void addObjectAll(GameObject object) {
		if (object != null && !objectsAll.contains(object)) {
			objectsAll.add(object);
		} else {
			System.out.println(Console.info(Console.WarningType.Error) + "object is null or Object already exists!");
		}
	}
	
	public static void removeObjectAll(GameObject object) {
		if (object != null && objectsAll.contains(object)) {
			objectsAll.remove(object);
		} else {
			System.out.println(Console.info(Console.WarningType.Error) + "object is null or object does not exist!");
		}
	}
	
	public static List<GameObject> getObjectsAll() {
		return objectsAll;
	}
}
