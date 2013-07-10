package com.twopeople.race.World.Loader;

import com.twopeople.race.World.World;
import com.twopeople.race.entity.Entity;

/**
 * Created with podko_000.
 * User: podko_000
 * Date: 10.07.13
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public interface IWorldColorCommand {
    public void execute(World world, int px, int py);
}
