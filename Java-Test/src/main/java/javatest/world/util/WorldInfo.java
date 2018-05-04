package main.java.javatest.world.util;

import main.java.javatest.util.math.MathHelper;

public class WorldInfo {
	public int worldLength, worldHeight, worldSeed;
	public double friction = 0.2;
	public double gravity = 1.75;
	public double maxFallSpeed = MathHelper.roundTo((gravity * gravity) * ((gravity * gravity) * (gravity * gravity)), 3);
}
