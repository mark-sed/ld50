package com.sedlacek.ld50.level;

import com.sedlacek.ld50.main.Config;

public class Entrance extends Tile {

	public static final int ID = 1;
	
	public Entrance() {
		this.id = ID;
		this.img = ss.grabImage(2, 3, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
	}
}
