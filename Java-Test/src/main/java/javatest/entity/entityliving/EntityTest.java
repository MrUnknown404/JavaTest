package main.java.javatest.entity.entityliving;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import main.java.javatest.entity.EntityLiving;
import main.java.javatest.util.handlers.ObjectHandler;
import main.java.javatest.util.math.MathHelper;

public class EntityTest extends EntityLiving {

	Color color = new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat());
	
	public EntityTest(double x, double y, int width, int height) {
		super(x, y, width, height);
		disableGravity();
		
		if (ObjectHandler.objects.size() != 1) {
			addVelocity(ThreadLocalRandom.current().nextDouble(-12, 12),ThreadLocalRandom.current().nextDouble(-8, 8));
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width, height);
	}
}
