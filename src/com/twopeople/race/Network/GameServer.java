package com.twopeople.race.Network;

import com.esotericsoftware.kryonet.Server;
import com.twopeople.race.World.World;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.07.13
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
public class GameServer extends GameConnection {
    private Server server;

    public GameServer(World w)
    {
        _world=w;
        server = new Server();
        server.start();
        try {
            server.bind(4096, 8182);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        server.addListener(new ServerListener(this));
    }

    public World getWorld() {
        return _world;
    }
}
