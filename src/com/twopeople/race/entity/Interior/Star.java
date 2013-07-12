package com.twopeople.race.entity.Interior;

import com.twopeople.race.entity.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;

public class Star extends MoveableEntity {
	private int _starType;

	private int skippedTicks = 0;
	private float alpha;
	private float minAlpha = 125;
	private float maxAlpha = 255;
	private boolean fadingOut = true;
	private int alphaDelta;

	public Star(World world, float x, float y) {
		super(world, x, y, 16, 16);

		setCollisionType(CollisionType.None);

		_starType = _getStarType();
		alpha = maxAlpha;
		alphaDelta = world.getRandom().nextInt(8) + world.getRandom().nextInt(8);
		direction.x = (float) Math.cos(Math.toRadians(world.getRandom().nextInt(90)));
		direction.y = (float) Math.sin(Math.toRadians(world.getRandom().nextInt(360)));
		
		setMaxSpeed(world.getRandom().nextFloat() * 0.005f);
		speed = getMaxSpeed();
	}

	public static Star polar(World world, float x, float y) {
		Star s = new Star(world, x, y);
		s._starType = 4;
		return s;
	}

	private int _getStarType() {
		int r = world.getRandom().nextInt() % 100;
		if (r < 70) return 0;
		else if (r < 90) return 1;
		else if (r < 97) return 2;
		else return 3;
	}

	public void update(GameContainer container, int delta, EntityGridVault vault) {
		super.update(container, delta, vault);

		if (skippedTicks++ > 2) {
			skippedTicks = 0;
			if (fadingOut) alpha -= alphaDelta;
			else alpha += alphaDelta;

			if (alpha < minAlpha) fadingOut = false;
			else if (alpha > maxAlpha) fadingOut = true;
		}
	}

	public void render(GameContainer container, Graphics g, Camera camera) {
		Image image = Art.stars.getSprite(_starType, 0);
		image.setAlpha((float) (alpha / 255));
		image.draw(camera.getScreenX(x), camera.getScreenY(y));
	}
}