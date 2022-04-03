package com.sedlacek.ld50.items;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;

public class PiggyBank extends Item {

	public static final int ID = 2;
	private static SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/items.png"));
	
	public PiggyBank(int col, int row) {
		super(col, row);
		this.name = "Piggy bank";
		this.desc = "Multiplies gold by 2.";
		this.id = ID;
		this.w = 8;
		this.h = 8;
		this.currImg = ss.grabImage(2, 1, w, h, 16);
		this.limit = 1;
	}
}
