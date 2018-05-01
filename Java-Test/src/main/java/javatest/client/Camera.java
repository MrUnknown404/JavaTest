package main.java.javatest.client;

import main.java.javatest.Main;
import main.java.javatest.util.math.Vec2d;
import main.java.javatest.world.World;

public class Camera {

	private Vec2d pos = new Vec2d();
	private boolean bool;
	
	public void tick() {
		if (World.getPlayer() == null) {
			if (!World.doesWorldExist) {
				return;
			}
			return;
		}
		
		if (!KeyInput.keyDown[3]) {
			if (pos.x != (-World.getPlayer().getPositionX() - (World.getPlayer().getWidth() / 2)) + Main.WIDTH / 2) {
				pos.x = (-World.getPlayer().getPositionX() - (World.getPlayer().getWidth() / 2)) + Main.WIDTH / 2;
				bool = true;
			}
			if (pos.y != (-World.getPlayer().getPositionY() - World.getPlayer().getHeight()) + Main.HEIGHT / 2) {
				pos.y = (-World.getPlayer().getPositionY() - World.getPlayer().getHeight()) + Main.HEIGHT / 2;
				bool = true;
			}
		} else {
			if (pos.x != -MouseInput.vec.x - World.getPlayer().getPositionX() - World.getPlayer().getWidth() + Main.WIDTH) {
				pos.x = -MouseInput.vec.x - World.getPlayer().getPositionX() - World.getPlayer().getWidth() + Main.WIDTH;
				bool = true;
			}
			if (pos.y != -MouseInput.vec.y - World.getPlayer().getPositionY() - World.getPlayer().getHeight() + Main.HEIGHT) {
				pos.y = -MouseInput.vec.y - World.getPlayer().getPositionY() - World.getPlayer().getHeight() + Main.HEIGHT;
				bool = true;
			}
		}
		
		
		if (bool) {
			World.redoActives();
		}
		bool = false;
	}
	
	/** Adds to the camera's position */
	public void addPosition(double x, double y) {
		pos = pos.add(x, y);
	}
	
	/** Adds to the camera's position */
	public void addPosition(Vec2d vec) {
		pos = pos.add(vec);
	}
	
	/** Adds to the camera's X position */
	public void addPositionX(double x) {
		pos.x += x;
	}
	
	/** Adds to the camera's Y position */
	public void addPositionY(double y) {
		pos.y += y;
	}
	
	/** Sets the camera's position */
	public void setPosition(double x, double y) {
		pos = new Vec2d(x, y);
	}
	
	/** Sets the camera's position */
	public void setPosition(Vec2d vec) {
		pos = vec;
	}
	
	/** Sets the camera's X position */
	public void setPositionX(double x) {
		pos.x = x;
	}
	
	/** Sets the camera's Y position */
	public void setPositionY(double y) {
		pos.y = y;
	}
	
	/** Gets the camera's position */
	public Vec2d getPosition() {
		return pos;
	}
	
	/** Gets the camera's X position */
	public double getPositionX() {
		return pos.x;
	}
	
	/** Gets the camera's Y position */
	public double getPositionY() {
		return pos.y;
	}
}
