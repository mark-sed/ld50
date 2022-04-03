package com.sedlacek.ld50.level;

import com.sedlacek.ld50.main.Config;

public class Mine extends Tile {

	public static final int ID = 2;
	
	public Mine() {
		this.id = ID;
		img = ss.grabImage(1, 5, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
	}

}
