package main.java.javatest.entity.entityliving;

import java.awt.Color;
import java.awt.Graphics;

import main.java.javatest.Main;
import main.java.javatest.util.GameObject;
import main.java.javatest.util.math.MathHelper;

public class EntityPlayer extends EntityLiving {

	private double moveDirX = 0;
	
	public EntityPlayer(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void tickAlive() {
		super.tickAlive();
		
		if (moveDirX != 0) {
			GameObject o = null, o2 = null;
			if (moveDirX > 0) {
				o = getRight(-moveDirX);
			} else if (moveDirX < 0) {
				o2 = getLeft(-moveDirX);
			}
			
			if ((o != null || o2 != null) && doCollision) {
				if (o != null) {
					double x = o.getPositionX() - getPositionX();
					if (x > moveDirX) {
						addPositionX(x - width);
					}
				} else if (o2 != null) {
					addPositionX(o2.getPositionX() - getPositionX() + o2.getWidth());
				}
			} else {
				addPositionX(moveDirX);
			}
		}
	}
	
	@Override
	public void gameTickAlive() {
		super.gameTick();
		if (getPositionY() > Main.HEIGHT * 10) {
			setPositionY(0);
		}
	}
	
	/** Get move direction X */
	public double getMoveDirX() {
		return moveDirX;
	}
	
	/** Set move direction X */
	public void setMoveDirX(double x) {
		moveDirX = x;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width, height);
		super.render(g);
		
	}
}
