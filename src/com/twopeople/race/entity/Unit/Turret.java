package com.twopeople.race.entity.Unit;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.Entity;
import com.twopeople.race.entity.EntityGridVault;
import com.twopeople.race.entity.Projectile;

public class Turret extends Entity {
	private long lastShoot = System.currentTimeMillis();
	private final int CHILL_TIME = 150;

	public Turret() {
	}

	public Turret(World world, Entity owner) {
		super(world, owner.x, owner.y, 32, 32);
		setOwner(owner);
		bindToParent(owner);
	}

	public void updateDirection(float ms, float my) {
		Vector2f playerDir = (new Vector2f(ms - x, my - y)).normalise();
		direction.x = playerDir.x;
		direction.y = playerDir.y;
		angle = (float) direction.getTheta() + 90;
	}

	public void shoot(float ax, float ay) {
		long current = System.currentTimeMillis();
		if (current - lastShoot > CHILL_TIME) {
			lastShoot = current;

			Vector2f dir = new Vector2f(angle + 90);
			world.addProjectile(new Projectile(world, x, y, dir));
		}
	}

	@Override
	public void update(GameContainer container, int delta, EntityGridVault vault) {
		super.update(container, delta, vault);

		Input input = container.getInput();

		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			float ax = world.getCamera().getAbsoluteX(input.getMouseX());
			float ay = world.getCamera().getAbsoluteY(input.getMouseY());
			shoot(ax, ay);
		}
	}

	@Override
	public void render(GameContainer container, Graphics g, Camera camera) {
		Image image = Art.turret.getSprite(0, 0);
		image.setCenterOfRotation(w / 2, h / 2 + 3f);
		image.rotate(angle);

		Shape[] skeleton = owner.getBBSkeleton();
		float cx = 0, cy = 0;
		for (int i = 0; i < skeleton.length; ++i) {
			cx += skeleton[i].getCenterX() - owner.x;
			cy += skeleton[i].getCenterY() - owner.y;
		}

		cx /= skeleton.length;
		cy /= skeleton.length;

		float mx = (cx - owner.direction.x * 20);
		float my = (cy - owner.direction.y * 20);

		image.draw(camera.getScreenX(x) + mx - w / 2, camera.getScreenY(y) + my - h / 2 - 3f);
	}
}