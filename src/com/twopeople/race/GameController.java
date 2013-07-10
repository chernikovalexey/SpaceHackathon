package com.twopeople.race;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.twopeople.race.state.Game;

public class GameController extends StateBasedGame {
	public GameController() {
		super("Space Hackathon");
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new Game());
		enterState(2);
	}
}