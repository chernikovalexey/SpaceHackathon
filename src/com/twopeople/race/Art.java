package com.twopeople.race;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Art {
	public static final SpriteSheet ships = loadSprite("res/images/ships.png", 64, 64);
	public static final SpriteSheet asteroids = loadSprite("res/images/asteroids.png", 128, 128);
	public static final SpriteSheet stars = loadSprite("res/images/stars.png", 16, 16);
	public static final SpriteSheet track = loadSprite("res/images/track.png", 16, 16);
	public static final SpriteSheet explosions = loadSprite("res/images/explosions.png", 96, 96);

	public static SpriteSheet loadSprite(String file, int w, int h) {
		try {
			return new SpriteSheet(file, w, h);
		} catch (SlickException e) {

		}
		return null;
	}
}