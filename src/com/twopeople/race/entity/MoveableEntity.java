package com.twopeople.race.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import com.twopeople.race.World.World;

public class MoveableEntity extends Entity {
	private float friction;
	private float xSpeed, ySpeed;
	private Vector2f velocity = new Vector2f(0, 0);
	private Vector2f acceleration = new Vector2f(0, 0);
	private Vector2f inertness = new Vector2f(0, 0);

	public MoveableEntity(World world, float x, float y, int w, int h) {
		super(world, x, y, w, h);
	}

	public void setFriction(float f) {
		this.friction = f;
	}

	public void setXSpeed(float xs) {
		this.xSpeed = xs;
	}

	public void setYSpeed(float ys) {
		this.ySpeed = ys;
	}

	public boolean isMoving() {
		return velocity.x > 0 || velocity.y > 0;
	}

	@Override
	public void update(GameContainer container, int delta, EntityGridVault vault) {
		acceleration.x = -(velocity.x * friction) + direction.x * xSpeed + inertness.x;
		acceleration.y = -(velocity.y * friction) + direction.y * ySpeed + inertness.y;

		velocity.x += acceleration.x * delta * .08f;
		velocity.y += acceleration.y * delta * .08f;

		inertness.x = velocity.x * delta * 0.05f;
		inertness.y = velocity.y * delta * 0.05f;

		x += velocity.x * (delta * .01f) * 5;
		y += velocity.y * (delta * .01f) * 5;

		if (isMoving()) {
			vault.moved(this);
		}

		System.out.println(acceleration.x + ", " + acceleration.y);
		System.out.println(velocity.x + ", " + velocity.y);
		System.out.println(x + ", " + y);
		System.out.println(getCellX() + ", " + getCellY());
		System.out.println("=============");
	}

	public void move(Vector2f direction, int delta) {

	}
}