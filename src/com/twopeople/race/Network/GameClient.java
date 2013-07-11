package com.twopeople.race.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.07.13
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
public class GameClient {

    private Kryo kryo;
    public GameClient(EndPoint ipep)
    {
        Client client=new Client();
        kryo = ipep.getKryo();

    }


    public static void main(String[] args)
    {
        //Client client=new Client();
    }
}
