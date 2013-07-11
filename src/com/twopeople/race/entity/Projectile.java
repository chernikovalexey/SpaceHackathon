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
		super(world, x, y, 4, 4);
		setFriction(0.025f);
		setMaxSpeed(0.15f);
	}

	@Override
	public void render(GameContainer container, Graphics g, Camera camera) {
		g.setColor(Color.red);
		g.fillRect(camera.getScreenX(x), camera.getScreenY(y), w, h);
//		Image image = Art.turret.getSprite(1, 0);
//		image.draw(camera.getScreenX(x), camera.getScreenY(y));
	}
}