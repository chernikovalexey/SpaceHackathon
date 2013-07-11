package com.twopeople.race.Network;

import com.twopeople.race.World.World;

import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.07.13
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public interface IListener {
    public void received(InetAddress address,Request data);
    public World getWorld();
}
