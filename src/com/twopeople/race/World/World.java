package com.twopeople.race.World;

import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.twopeople.race.World.Loader.WorldMetaData;
import com.twopeople.race.entity.Entity;
import com.twopeople.race.entity.EntityGridVault;
import com.twopeople.race.entity.Unit.Player;

public class World {
	private Camera camera;

	private EntityGridVault entities;

	public static final int TILE_SIZE = 16;

	private WorldMetaData metaData;

	public World(Camera camera) {
		this.camera = camera;
		this.entities = new EntityGridVault(128, 128, 12, 12);
		entities.add(new Player(this, 10, 10));
	}

	public void setMetaData(WorldMetaData data) {
		this.metaData = data;
	}

	public void update(GameContainer container, int delta) {
		updateEntitiesList(entities, container, delta);
	}

	private void updateEntitiesList(EntityGridVault vault, GameContainer container, int delta) {
		Iterator<Entity> iterator = vault.getAll().iterator();
		while (iterator.hasNext()) {
			Entity e = iterator.next();
			e.update(container, delta, vault);
		}
	}

	public void render(GameContainer container, Graphics g) {
		renderEntitiesList(entities, container, g);
	}

	private void renderEntitiesList(EntityGridVault vault, GameContainer container, Graphics g) {
		Iterator<Entity> iterator = vault.getVisible(camera).iterator();
		while (iterator.hasNext()) {
			Entity e = iterator.next();
			e.render(container, g);
		}
	}

	public Camera getCamera() {
		return camera;
	}
}