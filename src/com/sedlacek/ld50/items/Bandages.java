package com.sedlacek.ld50.items;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;

public class Bandages extends Item {

	public static final int ID = 7;
	private static SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/items.png"));
	
	public Bandages(int col, int row) {
		super(col, row);
		this.name = "Bandages";
		this.desc = "Faster HP regeneration.";
		this.id = ID;
		this.w = 8;
		this.h = 8;
		this.currImg = ss.grabImage(7, 1, w, h, 16);
		this.limit = 2;
	}

}
