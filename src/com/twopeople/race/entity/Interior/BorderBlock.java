package com.twopeople.race.entity.Interior;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.AnimatedEntity;
import com.twopeople.race.entity.CollisionType;
import com.twopeople.race.entity.Entity;

public class BorderBlock extends AnimatedEntity {
	public enum BlockPosition {
		Left, Right
	}

	public BlockPosition position;

	public BorderBlock() {
	}

	public BorderBlock(World world, float x, float y) {
		super(world, x, y, 16, 16);
		setAnimSpeed(75);
		setAnimation(Art.track, 0, 7, 0, 0, true);
	}

	public void render(GameContainer container, Graphics g, Camera camera) {
		super.render(container, g, camera);
	}
}