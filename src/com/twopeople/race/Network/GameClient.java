package com.twopeople.race.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;
import com.sun.jmx.remote.internal.ClientListenerInfo;
import com.twopeople.race.World.World;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.07.13
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
public class GameClient extends GameConnection {

    private World world;
    private GameClient(String ip, World world)
    {
        this.world=world;
        Client client=new Client();
        try {
            client.connect(5000, ip, 4096, 8192);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        client.addListener(new ClientListener(this));
    }

    public GameClient(World world) {
        this.world=world;
        Server s=new Server();
        s.start();
        try {
            s.bind(4096, 8182);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        s.addListener(new ClientListener(this));
    }

    public static GameClient connect(String ip, World world)
    {
        return new GameClient(ip,world);
    }

    public static GameClient create(World world)
    {
        return new GameClient(world);
    }

    public static void main(String[] args)
    {
        //Client client=new Client();
    }
}
