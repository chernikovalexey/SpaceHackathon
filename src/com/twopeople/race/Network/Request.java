package com.twopeople.race.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.twopeople.race.entity.Unit.Player;

import java.lang.reflect.Field;

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


    public static Request connectionRequest(Player p) {
        Request r=new Request();
        r.nickName=p.getName();
        r.x=p.x;
        r.y=p.y;
        r.angle = p.getAngle();

        return r;
    }

    private static int _number=0;
    public static int Number() {
         _number++;
        return _number;
    }


    public class Type {
        public static final int Connection = 1;
    }

    public int rType;

    public byte[] write() {

        Output output=new Output(SIZE);
        output.writeInt(rType);
        switch (rType) {
            case Type.Connection:
                output.writeFloat(x);
                output.writeFloat(y);
                output.writeFloat(angle);
                output.writeString(nickName);
                break;
        }

        return output.getBuffer();
    }

    public void read(byte[] data) {
        Input input = new Input(data);
        rType = input.readInt();
        switch (rType) {
            case Type.Connection:
                x = input.readInt();
                y = input.readInt();
                angle = input.readFloat();
                nickName = input.readString();
                break;
        }
    }

    public String toString()
    {
        String result="";
        Field[] fs = getClass().getFields();
        for(Field f:fs)
        {
            try {
                result+=f.getName()+"="+f.get(this).toString()+" ";
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            catch (NullPointerException e)
            {

            }
        }

        return result;
    }

    public static void main(String[] args)
    {
        Request r=new Request();
        r.x=5;
        r.y=7;
        r.nickName="sdfsdf";
        System.out.println(r);
    }
}
