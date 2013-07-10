package com.twopeople.race.World.Loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with podko_000.
 * User: podko_000
 * Date: 10.07.13
 * Time: 22:51
 * To change this template use File | Settings | File Templates.
 */
public class MetaCreator {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        WorldMetaData meta = new WorldMetaData();
        System.out.println("Name = ");
        meta.setName(in.readLine());

        System.out.println("NumPlayers = ");
        meta.setNumPlayers(Integer.parseInt(in.readLine()));


        System.out.println("HaveLaps = ");
        String s=in.readLine();
        meta.setHaveLaps(s.equals("true"));

        if(meta.haveLaps())
        {
            System.out.println("Laps = ");
            meta.setLaps(Integer.parseInt(in.readLine()));
        }

        System.out.println("Path = ");


        meta.save(in.readLine()+"\\meta.bin");

        System.out.println("Map saved");
    }
}
