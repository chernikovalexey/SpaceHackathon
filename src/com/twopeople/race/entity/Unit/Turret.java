package com.twopeople.race.entity.Unit;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.Entity;

public class Turret extends Entity {
	public Turret(World world, Entity owner) {
		super(world, owner.x, owner.y, 16, 16);
		setOwner(owner);
		bindToParent(owner);
	}

	public void updateDirection(float ms, float my) {
		angle = (float) Math.toDegrees(Math.atan2(my, ms));
		System.out.println(angle);
	}

	public void render(GameContainer container, Graphics g, Camera camera) {
		Image image = Art.turret.getSprite(0, 0);
		image.setCenterOfRotation(w / 2, h / 2);
		image.rotate(angle);
		image.draw(camera.getScreenX(x), camera.getScreenY(y));
	}
}