package com.twopeople.race;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Art {
	public static final SpriteSheet ships = loadSprite("res/images/ships.png", 64, 64);

	public static SpriteSheet loadSprite(String file, int w, int h) {
		try {
			return new SpriteSheet(file, w, h);
		} catch (SlickException e) {

		}
		return null;
	}
}