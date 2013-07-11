package com.twopeople.race.entity;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;

public class AnimatedEntity extends MoveableEntity {
	private Animation animation;
	private int animSpeed = 200;

	public AnimatedEntity(World world, float x, float y, int w, int h) {
		super(world, x, y, w, h);
	}

	public void setAnimSpeed(int speed) {
		this.animSpeed = speed;
	}

	public void setAnimation(SpriteSheet sprite, int sx, int ex, int sy, int ey) {
		ArrayList<Image> images = new ArrayList<Image>();

		for (int x = sx; x <= ex; ++x) {
			for (int y = sy; y <= ey; ++y) {
				images.add(sprite.getSprite(x, y));
			}
		}

		Image[] a_images = new Image[images.size()];
		for (int i = 0; i < images.size(); ++i) {
			a_images[i] = images.get(i);
		}
		images.clear();

		animation = new Animation(a_images, animSpeed, true);
	}

	public void render(GameContainer container, Graphics g, Camera camera) {
		Image image = animation.getCurrentFrame();
		if (rotatable()) {
			float[] rc = getRotationCenter();
			image.setCenterOfRotation(rc[0], rc[1]);
			image.rotate(angle);
		}
		image.draw(camera.getScreenX(x), camera.getScreenY(y));
	}
}