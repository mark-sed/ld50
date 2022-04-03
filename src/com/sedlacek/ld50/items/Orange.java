package com.sedlacek.ld50.items;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;

public class Orange extends Item {

	public static final int ID = 8;
	private static SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/items.png"));
	
	public Orange(int col, int row) {
		super(col, row);
		this.name = "Orange";
		this.desc = "More HP regeneration.";
		this.id = ID;
		this.w = 8;
		this.h = 8;
		this.currImg = ss.grabImage(8, 1, w, h, 16);
		this.limit = 1;
	}

}
