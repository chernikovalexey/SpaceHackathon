package com.twopeople.race.entity.Interior;

import com.twopeople.race.entity.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;

public class Star extends Entity {
    private int _starType;
    private float opacity = 1;
    private float kOpacity = 0.01f;

	public Star(World world, float x, float y) {
		super(world, x, y, 16, 16);


		setCollisionType(CollisionType.None);

        _starType = _getStarType();

		direction.x = (float) Math.cos(Math.toRadians(world.getRandom().nextInt(1440)));
		direction.y = (float) Math.sin(Math.toRadians(world.getRandom().nextInt(720)));


	}

    public static Star polar(World world, float x, float y)
    {
        Star s=new Star(world, x, y);
        s._starType = 4;
        return s;
    }

    private int _getStarType() {
        int r = world.getRandom().nextInt() % 100;
        if(r<70)
            return 0;
        else if(r<90)
            return 1;
        else if(r<97)
            return 2;
        else
            return 3;
    }


    public void update(GameContainer container, int delta, EntityGridVault vault) {
		super.update(container, delta, vault);



        if(kOpacity < 0 && opacity>0.5)
            kOpacity=world.getRandom().nextFloat()/50;
        else if(kOpacity > 0 && opacity>=1 || kOpacity<0 && opacity > 0.5)
            kOpacity= -world.getRandom().nextFloat()/50;



        opacity += kOpacity;
	}

	public void render(GameContainer container, Graphics g, Camera camera) {
		Image image = Art.stars.getSprite(_starType, 0);
        image.setAlpha(opacity);
		image.draw(camera.getScreenX(x), camera.getScreenY(y));
	}
}