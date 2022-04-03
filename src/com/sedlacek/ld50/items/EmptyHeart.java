package com.sedlacek.ld50.items;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;

public class EmptyHeart extends Item {

	public static final int ID = 9;
	private static SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/items.png"));
	
	public EmptyHeart(int col, int row) {
		super(col, row);
		this.name = "Empty heart";
		this.desc = "+2 max HP.";
		this.id = ID;
		this.w = 8;
		this.h = 8;
		this.currImg = ss.grabImage(1, 2, w, h, 16);
		this.limit = 50; // does not matter
	}

}
