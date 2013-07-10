package com.twopeople.race.entity.Unit;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.EntityGridVault;
import com.twopeople.race.entity.MoveableEntity;

public class Player extends MoveableEntity {
	public Player(World world, float x, float y) {
		super(world, x, y, 64, 64);

		setFriction(0.9f);
		setMaxXSpeed(10f);
		setMaxYSpeed(10f);
	}

	@Override
	public void update(GameContainer container, int delta, EntityGridVault vault) {
		Input input = container.getInput();

		if (input.isKeyDown(Input.KEY_A)) {
			angle -= 0.5f;
		}
		if (input.isKeyDown(Input.KEY_D)) {
			angle += 0.5f;
		}
		if (input.isKeyDown(Input.KEY_W)) {
			acc -= 0.1f;
		}
		if (input.isKeyDown(Input.KEY_S)) {
			acc += 0.1f;
		}

		direction.set((float) Math.cos(angle), (float) Math.sin(angle));

		move(direction, delta);
		super.update(container, delta, vault);
	}

	@Override
	public void render(GameContainer container, Graphics g, Camera camera) {
		Image image = Art.ships.getSprite(0, 0);
		image.setCenterOfRotation(w / 2, h / 2);
		image.rotate(angle);
		image.draw(camera.getScreenX(x), camera.getScreenY(y));
	}
}