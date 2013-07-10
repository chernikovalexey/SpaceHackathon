package com.twopeople.race.entity;

import java.util.ArrayList;

public class EntityGridVault {
	private int cellsX, cellsY;
	private int cellWidth, cellHeight;
	private ArrayList<Entity>[] entities;

	@SuppressWarnings("unchecked")
	public EntityGridVault(int cw, int ch, int w, int h) {
		this.cellWidth = cw;
		this.cellHeight = ch;
		this.cellsX = w / cw;
		this.cellsY = h / ch;
		this.entities = new ArrayList[cellsX * cellsY];
		for (int x = 0; x < cellsX; ++x) {
			for (int y = 0; y < cellsY; ++y) {
				entities[x + y * cellsX] = new ArrayList<Entity>();
			}
		}
	}

	public int getCellX(Entity entity) {
		int cx = (int) Math.floor(entity.x / cellWidth);
		return cx;
	}

	public int getCellY(Entity entity) {
		int cy = (int) Math.floor(entity.y / cellHeight);
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

	public void moved(Entity entity) {
		remove(entity);
		add(entity);
	}
}