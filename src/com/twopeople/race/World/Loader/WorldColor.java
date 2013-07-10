package com.twopeople.race.World.Loader;

import org.newdawn.slick.Color;

import java.util.Dictionary;

/**
 * Created with podko_000.
 * User: podko_000
 * Date: 10.07.13
 * Time: 22:10
 * To change this template use File | Settings | File Templates.
 */
public class WorldColor {

    public static final Color borderBlock=new Color(255,0,0);
    public static final Color asteroid=new Color(0,255,0);

    public static Dictionary<Color, IWorldColorCommand> commands;
    static
    {
         commands.put(borderBlock, new BorderBlockCommand());
         commands.put(asteroid, new AsteroidCommand());
    }
}
