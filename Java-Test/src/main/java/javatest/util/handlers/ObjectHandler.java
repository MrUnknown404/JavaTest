package main.java.javatest.util.handlers;

import java.awt.Graphics;
import java.util.LinkedList;

import main.java.javatest.entity.Entity;
import main.java.javatest.entity.EntityLiving;
import main.java.javatest.util.Console;
import main.java.javatest.util.EnumWarningType;
import main.java.javatest.util.GameObject;

public class ObjectHandler {

	public static LinkedList<Entity> objects = new LinkedList<Entity>();
	
	public void tick() {
		for (int i = 0; i < objects.size(); i++) {
			GameObject obj = objects.get(i);
			
			obj.tick();
			if (obj instanceof EntityLiving) {
				EntityLiving EObj = (EntityLiving) obj;
				EObj.tickAlive();
			}
		}
	}
	
	public void gameTick() {
		for (int i = 0; i < objects.size(); i++) {
			GameObject obj = objects.get(i);
			
			obj.gameTick();
			if (obj instanceof EntityLiving) {
				EntityLiving EObj = (EntityLiving) obj;
				EObj.gameTickAlive();
			}
		}
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < objects.size(); i++) {
			GameObject obj = objects.get(i);
			
			obj.render(g);
		}
	}
	
	public static void addObject(Entity entity) {
		if (entity != null) {
			objects.add(entity);
		} else {
			System.out.println(Console.info(EnumWarningType.Error) + "entity is null!");
		}
	}
	
	public static void removeObject(Entity entity) {
		if (objects.contains(entity)) {
			objects.remove(entity);
		} else {
			System.out.println(Console.info(EnumWarningType.Error) + "entity is null!");
		}
	}
	
	public static LinkedList<Entity> getObjectList() {
		return objects;
	}
}
