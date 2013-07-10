package com.twopeople.race.World;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.twopeople.race.entity.Entity;
import com.twopeople.race.entity.Unit.Player;

public class World {
	private Camera camera;

	private Entity player;

	public World(Camera camera) {
		this.camera = camera;
		this.player = new Player(this, 10, 10);
	}

	public void update(GameContainer container, int delta) {
		player.update(container, delta);
	}

	public void render(GameContainer container, Graphics g) {
		player.render(container, g);
	}

	public Camera getCamera() {
		return camera;
	}
}