package com.twopeople.race.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;

import java.util.ArrayList;

public class Entity {
	private static int entitySerial = 0;
	private static ArrayList<Entity> all = new ArrayList<>();

	public World world;

	public int id;
	public float x, y;
	public int w, h;
	public int cellX, cellY;

	public CollisionType collisionType = CollisionType.None;

	public float angle = 0f;
	public int health;
	public Entity owner;
	public Entity parent;

	protected boolean remove = false;

	public Vector2f direction = new Vector2f(0, 0);

	public Entity() {
	}

	public Entity(World world, float x, float y, int w, int h) {
		this.world = world;
		setCoordinates(x, y);
		setSize(w, h);
		this.id = entitySerial++;
		all.add(this);
	}

	public void setCoordinates(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(int w, int h) {
		this.w = w;
		this.h = h;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}

	public Entity getOwner() {
		return owner;
	}

	public void bindToParent(Entity e) {
		this.parent = e;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getAngle() {
		return this.angle;
	}

	public void setCell(int cx, int cy) {
		this.cellX = cx;
		this.cellY = cy;
	}

	public void setCollisionType(CollisionType type) {
		this.collisionType = type;
	}

	public int getCellX() {
		return cellX;
	}

	public int getCellY() {
		return cellY;
	}

	public boolean shouldRemove() {
		return remove;
	}

	public boolean rotatable() {
		return false;
	}

	public float getCenterX() {
		return x + w / 2;
	}

	public float getCenterY() {
		return y + h / 2;
	}

	public float[] getRotationCenter() {
		return new float[] { w / 2, h / 2 };
	}

	public void rotate(float angle) {
		this.angle += angle;
	}

	public boolean isComplexFigure() {
		return false;
	}

	public Shape getBounds() {
		return new Rectangle(x, y, w, h);
	}

	public Shape[] getBBSkeleton() {
		return new Shape[] { getBounds() };
	}

	public boolean collidesWith(Entity entity) {
		for (Shape shape1 : entity.getBBSkeleton()) {
			for (Shape shape2 : this.getBBSkeleton()) {
				if (shape1.intersects(shape2)) {
					return true;
				}
			}
		}
		return false;
	}

	public void ranOutsideWorld() {
		// remove = true;
	}

	public float getDistanceTo(Entity entity) {
		return (float) Math.sqrt(Math.pow(entity.x - x, 2.0) + Math.pow(entity.y - y, 2.0));
	}

	public void update(GameContainer container, int delta, EntityGridVault vault) {
		if (parent != null) {
			setCoordinates(parent.x, parent.y);
		}
	}

	public void render(GameContainer container, Graphics g, Camera camera) {
	}

	public static Entity getById(int id) {
		return all.get(id - 1);
	}
}