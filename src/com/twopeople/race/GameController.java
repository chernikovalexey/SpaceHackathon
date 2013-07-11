package com.twopeople.race;

import com.twopeople.race.World.Camera;
import com.twopeople.race.World.Loader.WorldLoader;
import com.twopeople.race.World.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.twopeople.race.state.Game;

import java.io.IOException;

public class GameController extends StateBasedGame {
	public GameController() {
		super("Space Hackathon");
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
        try {
            new WorldLoader("E:\\Projects\\Java\\map", new World(new Camera(800,800)));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        addState(new Game());
		enterState(2);
	}
}