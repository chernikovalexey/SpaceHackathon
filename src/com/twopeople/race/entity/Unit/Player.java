package com.twopeople.race.entity.Unit;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.CollisionType;
import com.twopeople.race.entity.EntityGridVault;
import com.twopeople.race.entity.MoveableEntity;

public class Player extends MoveableEntity {
	public Player(World world, float x, float y) {
		super(world, x, y, 64, 64);

		setCollisionType(CollisionType.All);
		setFriction(.022f);
		setMaxSpeed(0.09f);
		setCameraOwner(true);
	}

	@Override
	public Shape getBounds() {
		Rectangle rect  = new Rectangle(x, y, w, h);
		rect.transform(Transform.createRotateTransform((float)Math.toRadians(angle)));
		return rect;
	}

	@Override
	public void update(GameContainer container, int delta, EntityGridVault vault) {
		Input input = container.getInput();

		float k = 0.01f;

		if (input.isKeyDown(Input.KEY_A)) {
			rotate(getRealSpeed() / (-delta * k));
		}
		if (input.isKeyDown(Input.KEY_D)) {
			rotate(getRealSpeed() / (delta * k));
		}
		if (input.isKeyDown(Input.KEY_W)) {
			speed += 0.425f;
		}
		if (input.isKeyDown(Input.KEY_S)) {
			speed -= 0.625f;
		}

		direction.set((float) Math.cos(Math.toRadians(angle + 90)), (float) Math.sin(Math.toRadians(angle + 90)));

		super.update(container, delta, vault);
	}

	@Override
	public void render(GameContainer container, Graphics g, Camera camera) {
		Image image = Art.ships.getSprite(0, 0);
		image.setCenterOfRotation(w / 2, h / 2);
		image.rotate(angle);
		image.draw(camera.getScreenX(x), camera.getScreenY(y));
	}
}