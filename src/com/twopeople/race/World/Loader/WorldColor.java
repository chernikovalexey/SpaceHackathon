package com.twopeople.race.World.Loader;

import org.newdawn.slick.Color;

import java.util.HashMap;

/**
 * Created with podko_000.
 * User: podko_000
 * Date: 10.07.13
 * Time: 22:10
 * To change this template use File | Settings | File Templates.
 */
public class WorldColor {

    public static final Color L_BORDER_BLOCK = new Color(128, 0, 0);
    public static final Color R_BORDER_BLOCK = new Color(255, 0, 0);
    public static final Color ASTEROID = new Color(0, 255, 0);
    public static final Color ZERO = Color.black;
    public static final Color SPAWNER = new Color(255,255,0);

    public static HashMap<Color, IWorldColorCommand> commands;

    static {
        commands=new HashMap<Color, IWorldColorCommand>();
        commands.put(L_BORDER_BLOCK, new BorderBlockCommand());
        commands.put(R_BORDER_BLOCK, new BorderBlockCommand());
        commands.put(ASTEROID, new AsteroidCommand());
        commands.put(ZERO, new ZeroCommand());
        commands.put(SPAWNER, new SpawnerCommand());
    }
}
