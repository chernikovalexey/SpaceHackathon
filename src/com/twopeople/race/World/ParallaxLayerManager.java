package com.twopeople.race.World;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 12.07.13
 * Time: 4:27
 * To change this template use File | Settings | File Templates.
 */
public class ParallaxLayerManager {
    private ArrayList<ParallaxLayer> layers = new ArrayList<>();

    public ParallaxLayerManager(World world) {
        ParallaxLayer.generate(world, layers);
    }

    public void update(GameContainer container, int delta) {
        for (ParallaxLayer p : layers) {
            p.update(container, delta);
        }
    }

    public void renderUp(GameContainer container, Graphics g, Camera wc) {
        for (ParallaxLayer p : layers)
            if (p.getDistanceMode() > 1)
                p.render(container, g, wc);
    }

    public void renderDown(GameContainer container, Graphics g, Camera wc) {
        for (ParallaxLayer p : layers)
            if (p.getDistanceMode() < 1)
                p.render(container, g, wc);
    }
}
