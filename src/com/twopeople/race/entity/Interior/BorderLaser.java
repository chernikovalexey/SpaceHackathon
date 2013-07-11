package com.twopeople.race.entity.Interior;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Triangulator;
import org.newdawn.slick.geom.Vector2f;

import com.twopeople.race.Art;
import com.twopeople.race.World.Camera;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.Entity;
import com.twopeople.race.entity.EntityGridVault;

public class BorderLaser extends Entity {
	private Entity from, to;
	private int skippedTicks = 0;
	private int alpha;
	private int minAlpha = 10;
	private int maxAlpha = 165;
	private boolean increasing = true;

	public BorderLaser(World world, Entity from, Entity to) {
		super(world, to.x, to.y, (int) (to.x - from.x), 1);
		this.from = from;
		this.to = to;
		alpha = minAlpha;
	}

	@Override
	public Shape getBounds() {
		return new Line(from.getCenterX(), from.getCenterY(), to.getCenterX(), to.getCenterY());
	}

	public void update(GameContainer container, int delta, EntityGridVault vault) {
		if (skippedTicks++ > 1) {
			skippedTicks = 0;
			if (increasing) ++alpha;
			else --alpha;
			if (alpha > maxAlpha) {
				increasing = false;
			} else if(alpha < minAlpha) {
				increasing = true;
			}
		}
	}

	public void render(GameContainer container, Graphics g, Camera camera) {
		g.setColor(new Color(0, 126, 255, 255));
		g.setAntiAlias(true);
		g.drawLine(camera.getScreenX(from.getCenterX()), camera.getScreenY(from.getCenterY()),
				camera.getScreenX(to.getCenterX()), camera.getScreenY(to.getCenterY()));
		g.setColor(new Color(0, 126, 255, alpha));
		g.drawLine(camera.getScreenX(from.getCenterX() - 1), camera.getScreenY(from.getCenterY() - 1),
				camera.getScreenX(to.getCenterX() - 1), camera.getScreenY(to.getCenterY() - 1));
		g.drawLine(camera.getScreenX(from.getCenterX() + 1), camera.getScreenY(from.getCenterY() + 1),
				camera.getScreenX(to.getCenterX() + 1), camera.getScreenY(to.getCenterY() + 1));
	}
}