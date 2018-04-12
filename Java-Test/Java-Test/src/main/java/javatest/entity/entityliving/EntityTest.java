package main.java.javatest.entity.entityliving;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import main.java.javatest.entity.EntityLiving;
import main.java.javatest.util.math.MathHelper;

public class EntityTest extends EntityLiving {

	Color color = null;
	
	public EntityTest(double x, double y) {
		super(x, y);
		color = new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat()); // / 2f + 0.5f
		
		//addVelocity(ThreadLocalRandom.current().nextDouble(-3, 3), ThreadLocalRandom.current().nextDouble(-3, 3));
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(MathHelper.floor(getX()), MathHelper.floor(getY()), 32, 32);
	}
}
