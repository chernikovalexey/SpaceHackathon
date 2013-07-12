package com.twopeople.race.entity.Interior;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.CollisionType;
import com.twopeople.race.entity.Entity;
import com.twopeople.race.entity.EntityGridVault;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: podko_000
 * Date: 12.07.13
 * Time: 3:51
 * To change this template use File | Settings | File Templates.
 */
public class CosmoDust extends Entity{

    private int _dustType;
    public CosmoDust(World world, float x, float y)
    {
        super(world, x, y, 512, 512);
        setCollisionType(CollisionType.None);

        _dustType = Math.abs(world.getRandom().nextInt() % 4);

        direction.x = (float) Math.cos(Math.toRadians(world.getRandom().nextInt(1440)));
        direction.y = (float) Math.sin(Math.toRadians(world.getRandom().nextInt(720)));
    }


    public void update(GameContainer container, int delta, EntityGridVault vault) {
        super.update(container, delta, vault);
    }

    public void render(GameContainer container, Graphics g, Camera camera) {
        Image image = Art.cosmoDust.getSprite(_dustType, 0);
        image.setAlpha(0.3f);
        image.draw(camera.getScreenX(x), camera.getScreenY(y));
    }
}
