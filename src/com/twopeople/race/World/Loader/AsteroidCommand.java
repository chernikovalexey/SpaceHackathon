package com.twopeople.race.World.Loader;

import com.twopeople.race.World.World;
import com.twopeople.race.entity.Asteroid;
import com.twopeople.race.entity.Entity;
import org.newdawn.slick.Color;

/**
 * Created with podko_000.
 * User: podko_000
 * Date: 10.07.13
 * Time: 22:18
 * To change this template use File | Settings | File Templates.
 */
public class AsteroidCommand implements IWorldColorCommand {

    @Override
    public void execute(World world, int px, int py, Color color) {
        Asteroid asteroid = new Asteroid(world, px * World.TILE_SIZE, py * World.TILE_SIZE);
        //todo: set random vector of movement to ASTEROID
        //world.addEntity(asteroid);
    }
}
