package com.twopeople.race.World.Loader;

import com.twopeople.race.World.Loader.IWorldColorCommand;
import com.twopeople.race.World.World;
import com.twopeople.race.entity.Entity;
import com.twopeople.race.entity.Interior.BorderBlock;

import org.newdawn.slick.Color;

/**
 * Created with podko_000.
 * User: podko_000
 * Date: 10.07.13
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public class BorderBlockCommand implements IWorldColorCommand {
    @Override
    public void execute(World world, int px, int py, Color pixel) {
        BorderBlock block = new BorderBlock(world, px * World.TILE_SIZE, py * World.TILE_SIZE);
        if (pixel.equals(WorldColor.R_BORDER_BLOCK))
            block.position = BorderBlock.BlockPosition.Right;
        else
            block.position = BorderBlock.BlockPosition.Left;
        world.addBorder(block);
    }
}
