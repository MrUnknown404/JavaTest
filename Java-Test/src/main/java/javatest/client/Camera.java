package main.java.javatest.client;

import main.java.javatest.Main;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.util.Console;
import main.java.javatest.util.math.Vec2d;
import main.java.javatest.world.World;

public class Camera {

	private Vec2d pos = new Vec2d();
	private static EntityPlayer player;
	private boolean[] bool = new boolean[2];
	
	public static void getPlayer() {
		for (int i = 0; i < World.getActiveEntities().size(); i++) {
			if (World.getActiveEntities().get(i) instanceof EntityPlayer) {
				player = (EntityPlayer) World.getActiveEntities().get(i);
				return;
			}
		}
		System.err.println(Console.info(Console.WarningType.FatalError) + "Player not found!");
	}
	
	public void tick() {
		if (player == null) {
			getPlayer();
			return;
		}
		
		if (pos.x != (-player.getPositionX() - (player.getWidth() / 2)) + Main.WIDTH / 2) {
			pos.x = (-player.getPositionX() - (player.getWidth() / 2)) + Main.WIDTH / 2;
			bool[0] = true;
		}
		if (pos.y != (-player.getPositionY() - player.getHeight()) + Main.HEIGHT / 2) {
			pos.y = (-player.getPositionY() - player.getHeight()) + Main.HEIGHT / 2;
			bool[1] = true;
		}
		
		if (bool[0] || bool[1]) {
			World.redoActives();
		}
		bool[0] = false;
		bool[1] = false;
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
