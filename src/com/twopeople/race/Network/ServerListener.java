package com.twopeople.race.Network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.twopeople.race.entity.Unit.Player;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.07.13
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public class ServerListener extends Listener {

    private GameServer _server;
    public ServerListener(GameServer server)
    {
        _server = server;
    }


}
