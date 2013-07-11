package com.twopeople.race.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.Asteroid;
import com.twopeople.race.entity.Entity;
import com.twopeople.race.entity.Interior.BorderBlock;
import com.twopeople.race.entity.MoveableEntity;
import com.twopeople.race.entity.Unit.Player;
import com.twopeople.race.entity.Unit.Turret;
import org.newdawn.slick.geom.Vector2f;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 11.07.13
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */

public class Request {

    public static final int SIZE = 20000;
    public String nickName;
    public float x, y, angle;
    public ArrayList<Asteroid> asteroids;
    public ArrayList<BorderBlock> sortedBlocks;
    public ArrayList<Player> players;
    public ArrayList<InetSocketAddress> connections;

    public static Request connectionRequest(Player p) {
        Request r = new Request();
        r.nickName = p.getName();
        r.x = p.x;
        r.y = p.y;
        r.angle = p.getAngle();

        return r;
    }

    private static int _number = 0;

    public static int Number() {
        _number++;
        return _number;
    }


    public class Type {
        public static final int Connection = 1;
        public static final int WorldData = 2;
    }

    public int rType;

    public byte[] write() {

        Output output = new Output(SIZE);
        output.writeInt(rType);
        switch (rType) {
            case Type.Connection:
                output.writeFloat(x);
                output.writeFloat(y);
                output.writeFloat(angle);
                output.writeString(nickName);
                break;
            case Type.WorldData:
                _writeConnections(connections, output);
                _writePlayers(players, output);
                _writeAsteroids(asteroids, output);
                _writeBorders(sortedBlocks, output);
                break;
        }

        return output.getBuffer();
    }

    public void read(byte[] data, IListener listener) throws UnknownHostException {
        Input input = new Input(data);
        rType = input.readInt();
        switch (rType) {
            case Type.Connection:
                x = input.readInt();
                y = input.readInt();
                angle = input.readFloat();
                nickName = input.readString();
                break;
            case Type.WorldData:

                _readConnections(input);
                _readPlayers(listener.getWorld(), input);
                _readAsteroids(listener.getWorld(), input);
                _readBorders(listener.getWorld(), input);
                break;
        }
    }

    private void _writeConnections(ArrayList<InetSocketAddress> connections, Output output) {
        output.writeInt(connections.size());

        for (InetSocketAddress a : connections) {
            byte[] bData = a.getAddress().getAddress();
            output.writeInt(bData.length);
            output.write(bData);
            output.write(a.getAddress().getAddress());
            output.writeInt(a.getPort());
        }
    }

    private void _readConnections(Input input) throws UnknownHostException {
        int size = input.readInt();
        connections = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            connections.add(new InetSocketAddress(InetAddress.getByAddress(input.readBytes(input.readInt())), input.readInt()));
        }
    }

    private void _writeAsteroids(ArrayList<Asteroid> a, Output output) {
        output.write(a.size());
        for (Asteroid asteroid : a) {
            _writeEntity(asteroid, output);
            _writeMoveableSpecial(asteroid, output);
            _writeAsteroidSpecial(asteroid, output);
        }
    }

    private void _readAsteroids(World w,Input input) {
        asteroids = new ArrayList<>();
        int size = input.readInt();
        for (int i = 0; i < size; i++) {
            Asteroid a = new Asteroid();
            _readEntity(a, input);
            _readMoveableSpecial(a, input);
            _readAsteroidSpecial(a, input);
            a.world=w;
            asteroids.add(a);
        }
    }

    private void _writePlayers(ArrayList<Player> players, Output output) {
        output.writeInt(players.size());

        for (Player p : players) {
            _writeEntity(p, output);
            _writeMoveableSpecial(p, output);
            _writePlayerSpec(p, output);
        }
    }

    private void _readPlayers(World w, Input input) {
        int size = input.readInt();
        players = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Player p = new Player();
            _readEntity(p, input);
            _readMoveableSpecial(p, input);
            _readPlayerSpec(p, input);
            p.world=w;
            players.add(p);
        }
    }

    private void _writeBorders(ArrayList<BorderBlock> blocks, Output output) {
        output.writeInt(blocks.size());

        for (BorderBlock b : blocks) {
            _writeEntity(b, output);
            output.writeBoolean(b.position == BorderBlock.BlockPosition.Left);
        }
    }

    private void _readBorders(World w, Input input) {
        sortedBlocks = new ArrayList<BorderBlock>();
        int size = input.readInt();
        for (int i = 0; i < size; i++) {
            BorderBlock b = new BorderBlock();
            _readEntity(b, input);
            if (input.readBoolean())
                b.position = BorderBlock.BlockPosition.Left;
            else
                b.position = BorderBlock.BlockPosition.Right;

            b.world = w;
            sortedBlocks.add(b);
        }
    }

    private void _writePlayerSpec(Player p, Output output) {
        _writeEntity(p.turret, output);
        output.writeString(p.getName());
    }

    private void _readPlayerSpec(Player p, Input input) {
        p.turret = new Turret();
        _readEntity(p.turret, input);
        p.name = input.readString();
    }

    private void _writeMoveableSpecial(MoveableEntity entity, Output output) {
        output.writeFloat(entity.friction);
        output.writeFloat(entity.speed);
        output.writeFloat(entity.maxSpeed);

        _writeVector(entity.velocity, output);
        _writeVector(entity.acceleration, output);
    }

    private void _readMoveableSpecial(MoveableEntity movable, Input input) {
        movable.friction = input.readFloat();
        movable.speed = input.readFloat();
        movable.maxSpeed = input.readFloat();

        movable.velocity = _readVector(input);
        movable.acceleration = _readVector(input);
    }

    private void _writeVector(Vector2f v, Output output) {
        output.writeFloat(v.getX());
        output.writeFloat(v.getY());
    }

    private Vector2f _readVector(Input input) {
        return new Vector2f(input.readFloat(), input.readFloat());
    }

    private void _writeAsteroidSpecial(Asteroid asteroid, Output output) {
        output.writeInt(asteroid.asteroidType);
        output.writeFloat(asteroid.rotationSpeed);
    }

    private void _readAsteroidSpecial(Asteroid a, Input input) {
        a.asteroidType = input.readInt();
        a.rotationSpeed = input.readInt();
    }

    private void _writeEntity(Entity e, Output output) {
        output.writeInt(e.id);
        output.writeFloat(e.x);
        output.writeFloat(e.y);
        output.writeFloat(e.angle);
        output.writeInt(e.health);
        _writeVector(e.direction, output);

        if (e.owner != null)
            output.writeInt(e.owner.id);
        else
            output.writeInt(0);


        if (e.parent != null)
            output.writeInt(e.parent.id);
        else
            output.writeInt(0);
    }


    private void _readEntity(Entity e, Input input) {
        e.id = input.readInt();
        e.x = input.readFloat();
        e.y = input.readFloat();
        e.angle = input.readFloat();
        e.health = input.readInt();
        e.direction = _readVector(input);
        int id = input.readInt();
        if (id != 0)
            e.owner = Entity.getById(id);

        id = input.readInt();

        if (id != 0)
            e.owner = Entity.getById(id);


        input.readInt();
    }


    public String toString() {
        String result = "";
        Field[] fs = getClass().getFields();
        for (Field f : fs) {
            try {
                result += f.getName() + "=" + f.get(this).toString() + " ";
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (NullPointerException e) {

            }
        }

        return result;
    }

    public static void main(String[] args) {
        Request r = new Request();
        r.x = 5;
        r.y = 7;
        r.nickName = "sdfsdf";
        System.out.println(r);
    }
}
