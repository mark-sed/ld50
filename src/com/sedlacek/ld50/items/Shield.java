package com.sedlacek.ld50.items;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;

public class Shield extends Item {

	public static final int ID = 1;
	private static SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/items.png"));
	
	public Shield(int col, int row) {
		super(col, row);
		this.name = "Wooden shield";
		this.desc = "Blocks 1 DMG.";
		this.id = ID;
		this.w = 8;
		this.h = 8;
		this.currImg = ss.grabImage(1, 1, w, h, 16);
		this.limit = 2;
	}

}
