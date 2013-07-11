package com.twopeople.race.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;

public class Projectile extends MoveableEntity {
	public Projectile(World world, float x, float y, Vector2f direction) {
		super(world, x, y, 6, 6);
		this.direction = direction;
		setFriction(0.0025f);
		setMaxSpeed(.025f);
		speed = getMaxSpeed();
	}

	public void update(GameContainer container, int delta, EntityGridVault vault) {
		super.update(container, delta, vault);
	}

	@Override
	public void render(GameContainer container, Graphics g, Camera camera) {
		 Image image = Art.turret.getSprite(1, 0);
		 image.draw(camera.getScreenX(x), camera.getScreenY(y));
	}

	@Override
	public void ranOutsideWorld() {
		remove = true;
	}
}