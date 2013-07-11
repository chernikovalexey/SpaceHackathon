package com.twopeople.race.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;

public class Game extends BasicGameState {
	private World world;
	private Camera camera;

	private void startGame(GameContainer container) {
		camera = new Camera(container.getWidth(), container.getHeight());
		world = new World(camera);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		startGame(container);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		startGame(container);
	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int delta) throws SlickException {
		camera.update(delta);
		world.update(container, delta);
	}

	@Override
	public void render(GameContainer container, StateBasedGame arg1, Graphics g) throws SlickException {
		world.render(container, g);
	}

	@Override
	public int getID() {
		return 2;
	}
}