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
    public static final Color asteroid = new Color(0, 255, 0);
    public static final Color zero = Color.black;

    public static HashMap<Color, IWorldColorCommand> commands;

    static {
        commands=new HashMap<Color, IWorldColorCommand>();
        commands.put(L_BORDER_BLOCK, new BorderBlockCommand());
        commands.put(R_BORDER_BLOCK, new BorderBlockCommand());
        commands.put(asteroid, new AsteroidCommand());
        commands.put(zero, new ZeroCommand());
    }
}
