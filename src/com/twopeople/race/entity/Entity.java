package com.twopeople.race.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;

public class Entity {
	public World world;
	public float x, y;
	public int w, h;
	public int cellX, cellY;
	public float angle = 0f;

	protected Vector2f direction = new Vector2f(0, 0);

	public Entity(World world, float x, float y, int w, int h) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void setCell(int cx, int cy) {
		this.cellX = cx;
		this.cellY = cy;
	}

	public int getCellX() {
		return cellX;
	}

	public int getCellY() {
		return cellY;
	}

	public void rotate(float angle) {
		this.angle += angle;
	}

	public float getDistanceTo(Entity entity) {
		return (float) Math.sqrt(Math.pow(entity.x - x, 2.0) + Math.pow(entity.y - y, 2.0));
	}

	public void update(GameContainer container, int delta, EntityGridVault vault) {
	}

	public void render(GameContainer container, Graphics g, Camera camera) {
	}
}