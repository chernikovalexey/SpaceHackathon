package com.twopeople.race.entity.Unit;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import com.twopeople.race.World.World;
import com.twopeople.race.entity.MoveableEntity;

public class Player extends MoveableEntity {
	public Player(World world, float x, float y) {
		super(world, x, y, 64, 64);

		setFriction(.01f);
		setXSpeed(5f);
		setYSpeed(5f);
	}

	@Override
	public void update(GameContainer container, int delta) {
		Input input = container.getInput();
		boolean pressed = false;

		if (input.isKeyDown(Input.KEY_A)) {
			direction.x = -1;
			pressed = true;
		}
		if (input.isKeyDown(Input.KEY_D)) {
			direction.x = 1;
			pressed = true;
		}
		if (input.isKeyDown(Input.KEY_W)) {
			direction.y = -1;
			pressed = true;
		}
		if (input.isKeyDown(Input.KEY_S)) {
			direction.y = 1;
			pressed = true;
		}

		if (pressed) {
			move(direction, delta);
		}
		super.update(container, delta);
	}
}