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

	public AnimatedEntity() {
	}

	public AnimatedEntity(World world, float x, float y, int w, int h) {
		super(world, x, y, w, h);
	}

	public void setAnimSpeed(int speed) {
		this.animSpeed = speed;
	}

	public void setAnimation(SpriteSheet sprite, int sx, int ex, int sy, int ey, boolean reflect) {
		ArrayList<Image> images = new ArrayList<Image>();

		for (int x = sx; x <= ex; ++x) {
			for (int y = sy; y <= ey; ++y) {
				images.add(sprite.getSprite(x, y));
			}
		}

		int s = images.size();
		if (reflect) s = s * 2 - 2;
		Image[] a_images = new Image[s];
		for (int i = 0; i < images.size(); ++i) {
			a_images[i] = images.get(i);
		}
		if (reflect) {
			int k = images.size() - 1;
			for (int i = images.size() - 1; i >= 1; --i) {
				a_images[k++] = images.get(i);
			}
		}

		images.clear();

		animation = new Animation(a_images, animSpeed, true);
	}

	public void render(GameContainer container, Graphics g, Camera camera) {
		animation.draw(camera.getScreenX(x), camera.getScreenY(y));
	}
}