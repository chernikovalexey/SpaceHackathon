package com.twopeople.race.entity.Interior;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.CollisionType;
import com.twopeople.race.entity.Entity;

public class BorderBlock extends Entity {

	public enum BlockPosition {
		Left, Right
	}

	public BlockPosition position;

	public BorderBlock(World world, float x, float y) {
		super(world, x, y, 16, 16);
	}

	public void render(GameContainer container, Graphics g, Camera camera) {
		Art.track.getSprite(0, 0).draw(camera.getScreenX(x), camera.getScreenY(y));
	}
}