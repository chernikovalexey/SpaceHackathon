package com.twopeople.race.Network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.twopeople.race.entity.Unit.Player;


/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.07.13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public class ClientListener extends Listener {

    private GameConnection _connection;
    public ClientListener(GameConnection c)
    {
        _connection=c;
    }

    @Override public void connected(Connection connection)
    {
        System.out.println("Player connected to server");
        connection.sendUDP(Request.connectionRequest(_connection.getWorld().getControllablePlayer()));
    }

    @Override
    public void received(Connection connection, Object request)
    {
        Request r=(Request)request;
        switch (r.rType)
        {
            case Request.Type.Connection:
                _connection(r);
                break;
        }

    }

    private void _connection(Request request) {
        Player p=new Player(_connection.getWorld(), request.x, request.y);
        p.setAngle(request.angle);
        p.setName(request.nickName);
        _connection.getWorld().playerConnects(p);
    }
}
