package com.sedlacek.ld50.items;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;

public class Wings extends Item {

	public static final int ID = 4;
	private static SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/items.png"));
	
	public Wings(int col, int row) {
		super(col, row);
		this.name = "Wings";
		this.desc = "Sometimes Reaper can't catch up.";
		this.id = ID;
		this.w = 8;
		this.h = 8;
		this.currImg = ss.grabImage(4, 1, w, h, 16);
		this.limit = 1;
	}

}
