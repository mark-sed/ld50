package com.sedlacek.ld50.level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;
import com.sedlacek.ld50.main.Config;

public abstract class Tile {

	protected int id;
	protected BufferedImage img;
	protected static SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/tiles.png"));
	
	public void update() {
		
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(img, x, y, Config.TILE_SIZE*Config.SIZE_MULT, Config.TILE_SIZE*Config.SIZE_MULT, null);
	}
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public BufferedImage getImg() {
		return img;
	}
	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
	
}
