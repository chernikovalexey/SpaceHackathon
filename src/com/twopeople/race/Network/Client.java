package com.twopeople.race.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.07.13
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
public class Client {
    public Client()
    {
        Server s=new Server();
        try {
            s.bind(20000, 20001);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        Kryo k = s.getKryo();
        k.register(String.class);

    }


    public static void main(String[] args)
    {
        Client client=new Client();
    }
}
