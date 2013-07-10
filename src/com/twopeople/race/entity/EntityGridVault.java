package com.twopeople.race.entity;

import java.util.ArrayList;

import com.twopeople.race.World.Camera;

public class EntityGridVault {
	private int cellsX, cellsY;
	private int cellWidth, cellHeight;
	private ArrayList<Entity>[] entities;

	@SuppressWarnings("unchecked")
	public EntityGridVault(int cw, int ch, int w, int h) {
		this.cellWidth = cw;
		this.cellHeight = ch;
		this.cellsX = w;
		this.cellsY = h;
		this.entities = new ArrayList[cellsX * cellsY];
		for (int x = 0; x < cellsX; ++x) {
			for (int y = 0; y < cellsY; ++y) {
				entities[x + y * cellsX] = new ArrayList<Entity>();
			}
		}
	}

	public int getCellX(Entity entity) {
		int cx = (int) Math.floor(entity.x / cellWidth);
		if (cx < 0) cx = 0;
		if (cx >= cellsX) return cellsX - 1;
		return cx;
	}

	public int getCellY(Entity entity) {
		int cy = (int) Math.floor(entity.y / cellHeight);
		if (cy < 0) cy = 0;
		if (cy >= cellsY) return cellsY - 1;
		return cy;
	}

	public ArrayList<Entity> getAt(int x, int y) {
		return entities[x + y * cellsX];
	}

	public void add(Entity entity) {
		int cx = getCellX(entity);
		int cy = getCellY(entity);
		entity.setCell(cx, cy);
		getAt(cx, cy).add(entity);
	}

	public void remove(Entity entity) {
		getAt(entity.getCellX(), entity.getCellY()).remove(entity);
	}

	public ArrayList<Entity> getAll() {
		ArrayList<Entity> all = new ArrayList<Entity>();
		for (int x = 0; x < cellsX; ++x) {
			for (int y = 0; y < cellsY; ++y) {
				all.addAll(getAt(x, y));
			}
		}
		return all;
	}

	public ArrayList<Entity> getVisible(Camera camera) {
		ArrayList<Entity> visible = new ArrayList<Entity>();

		int sx = (int) (camera.getX() / cellWidth) - 1;
		int sy = (int) (camera.getY() / cellHeight) - 1;
		int ex = sx + (int) (camera.getScreenWidth() / cellWidth) + 1;
		int ey = sy + (int) (camera.getScreenHeight() / cellHeight) + 1;
		if (sx < 0) sx = 0;
		if (sy < 0) sy = 0;
		if (ex >= cellsX) ex = cellsX - 1;
		if (ey >= cellsY) ey = cellsY - 1;

		for (int x = sx; x <= ex; ++x) {
			for (int y = sy; y <= ey; ++y) {
				visible.addAll(getAt(x, y));
			}
		}

		return visible;
	}

	public void moved(Entity entity) {
		remove(entity);
		add(entity);
	}
}