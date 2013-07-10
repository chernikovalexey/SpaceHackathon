package com.twopeople.race.entity;

import com.twopeople.race.World.World;

public class BorderBlock extends Entity {

    public enum BlockPosition
    {
        Left, Right
    }
    public BlockPosition position;

	public BorderBlock(World world, float x, float y) {
		super(world, x, y, 16, 16);
	}
}