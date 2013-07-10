package com.twopeople.race.entity.Unit;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import com.twopeople.race.World.World;
import com.twopeople.race.entity.MoveableEntity;

public class Player extends MoveableEntity {
	public Player(World world, float x, float y) {
		super(world, x, y, 64, 64);

		setFriction(0.9f);
		setXSpeed(10f);
		setYSpeed(10f);
	}

	@Override
	public void update(GameContainer container, int delta) {
		Input input = container.getInput();
		boolean pressed = false;

		direction.set(0,0);
		
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