package com.twopeople.race.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import com.twopeople.race.World.World;

public class MoveableEntity extends Entity {
	private float friction;
	private float xSpeed, ySpeed;
	private Vector2f velocity = new Vector2f(0, 0);
	private Vector2f acceleration = new Vector2f(0, 0);

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

	@Override
	public void update(GameContainer container, int delta) {
		acceleration.set(0, 0);

		System.out.println(velocity.x + ", " + velocity.y);
		System.out.println(x + ", " + y);
		System.out.println("=============");
	}

	public void move(Vector2f direction, int delta) {
		acceleration.x = -(velocity.x * friction) + direction.x * xSpeed;
		acceleration.y = -(velocity.y * friction) + direction.y * ySpeed;

		velocity.x += acceleration.x * delta * .001f;
		velocity.y += acceleration.y * delta * .001f;

		x += velocity.x * (delta * .001f) * 5;
		y += velocity.y * (delta * .001f) * 5;
	}
}