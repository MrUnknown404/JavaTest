package main.java.javatest.entity.entityliving;

import java.awt.Color;
import java.awt.Graphics;

import main.java.javatest.client.JavaGameTest;
import main.java.javatest.entity.EntityLiving;
import main.java.javatest.util.math.MathHelper;

public class EntityPlayer extends EntityLiving {

	public EntityPlayer(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void gameTickAlive() {
		super.gameTick();
		if (getPositionY() > JavaGameTest.HEIGHT * 10) {
			setPositionY(0);
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width, height);
	}
}
