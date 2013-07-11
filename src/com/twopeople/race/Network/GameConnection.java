package com.twopeople.race.Network;

import com.twopeople.race.World.World;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.07.13
 * Time: 17:46
 * To change this template use File | Settings | File Templates.
 */
public abstract class GameConnection {
    protected World _world;

    public World getWorld() {
        return _world;
    }
}
