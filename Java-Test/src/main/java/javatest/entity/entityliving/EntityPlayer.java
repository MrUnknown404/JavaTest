package main.java.javatest.entity.entityliving;

import java.awt.Color;
import java.awt.Graphics;

import main.java.javatest.entity.EntityLiving;
import main.java.javatest.util.math.MathHelper;

public class EntityPlayer extends EntityLiving {

	public EntityPlayer(double x, double y) {
		super(x, y);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(MathHelper.floor(getX()), MathHelper.floor(getY()), 32, 32);
	}
}
