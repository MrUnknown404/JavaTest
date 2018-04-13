package main.java.javatest.entity.entityliving;

import java.awt.Color;
import java.awt.Graphics;

import main.java.javatest.entity.EntityLiving;
import main.java.javatest.util.math.MathHelper;

public class EntityPlayer extends EntityLiving {

	public EntityPlayer(double x, double y, int width, int height) {
		super(x, y, width, height);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width, height);
		
		/* draw hitboxes
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.CYAN);
		g2d.draw(getBounds());
		g.setColor(Color.RED);
		g2d.draw(getBoundsTop());
		g.setColor(Color.BLUE);
		g2d.draw(getBoundsBottom());
		g.setColor(Color.GREEN);
		g2d.draw(getBoundsLeft());
		g.setColor(Color.YELLOW);
		g2d.draw(getBoundsRight());
		//*/
	}
}
