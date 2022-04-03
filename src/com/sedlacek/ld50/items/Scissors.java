package com.sedlacek.ld50.items;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;

public class Scissors extends Item {

	public static final int ID = 5;
	private static SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/items.png"));
	
	public Scissors(int col, int row) {
		super(col, row);
		this.name = "Scissors";
		this.desc = "Your attacks deal double damage.";
		this.id = ID;
		this.w = 8;
		this.h = 8;
		this.currImg = ss.grabImage(5, 1, w, h, 16);
		this.limit = 1;
	}

}
