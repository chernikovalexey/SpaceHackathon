package com.twopeople.race.entity.Unit;

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
import com.twopeople.race.entity.AnimatedEntity;
import com.twopeople.race.entity.CollisionType;
import com.twopeople.race.entity.EntityGridVault;

public class Player extends AnimatedEntity {
	public String name;
    public Turret turret;
	private boolean controllable = false;

	public Player() {
	}

	public Player(World world, float x, float y) {
		super(world, x, y, 64, 64);

		setCollisionType(CollisionType.All);
		setFriction(.032f);
		setMaxSpeed(0.55f);
		setCameraOwner(true);
		setAnimation(Art.ships, 0, 0, 0, 4);
		turret = new Turret(world, this);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isControllable() {
		return controllable;
	}

	public void setControllable(boolean ctrl) {
		this.controllable = ctrl;
	}

	@Override
	public boolean isComplexFigure() {
		return true;
	}

	@Override
	public Shape[] getBBSkeleton() {
		Rectangle r = new Rectangle(x, y + 5 + 15, w, h - 10 - 15);
		Transform transformation = Transform.createRotateTransform((float) Math.toRadians(angle), r.getCenterX(),
				r.getCenterY());
		Triangulator tr = r.getTriangles();
		Polygon[] polygons = new Polygon[tr.getTriangleCount() + 1];
		float[] v1, v2, v3;
		for (int i = 0; i < tr.getTriangleCount(); ++i) {
			v1 = tr.getTrianglePoint(i, 0);
			v2 = tr.getTrianglePoint(i, 1);
			v3 = tr.getTrianglePoint(i, 2);
			polygons[i] = new Polygon(new float[] { v1[0], v1[1], v2[0], v2[1], v3[0], v3[1] });
			polygons[i] = (Polygon) polygons[i].transform(transformation);
		}
		Polygon nosePoly = new Polygon(new float[] { x, y + 5 + 15, x + w, y + 5 + 15, x + w / 2, y + 5 });
		nosePoly = (Polygon) nosePoly.transform(transformation);
		polygons[polygons.length - 1] = nosePoly;
		return polygons;
	}

	@Override
	public boolean rotatable() {
		return true;
	}

	@Override
	public float[] getRotationCenter() {
		return new float[] { w / 2, h / 2 + 5 };
	}

	@Override
	public void update(GameContainer container, int delta, EntityGridVault vault) {
		Input input = container.getInput();

		float k = 1f;

		if (input.isKeyDown(Input.KEY_A)) {
			rotate(getRealSpeed() / (-delta * k));
		}
		if (input.isKeyDown(Input.KEY_D)) {
			rotate(getRealSpeed() / (delta * k));
		}
		if (input.isKeyDown(Input.KEY_W)) {
			speed += 0.175f;
		}
		if (input.isKeyDown(Input.KEY_S)) {
			speed -= 0.325f;
		}

		direction.set((float) Math.cos(Math.toRadians(angle + 90)), (float) Math.sin(Math.toRadians(angle + 90)));

		super.update(container, delta, vault);

		turret.update(container, delta, vault);
		turret.updateDirection(world.getCamera().getAbsoluteX(input.getMouseX()),
				world.getCamera().getAbsoluteY(input.getMouseY()));
	}

	@Override
	public void render(GameContainer container, Graphics g, Camera camera) {
		// super.render(container, g, camera);
		Image image = Art.ships.getSprite(0, 0);
		image.setCenterOfRotation(w / 2, h / 2 + 5);
		image.rotate(angle);
		g.setAntiAlias(true);
		g.drawImage(image, camera.getScreenX(x), camera.getScreenY(y));

		turret.render(container, g, camera);

		// g.setColor(new Color(255, 255, 255, 125));
		// for (Shape shape : getBBSkeleton()) {
		// g.fill(shape);
		// }
	}
}