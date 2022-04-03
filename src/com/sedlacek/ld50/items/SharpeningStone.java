package com.sedlacek.ld50.items;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;

public class SharpeningStone extends Item {

	public static final int ID = 6;
	private static SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/items.png"));
	
	public SharpeningStone(int col, int row) {
		super(col, row);
		this.name = "Sharpening stone";
		this.desc = "+2 DMG.";
		this.id = ID;
		this.w = 8;
		this.h = 8;
		this.currImg = ss.grabImage(6, 1, w, h, 16);
		this.limit = 100;
	}

}
