package com.twopeople.race.World;

import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.twopeople.race.entity.Entity;
import com.twopeople.race.entity.EntityGridVault;
import com.twopeople.race.entity.Unit.Player;

public class World {
	private Camera camera;

	private EntityGridVault entities;

	public static final int TILE_SIZE = 16;
	
	public World(Camera camera) {
		this.camera = camera;
		entities.add(new Player(this, 10, 10));
	}

	public void update(GameContainer container, int delta) {
		//player.update(container, delta);
	}

	private void updateEntitiesList(GameContainer container, int delta) {
		//Iterator<Entity> iterator = entities.getAll();
	}
	
	public void render(GameContainer container, Graphics g) {
		//player.render(container, g);
	}
	
	private void renderEntitiesList(GameContainer container, Graphics g) {
		//Iterator<Entity> iterator = entities.getVisible(camera);
		
	}

	public Camera getCamera() {
		return camera;
	}
}