package com.twopeople.race.World.Loader;

import com.esotericsoftware.kryonet.Server;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.StartPoint;
import org.newdawn.slick.Color;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.07.13
 * Time: 4:47
 * To change this template use File | Settings | File Templates.
 */
public class SpawnerCommand implements IWorldColorCommand {
    @Override
    public void execute(World world, int px, int py, Color color) {
        StartPoint sp=new StartPoint(world, px * World.TILE_SIZE, py* World.TILE_SIZE);

        world.addStartPoint(sp);
    }
}
