package main.java.javatest.client;

import main.java.javatest.Main;
import main.java.javatest.util.math.Vec2d;

public class Camera {

	private Vec2d pos = new Vec2d();
	private boolean bool;
	
	public void tick() {
		if (Main.getWorldHandler() == null || Main.getWorldHandler().getPlayer() == null) {
			return;
		}
		
		if (!KeyInput.keyDown[3]) {
			if (pos.x != (-Main.getWorldHandler().getPlayer().getPositionX() - (Main.getWorldHandler().getPlayer().getWidth() / 2)) + Main.WIDTH_DEF / 2) {
				pos.x = (-Main.getWorldHandler().getPlayer().getPositionX() - (Main.getWorldHandler().getPlayer().getWidth() / 2)) + Main.WIDTH_DEF / 2;
				bool = true;
			}
			if (pos.y != (-Main.getWorldHandler().getPlayer().getPositionY() - Main.getWorldHandler().getPlayer().getHeight()) + Main.HEIGHT_DEF / 2) {
				pos.y = (-Main.getWorldHandler().getPlayer().getPositionY() - Main.getWorldHandler().getPlayer().getHeight()) + Main.HEIGHT_DEF / 2;
				bool = true;
			}
		} else {
			if (pos.x != -MouseInput.vec.x - Main.getWorldHandler().getPlayer().getPositionX() - Main.getWorldHandler().getPlayer().getWidth() + Main.WIDTH_DEF) {
				pos.x = -MouseInput.vec.x - Main.getWorldHandler().getPlayer().getPositionX() - Main.getWorldHandler().getPlayer().getWidth() + Main.WIDTH_DEF;
				bool = true;
			}
			if (pos.y != -MouseInput.vec.y - Main.getWorldHandler().getPlayer().getPositionY() - Main.getWorldHandler().getPlayer().getHeight() + Main.HEIGHT_DEF) {
				pos.y = -MouseInput.vec.y - Main.getWorldHandler().getPlayer().getPositionY() - Main.getWorldHandler().getPlayer().getHeight() + Main.HEIGHT_DEF;
				bool = true;
			}
		}
		
		if (bool) {
			Main.getWorldHandler().redoActives();
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
