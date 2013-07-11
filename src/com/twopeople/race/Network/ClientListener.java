package com.twopeople.race.Network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.Unit.Player;

import java.net.Inet4Address;
import java.net.InetAddress;


/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.07.13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public class ClientListener implements IListener {

    private GameConnection _connection;
    public ClientListener(GameConnection c)
    {
        _connection=c;
    }

    public void received(InetAddress address,Request request)
    {
        Request r=(Request)request;
        System.out.println("ip=" + address + " Request #" + Request.Number() + " " + request.toString());
        switch (r.rType)
        {
            case Request.Type.Connection:
                _connection(r);
                break;
        }

    }

    @Override
    public World getWorld() {
        return _connection.getWorld();
    }

    private void _connection(Request request) {
        Player p=new Player(_connection.getWorld(), request.x, request.y);
        p.setAngle(request.angle);
        p.setName(request.nickName);
        _connection.getWorld().playerConnects(p);
    }


    public void idle(Connection connection)
    {
        System.out.println("idle");
    }
}
