package com.twopeople.race.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;
import com.sun.jmx.remote.internal.ClientListenerInfo;
import com.twopeople.race.GameController;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.07.13
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
public class GameClient extends GameConnection{

    private Thread thread;
    private UdpClient client;

    private final int TCP_PORT = 27015;
    private final int UDP_PORT = 27016;
    private GameClient(String ip, World world) throws IOException {

        _init(world);
        _connect(ip);
    }

    private void _connect(String ip) throws IOException {
        client.send(Request.connectionRequest(_world.getControllablePlayer()), InetAddress.getByName(ip));
    }

    private void _init(World world) throws SocketException
    {
        this._world=world;
        try {
            client=new UdpClient(27015);
        } catch (SocketException e) {
            client=new UdpClient(27016);
        }
    }

    private GameClient(World world) throws SocketException {
        _init(world);
    }

    public static GameClient connect(String ip, World world) throws IOException {
        return new GameClient(ip,world);
    }

    public static GameClient create(World world) throws SocketException {
        return new GameClient(world);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer game = new AppGameContainer(new GameController());

    }
}
