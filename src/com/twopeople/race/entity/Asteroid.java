package com.twopeople.race.entity;

import com.twopeople.race.World.World;

public class Asteroid extends MoveableEntity {
	public Asteroid(World world, float x, float y) {
		super(world, x, y, 24, 24);
	}
}