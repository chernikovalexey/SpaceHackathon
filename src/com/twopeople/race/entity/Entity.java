package com.twopeople.race.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.twopeople.race.World.World;

public class Entity {
	private World world;
	protected float x, y;
	protected int w, h;
	private int cellX, cellY;

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

	public void update(GameContainer container, int delta, EntityGridVault vault) {
	}

	public void render(GameContainer container, Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, w, h);
	}
}