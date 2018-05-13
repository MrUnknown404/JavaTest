package main.java.javatest.entity.entityliving;

import java.awt.Rectangle;

import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.util.math.MathHelper;

public class EntityDummy extends EntityLiving {
	
	private float damage, initDamage;
	private int damageDisapearTimeMax = 120, damageDisapearTime = damageDisapearTimeMax;
	private boolean isCrit;
	
	public EntityDummy(double x, double y) {
		super(x, y, 32, 48, 1, EntityProperties.DUMMY);
	}
	
	@Override
	public void tick() {
		super.tick();
		if (initDamage == 0 && damage != 0) {
			initDamage = damage;
		}
		
		if (damage > 0) {
			if (damageDisapearTime == 0) {
				damage = MathHelper.clamp(MathHelper.roundTo(damage - (initDamage / 8), 2), 0, Float.MAX_VALUE);
			} else {
				damageDisapearTime--;
			}
		} else if (damage == 0 && initDamage != 0) {
			initDamage = 0;
			isCrit = false;
		}
	}
	
	@Override
	public void onHit(float damage, boolean isCrit) {
		this.damage = damage;
		this.isCrit = isCrit;
		damageDisapearTime = damageDisapearTimeMax;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public boolean getIsCrit() {
		return isCrit;
	}
	
	@Override
	public Rectangle getBoundsAll() {
		return new Rectangle((int) getPositionX() + 6, (int) getPositionY() + 12, width - 12, height - 12);
	}
}
