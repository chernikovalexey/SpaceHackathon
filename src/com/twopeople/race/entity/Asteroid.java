package com.twopeople.race.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;

public class Asteroid extends MoveableEntity {
	private int skippedTicks = 0;
	private final int SKIP_TICKS = 6;

	public Asteroid(World world, float x, float y) {
		super(world, x, y, 128, 128);

		setCollisionType(CollisionType.All);
		setConstantSpeed(true);
		setFriction(0.01f);
		setMaxSpeed(world.getRandom().nextFloat() * .12f);
		speed = getMaxSpeed();
		direction.x = (float) Math.cos(Math.toRadians(world.getRandom().nextInt(360)));
		direction.y = (float) Math.sin(Math.toRadians(world.getRandom().nextInt(360)));
	}

	@Override
	public Shape getBounds() {
		return new Circle(x + w / 2, y + h / 2, w);
	}

	@Override
	public void update(GameContainer container, int delta, EntityGridVault vault) {
		if (skippedTicks++ > SKIP_TICKS) {
			skippedTicks = 0;
			angle += 1f;
		}
		speed = getMaxSpeed();
		super.update(container, delta, vault);
	}

	@Override
	public void render(GameContainer container, Graphics g, Camera camera) {
		Image image = Art.asteroids.getSprite(0, 0);
		image.setCenterOfRotation(w / 2, h / 2);
		image.rotate(angle);
		image.draw(camera.getScreenX(x), camera.getScreenY(y));
	}
}