package com.sedlacek.ld50.level;

import java.util.Random;

import com.sedlacek.ld50.main.Config;

public class DungeonTile extends Tile {
	
	public static final int ID = 0;
	Random r = new Random();
	
	public DungeonTile() {
		this.id = ID;
		float rf = r.nextFloat();
		if(rf < 0.15) {
			img = ss.grabImage(1, 1, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		}
		else if(rf < 0.40) {
			img = ss.grabImage(3, 1, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		}
		else {
			img = ss.grabImage(2, 1, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		}
	}

}
