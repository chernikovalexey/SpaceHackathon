package com.twopeople.race.World.Loader;

import com.twopeople.race.World.World;
import org.newdawn.slick.Color;
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

    public WorldLoader(String dirName, World world) throws IOException, ClassNotFoundException {
        metaData = WorldMetaData.load(dirName + "\\meta.bin");
        _world=world;
        _parseImage(dirName + "\\data.png");
    }

    private void _parseImage(String fileName) throws IOException {
        FileInputStream fs = new FileInputStream(fileName);
        Texture t = TextureLoader.getTexture("PNG", fs);

        Color pixelColor;
        for(int i=0;i<t.getImageWidth();i++)
            for(int j=0;j<t.getImageHeight();j++)
                WorldColor.commands.get(_getPixelColor(t,i,j)).execute(_world, i, j);
    }

    private byte[] data;

    private Color _getPixelColor(Texture texture, int x, int y) {
        if (data == null)
            data = texture.getTextureData();
        byte r = data[4 * y * texture.getImageWidth() + x];
        byte g = data[4 * y * texture.getImageWidth() + x + 1];
        byte b = data[4 * y * texture.getImageWidth() + x + 2];
        byte a = data[4 * y * texture.getImageWidth() + x + 3];

        return new Color(r,g,b,a);
    }

    public WorldMetaData getMetaData() {
        return metaData;
    }
}
