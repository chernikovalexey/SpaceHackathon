package com.twopeople.race.Network;

import com.esotericsoftware.kryonet.Listener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.07.13
 * Time: 19:31
 * To change this template use File | Settings | File Templates.
 */
public class UdpClient implements Runnable {

    private DatagramSocket socket;
    private Thread thread;
    private IListener listener;
    private ArrayList<InetAddress> addresses = new ArrayList<InetAddress>();

    public UdpClient(int port) throws SocketException {
         socket=new DatagramSocket(port);

        thread=new Thread(this);
        thread.start();
    }

    public void send(Request r, InetAddress address) throws IOException {
        byte[] data = r.write();
        DatagramPacket p = new DatagramPacket(data, data.length);
        if(address!=null)
        {
            p.setAddress(address);
            socket.send(p);
        }
        else
        {
            for(InetAddress addr: addresses)
            {
                p.setAddress(addr);
                socket.send(p);
            }
        }
    }

    public void addListener(IListener listener)
    {
        this.listener=listener;
    }

    @Override
    public void run() {
        DatagramPacket packet=new DatagramPacket(new byte[Request.SIZE],1);
        try {
            socket.receive(packet);
            Request r=new Request();
            r.read(packet.getData(), listener);
            listener.received(packet.getAddress(), r);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void stop()
    {
        thread.stop();
    }
}
