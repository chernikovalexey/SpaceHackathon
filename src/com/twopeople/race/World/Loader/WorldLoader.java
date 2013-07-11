package com.twopeople.race.World.Loader;

import com.twopeople.race.World.World;
import com.twopeople.race.entity.Entity;
import com.twopeople.race.entity.EntityGridVault;
import com.twopeople.race.entity.Interior.BorderBlock;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with podko_000. User: podko_000 Date: 10.07.13 Time: 21:41 To change
 * this template use File | Settings | File Templates.
 */
public class WorldLoader {
	private WorldMetaData metaData;
	private World _world;

	public WorldLoader(String dirName, World world) throws IOException, ClassNotFoundException, SlickException {
		metaData = WorldMetaData.load(dirName + "\\meta.bin");
		_world = world;
		_parseImage(dirName + "\\data.png");
		_sortBorders();
	}

	private void _sortBorders() {
		EntityGridVault v = _world.getBorders();

		List<Entity> borders = v.getAll();

		List<BorderBlock> left = new ArrayList<BorderBlock>();
		List<BorderBlock> right = new ArrayList<BorderBlock>();

		BorderBlock block;
		for (Entity border : borders) {
			block = (BorderBlock) border;
			if (block.position.equals(BorderBlock.BlockPosition.Left)) left.add(block);
			else right.add(block);
		}

		left = _orderBlocks(left);
		right = _orderBlocks(right);

		borders.clear();

		for (BorderBlock b : left)
			borders.add(b);

		for (BorderBlock b : right)
			borders.add(b);
	}

	private List<BorderBlock> _orderBlocks(List<BorderBlock> blocks) {
		List<BorderBlock> result = new ArrayList<BorderBlock>();

		BorderBlock a, b = null;
		float minDist, d;
		BorderBlock minJ = null;

		for (int i = 0; i < blocks.size(); i++) {
			a = blocks.get(i);
			result.add(a);
			minDist = Float.MAX_VALUE;
			for (int j = 0; j < blocks.size(); j++) {
				b = blocks.get(j);
				if (i == j) continue;

				d = a.getDistanceTo(b);
				if (d < minDist) {
					minDist = d;
					minJ = b;
				}
			}
			result.add(b);
			result.remove(a);
			result.remove(minJ);
		}

		return result;
	}

	private void _parseImage(String fileName) throws IOException, SlickException {

		Image img = new Image(fileName);
		Color pixelColor;
		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++) {
				pixelColor = img.getColor(i, j);

				WorldColor.commands.get(pixelColor).execute(_world, i, j, pixelColor);

			}
	}

	private byte[] data;

	public WorldMetaData getMetaData() {
		return metaData;
	}
}
