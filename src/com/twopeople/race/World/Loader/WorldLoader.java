package com.twopeople.race.World.Loader;

import com.twopeople.race.World.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created with podko_000.
 * User: podko_000
 * Date: 10.07.13
 * Time: 21:41
 * To change this template use File | Settings | File Templates.
 */
public class WorldLoader {
    private WorldMetaData metaData;
    private World _world;

    public WorldLoader(String dirName, World world) throws IOException, ClassNotFoundException, SlickException {
        metaData = WorldMetaData.load(dirName + "\\meta.bin");
        _world=world;
        _parseImage(dirName + "\\data.png");
    }

    private void _parseImage(String fileName) throws IOException, SlickException {

        Image img=new Image(fileName);
        Color pixelColor;
        for(int i=0;i<img.getWidth();i++)
            for(int j=0;j<img.getHeight();j++)
            {
                pixelColor=img.getColor(i, j);
                WorldColor.commands.get(pixelColor).execute(_world, i, j, pixelColor);
            }
    }

    private byte[] data;
    public WorldMetaData getMetaData() {
        return metaData;
    }
}
