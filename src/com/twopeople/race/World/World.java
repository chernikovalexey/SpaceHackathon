package com.twopeople.race.World;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.twopeople.race.World.Loader.WorldLoader;
import com.twopeople.race.World.Loader.WorldMetaData;
import com.twopeople.race.entity.Asteroid;
import com.twopeople.race.entity.Entity;
import com.twopeople.race.entity.EntityGridVault;
import com.twopeople.race.entity.StartPoint;
import com.twopeople.race.entity.Interior.BorderBlock;
import com.twopeople.race.entity.Interior.Star;
import com.twopeople.race.entity.Unit.Player;

public class World {
	private Camera camera;
	private Random random = new Random();


    private int width, height;
	private ArrayList<StartPoint> startPoints = new ArrayList<StartPoint>();
	private EntityGridVault background;
	private EntityGridVault entities;
	private ArrayList<Player> playersPtr = new ArrayList<Player>();
	private EntityGridVault borders;

	public static final int TILE_SIZE = 16;

	private WorldMetaData metaData;

	public World(Camera camera) {
		this.camera = camera;
		this.background = new EntityGridVault(256, 256, 6, 6);
		this.entities = new EntityGridVault(64, 64, 12, 12);
		this.borders = new EntityGridVault(256, 256, 6, 6);

		WorldLoader.load("res/maps/map1", this);

		entities.add(new Player(this, 250, 250));
		entities.add(new Asteroid(this, 330, 280));
		for (int x = 0; x < 256 * 6; x += 10) {
			for (int y = 0; y < 256 * 6; y += 16) {
				if (random.nextInt(128) % 2 == 0) {
					// background.add(new Star(this, x, y));
				}
			}
		}
	}

    public void addStartPoint(StartPoint point)
    {
         startPoints.add(point);
    }

	public void setMetaData(WorldMetaData data) {
		this.metaData = data;
	}

	public void update(GameContainer container, int delta) {
		updateEntitiesList(background, container, delta, true);
		updateEntitiesList(entities, container, delta, false);
		updateEntitiesList(borders, container, delta, true);
	}

	private void updateEntitiesList(EntityGridVault vault, GameContainer container, int delta, boolean vis) {
		Iterator<Entity> iterator = vis ? vault.getVisible(camera).iterator() : vault.getAll().iterator();
		while (iterator.hasNext()) {
			Entity e = iterator.next();
			e.update(container, delta, vault);
			if (e.shouldRemove()) {
				vault.remove(e);
				if (e instanceof Player) {
					playersPtr.remove(e);
				}
			}
		}
	}

	public void render(GameContainer container, Graphics g) {
		renderEntitiesList(background, container, g);
		renderEntitiesList(entities, container, g);
		renderEntitiesList(borders, container, g);

		g.drawString("entities: " + entities.size(), 10, 30);
		g.drawString("borders: " + borders.size(), 10, 50);
	}

	private void renderEntitiesList(EntityGridVault vault, GameContainer container, Graphics g) {
		Iterator<Entity> iterator = vault.getVisible(camera).iterator();
		while (iterator.hasNext()) {
			Entity e = iterator.next();
			e.render(container, g, camera);
		}
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void addPlayer(Player p) {
		entities.add(p);
		playersPtr.add(p);
	}

	public void addBorder(BorderBlock border) {
		borders.add(border);
	}

	public Camera getCamera() {
		return camera;
	}

	public Random getRandom() {
		return random;
	}

	public EntityGridVault getBorders() {
		return borders;
	}

	public ArrayList<Entity> getCollidableEntities(Entity entity) {
		return entities.getCollidables(entity);
	}

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}