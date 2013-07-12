package com.twopeople.race.World;

import com.twopeople.race.entity.Interior.CosmoDust;
import com.twopeople.race.entity.Interior.Star;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 12.07.13
 * Time: 1:49
 * To change this template use File | Settings | File Templates.
 */
public class ParallaxLayer {
    private ArrayList<Star> stars = new ArrayList<>();
    private ArrayList<CosmoDust> dust = new ArrayList<>();
    private World world;
    private double distanceMode;
    private static Random random = new Random();
    private Camera camera;

    public int width, height;


    public ParallaxLayer(World world, float distanceMode) {
        camera = new Camera(world.getCamera().getScreenWidth(), world.getCamera().getScreenHeight());
        this.world = world;
        this.distanceMode = distanceMode;
        width = (int) (world.getWidth() * distanceMode);
        height = (int) (world.getHeight() * distanceMode);
        if(distanceMode < 1)
            _generateStars();
        _generateDust();
    }

    public double getDistanceMode()
    {
        return distanceMode;
    }

    private void _generateDust() {
        int size = 1;// (int) (random.nextInt() % ((width * height / World.TILE_SIZE / World.TILE_SIZE) / 40000f) + 0.0001 * width * height / World.TILE_SIZE / World.TILE_SIZE);

        for (int i = 0; i < size; i++) {
            dust.add(new CosmoDust(world, (random.nextInt() % width), (float) (random.nextInt() % width)));
        }

    }

    private void _generateStars() {
        int size = 5;
        for (int i = 0; i < size; i++)
            stars.add(_createStar());
    }

    private Star _createStar() {
        return new Star(world, (float) (random.nextInt() % width), (float) (random.nextInt() % width));
    }

    public static void generate(World world, ArrayList<ParallaxLayer> layers) {
        float k = 0.6f;
        int width = (int) (world.getWidth() * k);
        int height = (int) (world.getHeight() * k);
        while (width > world.getCamera().getScreenWidth() && height > world.getCamera().getScreenHeight()) {
            layers.add(new ParallaxLayer(world, k));
            k -= 0.06;
            width = (int) (world.getWidth() * k);
            height = (int) (world.getHeight() * k);
        }

        float zoom = ((float) world.getCamera().getScreenWidth() + 200) / world.getWidth();
        ParallaxLayer p = new ParallaxLayer(world, zoom);
        p.stars.clear();
        p.stars.add(Star.polar(world, world.getCamera().getScreenWidth() / 3, world.getCamera().getScreenWidth() / 3));
        layers.add(p);


        //dddddddlayers.add(new ParallaxLayer(world, 1.1f));
        System.out.println(layers);
    }

    public void render(GameContainer container, Graphics g, Camera wc) {

        _configurateCamera(wc);
        for (Star s : stars)
            s.render(container, g, camera);

        for(CosmoDust d: dust)
            d.render(container, g, camera);
    }

    private void _configurateCamera(Camera wc) {
        float wWDelta = wc.getScreenWidth() - world.getWidth();
        float hWDelta = wc.getScreenHeight() - world.getHeight();

        float wLDelta = camera.getScreenWidth() - height;
        float hLDelta = camera.getScreenHeight() - width;

        float cx = wLDelta / wWDelta * (int) wc.getX();
        float cy = hLDelta / hWDelta * (int) wc.getY();


        camera.setX(cx);
        camera.setY(cy);
    }

    public void update(GameContainer container, int delta) {
        for (Star s : stars)
            s.update(container, delta, null);

        for (CosmoDust d : dust)
            d.update(container, delta, null);
    }
}
