package com.sedlacek.ld50.items;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;
import com.sedlacek.ld50.main.Game;

public class Coins extends Item {

	public static final int ID = 0;
	private static SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
	
	public Coins(int col, int row, int amount) {
		super(col, row);
		this.id = ID;
		this.amount = amount;
		this.w = 9;
		this.h = 9;
		this.currImg = ss.grabImage(1, 2, w, h, 16);
	}
	
	@Override
	public void pickup() {
		Game.player.gainCoins(amount);
		this.pickedup = true;
	}

	@Override
	public void fixedUpdate() {
		// TODO Auto-generated method stub
		
	}

}
