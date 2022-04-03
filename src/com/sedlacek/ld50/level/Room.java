package com.sedlacek.ld50.level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Game;

public class Room {

	protected int width;
	protected int height;
	protected Tile[][] tiles;
	protected ArrayList<Pairs> occupied;
	protected Entrance exit;
	public static BufferedImage background = ImageLoader.loadNS("/background.png");
	
	public Room(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width][height];
		occupied = new ArrayList<Pairs>();
		Game.player.col = width/2;
		Game.player.row = height-1;
	}
	
	public void update() {
		for(Pairs p: occupied) {
			int x = p.getX();
			int y = p.getY();
			tiles[x][y].update();
		}
	}
	
	public int getTileID(int col, int row) {
		if(col < 0 || col >= width || row < 0 || row >= height)
			return -2;
		return tiles[col][row] != null ? tiles[col][row].getID() : -1;
	}
	
	public void render(Graphics g, int leftCornerX, int leftCornerY) {
		g.drawImage(background, 0, 0, Config.WIDTH, Config.HEIGHT, null);
		// Dont use occupied, but render from the back to the fron to dont overlay
		for(int x = 0; x < width; ++x) {
			for(int y = 0; y < height; ++y) {
				if(tiles[x][y] == null)
					continue;
				tiles[x][y].render(g, leftCornerX+x*Config.TILE_SIZE*Config.SIZE_MULT, leftCornerY+y*Config.TILE_SIZE*Config.SIZE_MULT);
			}
		}
	}
}


