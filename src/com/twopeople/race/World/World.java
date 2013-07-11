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
import com.twopeople.race.entity.Asteroid;
import com.twopeople.race.entity.Entity;
import com.twopeople.race.entity.EntityGridVault;
import com.twopeople.race.entity.StartPoint;
import com.twopeople.race.entity.Interior.BorderBlock;
import com.twopeople.race.entity.Unit.Player;

public class World {
    private Camera camera;
    private Random random = new Random();

    private ArrayList<StartPoint> startPoints = new ArrayList<StartPoint>();
    private EntityGridVault background;
    private EntityGridVault entities;
    private ArrayList<Player> playersPtr = new ArrayList<Player>();
    private ArrayList<Entity> borders = new ArrayList<Entity>();

    public static final int TILE_SIZE = 16;
    private int width = 4540, height = 1980;

    private WorldMetaData metaData;

    public World(Camera camera) {
        this.camera = camera;

        WorldSize size = WorldLoader.preload("res/maps/map001");
        setWidth(size.width);
        setHeight(size.height);

        this.background = new EntityGridVault(256, 256, width, height);
        this.entities = new EntityGridVault(128, 128, width, height);

        WorldLoader.load("res/maps/map001", this);

        camera.setWorldSize(width, height);
        Player player = new Player(this, 0, 0);
        player.setCoordinates(startPoints.get(0).x, startPoints.get(0).y);
        addPlayer(player);
        addEntity(new Asteroid(this, 360, 290));
        for (int x = 0; x < 256 * 6; x += 10) {
            for (int y = 0; y < 256 * 6; y += 16) {
                if (random.nextInt(128) % 2 == 0) {
                    // background.add(new Star(this, x, y));
                }
            }
        }
    }

    public void addStartPoint(StartPoint point) {
        startPoints.add(point);
    }

    public void setMetaData(WorldMetaData data) {
        this.metaData = data;
    }

    public void update(GameContainer container, int delta) {
        updateEntitiesGrid(background, container, delta, true);
        updateEntitiesGrid(entities, container, delta, false);
        updateEntitiesList(borders, container, delta);
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
        renderEntitiesGrid(background, container, g);
        renderEntitiesGrid(entities, container, g);
        renderEntitiesList(borders, container, g);

        renderVaultGrid(g, entities);

        Entity[] left = new Entity[borders.size()];
        int li = 0;
        for (Entity block : borders) {
            BorderBlock b = (BorderBlock) block;
            if (b.position == BorderBlock.BlockPosition.Left) {
                left[li++] = b;
            }
        }

        Entity[] right = new Entity[borders.size()];
        int ri = 0;
        for (Entity block : borders) {
            BorderBlock b = (BorderBlock) block;
            if (b.position == BorderBlock.BlockPosition.Right) {
                right[ri++] = b;
            }
        }

        g.setColor(Color.red);
        for (int i = 0; i < li; ++i) {
            if (i < li - 1) {
                g.drawLine(camera.getScreenX(left[i].x), camera.getScreenY(left[i].y),
                        camera.getScreenX(left[i + 1].x), camera.getScreenY(left[i + 1].y));
            }
        }
        if(metaData.haveLaps()) {
            g.drawLine(camera.getScreenX(left[0].x), camera.getScreenY(left[0].y), camera.getScreenX(left[li - 1].x),
                    camera.getScreenY(left[li - 1].y));
        }

        g.setColor(Color.green);
        for (int i = 0; i < ri; ++i) {
            if (i < ri - 1) {
                g.drawLine(camera.getScreenX(right[i].x), camera.getScreenY(right[i].y),
                        camera.getScreenX(right[i + 1].x), camera.getScreenY(right[i + 1].y));
            }
        }
        if(metaData.haveLaps()) {
            g.drawLine(camera.getScreenX(right[0].x), camera.getScreenY(right[0].y),
                    camera.getScreenX(right[ri - 1].x), camera.getScreenY(right[ri - 1].y));
        }

        g.drawString("background: " + background.size(), 10, 30);
        g.drawString("entities: " + entities.size(), 10, 50);
        g.drawString("borders: " + borders.size(), 10, 70);
    }

    @SuppressWarnings("unused")
    private void renderVaultGrid(Graphics g, EntityGridVault vault) {
        for (int x = 0; x < vault.cellsX; ++x) {
            for (int y = 0; y < vault.cellsY; ++y) {
                float xo = x * vault.cellWidth;
                float yo = y * vault.cellHeight;
                if (camera.isVisible(new Entity(this, xo, yo, vault.cellWidth, vault.cellHeight))) {
                    g.drawRect(camera.getScreenX(xo), camera.getScreenY(yo), xo + vault.cellWidth, yo
                            + vault.cellHeight);
                    g.drawString("" + vault.getAt(x, y).size(), camera.getScreenX(xo + 5), camera.getScreenY(yo + 5));
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
}