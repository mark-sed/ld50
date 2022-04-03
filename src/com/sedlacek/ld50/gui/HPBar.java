package com.sedlacek.ld50.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Entity;

public class HPBar extends GUIObject {
	
	private Entity e;
	private BufferedImage heart;
	private BufferedImage halfHeart;
	private BufferedImage border;
	
	public HPBar(Entity e) {
		this.e = e;
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
		this.heart = ss.grabImage(1, 1, 9, 8, 16);
		this.halfHeart = ss.grabImage(2, 1, 5, 8, 16);
		this.border = ss.grabImage(3, 1, 9, 8, 16);
		this.x = 20;
		this.y = 20;
	}

	@Override
	protected void update() {
		
		
	}

	@Override
	protected void render(Graphics g) {
		if(hide)
			return;
		int borders = e.getMaxHp()/2;
		int whole = e.getHp()/2;
		boolean half = e.getHp() % 2 == 1;
		
		int w = border.getWidth()*Config.SIZE_MULT;
		int h = border.getHeight()*Config.SIZE_MULT;
		int space = 5;
		for(int i = 0; i < borders; ++i) {
			g.drawImage(border, x+i*w+i*space, y, w, h, null);
			if(i < whole) {
				g.drawImage(heart, x+i*w+i*space, y, w, h, null);
			}
			else if(i == whole && half) {
				g.drawImage(halfHeart, x+i*w+i*space, y, (halfHeart.getWidth()*Config.SIZE_MULT), (halfHeart.getHeight()*Config.SIZE_MULT), null);
			}
		}
	}

}
