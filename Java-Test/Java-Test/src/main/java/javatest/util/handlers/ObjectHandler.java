package main.java.javatest.util.handlers;

import java.awt.Graphics;
import java.util.LinkedList;

import main.java.javatest.entity.Entity;
import main.java.javatest.entity.EntityLiving;
import main.java.javatest.util.GameObject;

public class ObjectHandler {

	public LinkedList<Entity> object = new LinkedList<Entity>();
	
	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}
	}
	
	public void tickAlive() {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			if (tempObject instanceof EntityLiving) {
				((EntityLiving)tempObject).tickAlive();
			}
		}
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	public void addObject(Entity entity) {
		this.object.add(entity);
	}
	
	public void removeObject(Entity entity) {
		this.object.remove(entity);
	}
}
