package com.twopeople.race.entity.Unit;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.Entity;
import com.twopeople.race.entity.EntityGridVault;
import com.twopeople.race.entity.Projectile;

public class Turret extends Entity {
	public Turret() {
	}
	
	public Turret(World world, Entity owner) {
		super(world, owner.x, owner.y, 16, 16);
		setOwner(owner);
		bindToParent(owner);
	}

	public void updateDirection(float ms, float my) {
		Vector2f playerDir = (new Vector2f(ms - x, my - y)).normalise();
		direction.x = playerDir.x;
		direction.y = playerDir.y;
		angle = (float) direction.getTheta() + 90;
	}

	@Override
	public void update(GameContainer container, int delta, EntityGridVault vault) {
		super.update(container, delta, vault);

		Input input = container.getInput();

		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			float ax = world.getCamera().getAbsoluteX(input.getMouseX());
			float ay = world.getCamera().getAbsoluteY(input.getMouseY());
			Vector2f dir = new Vector2f(-angle);
			world.addProjectile(new Projectile(world, x, y, direction));
		}
	}

	@Override
	public void render(GameContainer container, Graphics g, Camera camera) {
		Image image = Art.turret.getSprite(0, 0);
		image.setCenterOfRotation(w / 2, h / 2);
		image.rotate(angle);
		image.draw(camera.getScreenX(x), camera.getScreenY(y));
	}
}