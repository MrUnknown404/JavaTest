package main.java.javatest.entity;

import java.util.List;

import main.java.javatest.Main;
import main.java.javatest.blocks.util.Block;
import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.entity.util.EntityProperties;
import main.java.javatest.util.TickableGameObject;
import main.java.javatest.util.math.MathHelper;
import main.java.javatest.util.math.Vec2d;
import main.java.javatest.world.util.Chunk;

public abstract class Entity extends TickableGameObject {
	
	protected EntityProperties type;
	protected boolean doGravity = false;
	private double gravityY = 0;
	private Vec2d velocity = new Vec2d();
	
	public Entity(double x, double y, int width, int height, EntityProperties type) {
		super(x, y, width, height);
		this.type = type;
		doGravity = type.getDoGravity();
	}
	
	@Override
	public void tick() {
		doVelocity();
	}
	
	/** Gets what's below the entity plus the argument */
	public TickableGameObject getBelow(double y) {
		if (Main.getWorldHandler().getChunksEntityIsIn(this).isEmpty()) {
			return null;
		}
		
		List<Chunk> cnks = Main.getWorldHandler().getChunksEntityIsIn(this);
		for (int i2 = 0; i2 < cnks.size(); i2++) {
			for (int i = 0; i < cnks.get(i2).getBlocks().size(); i++) {
				TickableGameObject obj = cnks.get(i2).getBlocks().get(i);
				if (obj instanceof Block && obj != this) {
					Block tObj = (Block) obj;
					if (tObj.getBlockProperties().getHasCollision()) {
						if (getBoundsBottom().intersects(tObj.getBoundsTop().x, tObj.getBoundsTop().y - Main.getWorldHandler().getWorldInfo().gravity + y, tObj.getBoundsTop().getWidth(), tObj.getBoundsTop().getHeight())) {
							return tObj;
						}
					}
				}
			}
		}
		return null;
	}

	/** Gets what's above the entity plus the argument */
	public TickableGameObject getAbove(double y) {
		if (Main.getWorldHandler().getChunksEntityIsIn(this).isEmpty()) {
			return null;
		}
		
		List<Chunk> cnks = Main.getWorldHandler().getChunksEntityIsIn(this);
		for (int i2 = 0; i2 < cnks.size(); i2++) {
			for (int i = 0; i < cnks.get(i2).getBlocks().size(); i++) {
				TickableGameObject obj = cnks.get(i2).getBlocks().get(i);
				if (obj instanceof Block && obj != this) {
					Block tObj = (Block) obj;
					if (tObj.getBlockProperties().getHasCollision()) {
						if (getBoundsTop().intersects(tObj.getBoundsBottom().x, tObj.getBoundsBottom().y - Main.getWorldHandler().getWorldInfo().gravity + y, tObj.getBoundsBottom().getWidth(), tObj.getBoundsBottom().getHeight())) {
							return tObj;
						}
					}
				}
			}
		}
		return null;
	}
	
	/** Gets what's left the entity plus the argument */
	public TickableGameObject getLeft(double x) {
		if (Main.getWorldHandler().getChunksEntityIsIn(this).isEmpty()) {
			return null;
		}
		
		List<Chunk> cnks = Main.getWorldHandler().getChunksEntityIsIn(this);
		for (int i2 = 0; i2 < cnks.size(); i2++) {
			for (int i = 0; i < cnks.get(i2).getBlocks().size(); i++) {
				TickableGameObject obj = cnks.get(i2).getBlocks().get(i);
				if (obj instanceof Block && obj != this) {
					Block tObj = (Block) obj;
					if (tObj.getBlockProperties().getHasCollision()) {
						if (getBoundsLeft().intersects(tObj.getBoundsRight().x + x, tObj.getBoundsRight().y, tObj.getBoundsRight().getWidth(), tObj.getBoundsRight().getHeight())) {
							return tObj;
						}
					}
				}
			}
		}
		return null;
	}
	
	/** Gets what's right the entity plus the argument */
	public TickableGameObject getRight(double x) {
		if (Main.getWorldHandler().getChunksEntityIsIn(this).isEmpty()) {
			return null;
		}
		
		List<Chunk> cnks = Main.getWorldHandler().getChunksEntityIsIn(this);
		for (int i2 = 0; i2 < cnks.size(); i2++) {
			for (int i = 0; i < cnks.get(i2).getBlocks().size(); i++) {
				TickableGameObject obj = cnks.get(i2).getBlocks().get(i);
				if (obj instanceof Block && obj != this) {
					Block tObj = (Block) obj;
					if (tObj.getBlockProperties().getHasCollision()) {
						if (getBoundsRight().intersects(tObj.getBoundsLeft().x + x, tObj.getBoundsLeft().y, tObj.getBoundsLeft().getWidth(), tObj.getBoundsLeft().getHeight())) {
							return tObj;
						}
					}
				}
			}
		}
		return null;
	}
	
	public boolean isGrounded() {
		TickableGameObject obj = getBelow(0);
		if (obj != null && obj instanceof Block) {
			return true;
		}
		return false;
	}
	
	public void doVelocity() {
		if (getVelocityX() != 0) {
			TickableGameObject o = getLeft(-getVelocityX());
			TickableGameObject o2 = getRight(-getVelocityX());
			if ((o != null || o2 != null) && getEntityProperties().getDoCollision()) {
				if (o != null) {
					double y = o.getPositionX() - getPositionX();
					if (y > getVelocityX()) {
						addPositionX(getPositionX() - o.getPositionX());
					}
				} else if (o2 != null) {
					double y = o2.getPositionX() - getPositionX();
					if (y < getVelocityX()) {
						addPositionX(o2.getPositionX() - getPositionX() + o2.getWidth());
					}
				}
			} else {
				addPositionX(getVelocityX());
			}
			
			if (getVelocityX() > 0) {
				setVelocityX(MathHelper.clamp(getVelocityX() - Main.getWorldHandler().getWorldInfo().friction, 0, Double.MAX_VALUE));
			} else if (getVelocityX() < 0) {
				setVelocityX(MathHelper.clamp(getVelocityX() + Main.getWorldHandler().getWorldInfo().friction, -Double.MAX_VALUE, 0));
			}
		}
		
		if (getVelocityY() != 0) {
			TickableGameObject o = getBelow(-getVelocityY());
			TickableGameObject o2 = getAbove(-getVelocityY());
			if ((o != null || o2 != null) && getEntityProperties().getDoCollision()) {
				if (o != null) {
					double y = o.getPositionY() - getPositionY();
					if (y > getVelocityY()) {
						addPositionY(y - height);
					}
				} else if (o2 != null) {
					double y = o2.getPositionY() - getPositionY();
					if (y < getVelocityY()) {
						addPositionY(y + o2.getHeight());
					}
				}
			} else {
				addPositionY(getVelocityY());
			}
			
			if (getVelocityY() > 0) {
				setVelocityY(MathHelper.clamp(getVelocityY() - Main.getWorldHandler().getWorldInfo().friction, 0, Double.MAX_VALUE));
			} else if (getVelocityY() < 0) {
				setVelocityY(MathHelper.clamp(getVelocityY() + Main.getWorldHandler().getWorldInfo().friction, -Double.MAX_VALUE, 0));
			}
		}
		
		if (getGravityY() != 0) {
			TickableGameObject o = getBelow(-getGravityY());
			if (o != null && this instanceof EntityPlayer) {
				double y = o.getPositionY() - getPositionY();
				if (y > getGravityY()) {
					addPositionY(y - height);
				}
			} else {
				addPositionY(getGravityY());
			}
			
			if (getGravityY() > 0) {
				setGravityY(MathHelper.clamp(getGravityY() - Main.getWorldHandler().getWorldInfo().friction, 0, Double.MAX_VALUE));
			} else if (getGravityY() < 0) {
				setGravityY(MathHelper.clamp(getGravityY() + Main.getWorldHandler().getWorldInfo().friction, -Double.MAX_VALUE, 0));
			}
		}
		
		if (doGravity) {
			if (getEntityProperties().getDoCollision()) {
				if (isGrounded() && getGravityY() != 0) {
					setPositionY(getBelow(0).getPositionY() - height);
					if (this instanceof EntityPlayer) {
						((EntityPlayer) this).setJumpY(0);
					}
					setGravityY(0);
				}
			}
			
			if (!isGrounded()) {
				addGravityY(Main.getWorldHandler().getWorldInfo().gravity / (Main.getWorldHandler().getWorldInfo().gravity * 2));
				if (getGravityY() > Main.getWorldHandler().getWorldInfo().maxFallSpeed) {
					setGravityY(Main.getWorldHandler().getWorldInfo().maxFallSpeed);
				}
			}
		} else {
			if (getEntityProperties().getDoCollision()) {
				if (isGrounded() && getGravityY() != 0) {
					setPositionY(getBelow(0).getPositionY() - height);
					if (this instanceof EntityPlayer) {
						((EntityPlayer) this).setJumpY(0);
					}
					setGravityY(0);
				}
			}
		}
	}
	
	/** Adds to the entities velocity */
	public void addVelocity(double x, double y) {
		velocity = velocity.add(velocity.x + x, velocity.y + y);
	}
	
	/** Adds to the entities velocity */
	public void addVelocity(Vec2d vec) {
		velocity = velocity.add(velocity.x + vec.x, velocity.y + vec.y);
	}
	
	/** Adds to the entities X velocity */
	public void addVelocityX(double x) {
		velocity.x += x;
	}
	
	/** Adds to the entities Y velocity */
	public void addVelocityY(double y) {
		velocity.y += y;
	}
	
	/** Sets the entities velocity */
	public void setVelocity(double x, double y) {
		velocity = new Vec2d(x, y);
	}
	
	/** Sets the entities velocity */
	public void setVelocity(Vec2d vec) {
		velocity = vec;
	}
	
	/** Sets the entities X velocity */
	public void setVelocityX(double x) {
		velocity.x = x;
	}
	
	/** Sets the entities Y velocity */
	public void setVelocityY(double y) {
		velocity.y = y;
	}
	
	/** Gets the entities velocity */
	public Vec2d getVelocity() {
		return velocity;
	}
	
	protected void setGravityY(double amount) {
		gravityY = amount;
	}
	
	public double getGravityY() {
		return gravityY;
	}
	
	protected void addGravityY(double amount) {
		gravityY += amount;
	}
	
	/** Gets the entities X velocity */
	public double getVelocityX() {
		return velocity.x;
	}
	
	/** Gets the entities Y velocity */
	public double getVelocityY() {
		return velocity.y;
	}
	
	public EntityProperties getEntityProperties() {
		return type;
	}
}
