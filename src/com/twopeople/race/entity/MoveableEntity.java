package com.twopeople.race.entity;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import com.twopeople.race.World.World;

public class MoveableEntity extends Entity {
	private float friction;
	public float speed, maxSpeed;
	public Vector2f velocity = new Vector2f(0, 0);
	private Vector2f acceleration = new Vector2f(0, 0);

	private boolean isCameraOwner = false;
	private boolean isConstantSpeed = false;

	public MoveableEntity(World world, float x, float y, int w, int h) {
		super(world, x, y, w, h);
	}

	public void setFriction(float f) {
		this.friction = f;
	}

	public void setMaxSpeed(float xs) {
		this.maxSpeed = xs;
	}

	public void setConstantSpeed(boolean c) {
		this.isConstantSpeed = c;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public float getRealSpeed() {
		return speed;
	}

	public void setCameraOwner(boolean o) {
		this.isCameraOwner = o;
	}

	public boolean isMoving() {
		return velocity.x > 0 || velocity.y > 0;
	}

	@Override
	public void update(GameContainer container, int delta, EntityGridVault vault) {
		if (speed < 0) speed = 0;
		if (speed > getMaxSpeed()) speed = getMaxSpeed();

		acceleration.x = (float) (-(velocity.x * friction) - direction.x * speed);
		acceleration.y = (float) (-(velocity.y * friction) - direction.y * speed);

		velocity.x += acceleration.x * delta * .08f;
		velocity.y += acceleration.y * delta * .08f;

		// x += velocity.x * (delta * .01f) * 2.5f;
		// y += velocity.y * (delta * .01f) * 2.5f;
		move(velocity.x * (delta * .01f) * 2.5f, velocity.y * (delta * .01f) * 2.5f);

		if (!isConstantSpeed) {
			speed -= getMaxSpeed() * friction * delta * 0.05f;
		}

		if (isMoving()) {
			vault.moved(this);
		}

		if (isCameraOwner) {
			world.getCamera().centerOn(this);
		}
	}

	public boolean move(float dx, float dz) {
		ArrayList<Entity> entities;

		x += dx;
		if (collisionType != CollisionType.None) {
			entities = world.getCollidableEntities(this);
			if (entities.size() > 0) {
				velocity.x = 0;
				x -= dx;
				return false;
			}
		}

		y += dz;
		if (collisionType != CollisionType.None) {
			entities = world.getCollidableEntities(this);
			if (entities.size() > 0) {
				velocity.y = 0;
				y -= dz;
				return false;
			}
		}

		return true;
	}
}