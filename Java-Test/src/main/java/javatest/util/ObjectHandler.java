package main.java.javatest.util;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import main.java.javatest.Main;
import main.java.javatest.entity.entityliving.EntityLiving;

public class ObjectHandler {

	private static List<GameObject> objectsAll = new ArrayList<GameObject>();
	
	public void tick() {
		for (int i = 0; i < objectsAll.size(); i++) {
			GameObject obj = objectsAll.get(i);
			
			if (obj != null && !obj.getIsActive()) {
				continue;
			} else if (obj == null) {
				return;
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
			} else if (obj == null) {
				return;
			}
			
			obj.gameTick();
			if (obj instanceof EntityLiving) {
				((EntityLiving) obj).gameTickAlive();
			}
		}
	}
	
	public static void redoActives() {
		for (int i = 0; i < objectsAll.size(); i++) {
			GameObject obj = objectsAll.get(i);
			
			if (obj == null) {
				continue;
			}
			
			Rectangle rect = new Rectangle((int) -Main.getCamera().getPositionX(), (int) -Main.getCamera().getPositionY(), Main.WIDTH, Main.HEIGHT);
			
			if (obj.getBoundsAll().intersects(rect)) {
				obj.setIsActive(true);
			} else {
				obj.setIsActive(false);
			}
		}
	}
	
	public static void redoSpecificActive(GameObject obj) {
		if (obj == null) {
			return;
		}
		
		Rectangle rect = new Rectangle((int) -Main.getCamera().getPositionX(), (int) -Main.getCamera().getPositionY(), Main.WIDTH, Main.HEIGHT);
		
		if (obj.getBoundsAll().intersects(rect)) {
			obj.setIsActive(true);
		} else {
			obj.setIsActive(false);
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
