package com.twopeople.race.entity.Unit;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Triangulator;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.CollisionType;
import com.twopeople.race.entity.EntityGridVault;
import com.twopeople.race.entity.MoveableEntity;

public class Player extends MoveableEntity {
	private Turret turret;

	public Player(World world, float x, float y) {
		super(world, x, y, 64, 64);

		setCollisionType(CollisionType.All);
		setFriction(.032f);
		setMaxSpeed(0.15f);
		setCameraOwner(true);

		turret = new Turret(world, this);
	}

	@Override
	public boolean isComplexFigure() {
		return true;
	}

	@Override
	public Shape[] getBBSkeleton() {
		Rectangle r = new Rectangle((int) x, (int) y, w, h);
		r.transform(Transform.createRotateTransform((float) Math.toRadians(angle)));
		Triangulator tr = r.getTriangles();
		Polygon[] polygons = new Polygon[tr.getTriangleCount()];
		float[] v1, v2, v3;
		for (int i = 0; i < tr.getTriangleCount(); ++i) {
			v1 = tr.getTrianglePoint(i, 0);
			v2 = tr.getTrianglePoint(i, 1);
			v3 = tr.getTrianglePoint(i, 2);
			polygons[i] = new Polygon(new float[] { v1[0], v1[1], v2[0], v2[1], v3[0], v3[1] });
		}
		return polygons;
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
			speed += 0.275f;
		}
		if (input.isKeyDown(Input.KEY_S)) {
			speed -= 0.325f;
		}

		direction.set((float) Math.cos(Math.toRadians(angle + 90)), (float) Math.sin(Math.toRadians(angle + 90)));

		super.update(container, delta, vault);
		
		turret.update(container, delta, vault);
		turret.updateDirection(input.getMouseX(), input.getMouseY());
	}

	@Override
	public void render(GameContainer container, Graphics g, Camera camera) {
		Image image = Art.ships.getSprite(0, 0);
		image.setCenterOfRotation(w / 2, h / 2);
		image.rotate(angle);
		image.draw(camera.getScreenX(x), camera.getScreenY(y));

		turret.render(container, g, camera);

		g.setColor(new Color(255, 255, 255, 125));
		for (Shape shape : getBBSkeleton()) {
			g.fill(shape);
		}
	}
}