package com.twopeople.race.World.Loader;

import java.io.*;

/**
 * Created with podko_000.
 * User: podko_000
 * Date: 10.07.13
 * Time: 21:42
 * To change this template use File | Settings | File Templates.
 */
public class WorldMetaData implements Serializable {
    private String name;
    private int numPlayers;
    private int laps;
    private boolean haveLaps;

    public WorldMetaData() {

    }

    public static WorldMetaData load(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fs=new FileInputStream(fileName);
        ObjectInputStream os=new ObjectInputStream(fs);
        WorldMetaData wmd = (WorldMetaData)os.readObject();
        return wmd;
    }

    public void save(String fileName) throws IOException {
        FileOutputStream fs=new FileOutputStream(fileName);
        ObjectOutputStream os=new ObjectOutputStream(fs);
        os.writeObject(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public boolean haveLaps() {
        return haveLaps;
    }

    public void setHaveLaps(boolean haveLaps) {
        this.haveLaps = haveLaps;
    }
}
