package com.2people.race;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class GameStartup {
	public static void main(String[] args) {
		try {
			AppGameContainer game = new AppGameContainer(new GameController());
			game.setDisplayMode(800, 600, false);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}