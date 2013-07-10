package com.twopeople.race.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import com.twopeople.race.World.World;

public class MoveableEntity extends Entity {
	private float friction;
	protected float acc;
	private float maxXSpeed, maxYSpeed;
	private Vector2f velocity = new Vector2f(0, 0);
	private Vector2f acceleration = new Vector2f(0, 0);

	public MoveableEntity(World world, float x, float y, int w, int h) {
		super(world, x, y, w, h);
	}

	public void setFriction(float f) {
		this.friction = f;
	}

	public void setMaxXSpeed(float xs) {
		this.maxXSpeed = xs;
	}

	public void setMaxYSpeed(float ys) {
		this.maxYSpeed = ys;
	}

	public boolean isMoving() {
		return velocity.x > 0 || velocity.y > 0;
	}

	@Override
	public void update(GameContainer container, int delta, EntityGridVault vault) {
		acceleration.x = (float) (-(velocity.x * friction) + Math.cos(direction.x) * acc);
		acceleration.y = (float) (-(velocity.y * friction) + Math.sin(direction.y) * acc);

		velocity.x += acceleration.x * delta * .08f;
		velocity.y += acceleration.y * delta * .08f;

		x += velocity.x * (delta * .01f) * 5;
		y += velocity.y * (delta * .01f) * 5;

		if (isMoving()) {
			vault.moved(this);
		}

		// System.out.println(acceleration.x + ", " + acceleration.y);
		// System.out.println(velocity.x + ", " + velocity.y);
		// System.out.println(x + ", " + y);
		// System.out.println(getCellX() + ", " + getCellY());
		// System.out.println("=============");
	}

	public void move(Vector2f direction, int delta) {

	}
}