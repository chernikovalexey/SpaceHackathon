package com.twopeople.race.entity.Interior;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.CollisionType;
import com.twopeople.race.entity.EntityGridVault;
import com.twopeople.race.entity.MoveableEntity;

public class Star extends MoveableEntity {
	public Star(World world, float x, float y) {
		super(world, x, y, 16, 16);

		setConstantSpeed(true);
		setCollisionType(CollisionType.None);
		setFriction(0.025f);
		setMaxSpeed(0.05f);

		direction.x = (float) Math.cos(Math.toRadians(world.getRandom().nextInt(1440)));
		direction.y = (float) Math.sin(Math.toRadians(world.getRandom().nextInt(720)));
	}

	public void update(GameContainer container, int delta, EntityGridVault vault) {
		super.update(container, delta, vault);
		speed = getMaxSpeed();
	}

	public void render(GameContainer container, Graphics g, Camera camera) {
		Image image = Art.stars.getSprite(0, 0);
		image.draw(camera.getScreenX(x), camera.getScreenY(y));
	}
}