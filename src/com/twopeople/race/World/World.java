package com.twopeople.race.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.twopeople.race.World.Loader.WorldLoader;
import com.twopeople.race.World.Loader.WorldLoader.WorldSize;
import com.twopeople.race.World.Loader.WorldMetaData;
import com.twopeople.race.entity.Entity;
import com.twopeople.race.entity.EntityGridVault;
import com.twopeople.race.entity.Projectile;
import com.twopeople.race.entity.StartPoint;
import com.twopeople.race.entity.Interior.BorderBlock;
import com.twopeople.race.entity.Interior.BorderBlock.BlockPosition;
import com.twopeople.race.entity.Interior.BorderLaser;
import com.twopeople.race.entity.Unit.Player;

public class World {
	private Camera camera;
	private Random random = new Random();

	private ArrayList<StartPoint> startPoints = new ArrayList<StartPoint>();
	private EntityGridVault entities;
	private ArrayList<Player> playersPtr = new ArrayList<Player>();
	private ArrayList<Entity> borders = new ArrayList<Entity>();
	private EntityGridVault projectiles;
	private ArrayList<ParallaxLayer> layers = new ArrayList<>();

	public static final int TILE_SIZE = 16;
	private int width = 4540, height = 1980;

	private WorldMetaData metaData;

	public World(Camera camera) {
		this.camera = camera;

		WorldSize size = WorldLoader.preload("res/maps/map001");
		setWidth(size.width);
		setHeight(size.height);

		this.entities = new EntityGridVault(800, 600, width, height);
		this.projectiles = new EntityGridVault(256, 256, width, height);

		WorldLoader.load("res/maps/map001", this);

		camera.setWorldSize(width, height);
		Player player = new Player(this, 0, 0);
		player.setCoordinates(startPoints.get(0).x, startPoints.get(0).y);
		player.setControllable(true);
		addPlayer(player);

		Entity[] left = findBorderBlocks(BorderBlock.BlockPosition.Left);
		for (int i = 0; i < left.length; ++i) {
			if (i < left.length - 1) {
				addEntity(new BorderLaser(this, left[i], left[i + 1]));
			}
		}

		Entity[] right = findBorderBlocks(BorderBlock.BlockPosition.Right);
		for (int i = 0; i < right.length; ++i) {
			if (i < right.length - 1) {
				addEntity(new BorderLaser(this, right[i], right[i + 1]));
			}
		}

		ParallaxLayer.generate(this, layers);
	}

	private Entity[] findBorderBlocks(BlockPosition pos) {
		ArrayList<Entity> db = new ArrayList<Entity>();
		for (Entity block : borders) {
			BorderBlock b = (BorderBlock) block;
			if (b.position.equals(pos)) {
				db.add(b);
			}
		}
		Entity[] blocks = new Entity[db.size()];
		for (int i = 0; i < db.size(); ++i) {
			blocks[i] = db.get(i);
		}
		return blocks;
	}

	public void addStartPoint(StartPoint point) {
		startPoints.add(point);
	}

	public void setMetaData(WorldMetaData data) {
		this.metaData = data;
	}

	public void update(GameContainer container, int delta) {
		updateEntitiesGrid(entities, container, delta, false);
		updateEntitiesGrid(projectiles, container, delta, false);
		updateEntitiesList(borders, container, delta);

		for (ParallaxLayer layer : layers) {
			layer.update(container, delta);
		}
	}

	private void updateEntitiesGrid(EntityGridVault vault, GameContainer container, int delta, boolean vis) {
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

	private void updateEntitiesList(ArrayList<Entity> list, GameContainer container, int delta) {
		for (int i = 0; i < list.size(); ++i) {
			list.get(i).update(container, delta, null);
		}
	}

	public void render(GameContainer container, Graphics g) {
		g.setColor(new Color(8, 8, 41, 255));
		g.fillRect(0, 0, camera.getScreenWidth(), camera.getScreenHeight());

		for (ParallaxLayer l : layers) {
			l.render(container, g, camera);
		}

		renderEntitiesGrid(entities, container, g);
		renderEntitiesGrid(projectiles, container, g);

		// renderVaultGrid(g, entities);

		renderEntitiesList(borders, container, g);

		g.setColor(Color.white);

		g.drawString("entities: " + entities.size(), 10, 50);
		g.drawString("borders: " + borders.size(), 10, 70);
		g.drawString("projectiles: " + projectiles.size(), 10, 90);
	}

	private void renderVaultGrid(Graphics g, EntityGridVault vault) {
		for (int x = 0; x <= vault.cellsX; ++x) {
			for (int y = 0; y <= vault.cellsY; ++y) {
				float xo = x * vault.cellWidth;
				float yo = y * vault.cellHeight;
				if (camera.isVisible(new Entity(this, xo, yo, vault.cellWidth, vault.cellHeight))) {
					g.drawRect(camera.getScreenX(xo), camera.getScreenY(yo), xo + vault.cellWidth, yo
							+ vault.cellHeight);
					if (x != vault.cellsX && y != vault.cellsY) {
						g.drawString("" + vault.getAt(x, y).size(), camera.getScreenX(xo + 5),
								camera.getScreenY(yo + 5));
					}
				}
			}
		}
	}

	private void renderEntitiesGrid(EntityGridVault vault, GameContainer container, Graphics g) {
		Iterator<Entity> iterator = vault.getVisible(camera).iterator();
		while (iterator.hasNext()) {
			Entity e = iterator.next();
			e.render(container, g, camera);
		}
	}

	private void renderEntitiesList(ArrayList<Entity> list, GameContainer container, Graphics g) {
		for (int i = 0; i < list.size(); ++i) {
			Entity e = list.get(i);
			if (camera.isVisible(e)) {
				e.render(container, g, camera);
			}
		}
	}

	public boolean isOutsideWorld(Entity entity) {
		return entity.x <= 0 || entity.y <= 0 || entity.x >= width || entity.y >= height;
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

	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}

	public Camera getCamera() {
		return camera;
	}

	public Random getRandom() {
		return random;
	}

	public ArrayList<Entity> getBorders() {
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

	public Player getControllablePlayer() {
		for (Player player : playersPtr) {
			if (player.isControllable()) return player;
		}
		return null;
	}

	public void playerConnects(Player player) {
		addPlayer(player);
	}
}