package com.twopeople.race.entity;

import java.util.ArrayList;

import com.twopeople.race.World.Camera;

public class EntityGridVault {
	public int cellsX, cellsY;
	public int cellWidth, cellHeight;
	public ArrayList<Entity>[] entities;
	private int totalAmount;

	@SuppressWarnings("unchecked")
	public EntityGridVault(int cw, int ch, int world_w, int world_h) {
		this.cellWidth = cw;
		this.cellHeight = ch;
		this.cellsX = world_w / cw;
		this.cellsY = world_h / ch;
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
		++totalAmount;
	}

	public void remove(Entity entity) {
		if (getAt(entity.getCellX(), entity.getCellY()).remove(entity)) {
			--totalAmount;
		}
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

	public ArrayList<Entity> getCollidables(Entity entity) {
		ArrayList<Entity> intersecting = new ArrayList<Entity>();
		if (totalAmount == 0) return intersecting;
		ArrayList<Entity> ranged = new ArrayList<Entity>();

		int cx = getCellX(entity);
		int cy = getCellY(entity);
		int scx = cx - 1;
		int scy = cy - 1;
		int ecx = cx + 1;
		int ecy = cy + 1;
		if (scx < 0) scx = 0;
		if (scy < 0) scy = 0;
		if (ecx >= cellsX) ecx = cellsX - 1;
		if (ecy >= cellsY) ecy = cellsY - 1;

		for (int x = scx; x <= ecx; ++x) {
			for (int y = scy; y <= ecy; ++y) {
				ranged.addAll(getAt(x, y));
			}
		}

		for (Entity e : ranged) {
			if (entity.collidesWith(e) && entity != e) {
				intersecting.add(e);
			}
		}

		return intersecting;
	}

	public int size() {
		return totalAmount;
	}

	public void moved(Entity entity) {
		remove(entity);
		add(entity);
	}
}