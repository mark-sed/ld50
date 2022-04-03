package com.sedlacek.ld50.items;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;
import com.sedlacek.ld50.main.AudioGalery;
import com.sedlacek.ld50.main.Game;

public class Heart extends Item {

	public static final int ID = 3;
	private static SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
	
	public Heart(int col, int row) {
		super(col, row);
		this.id = ID;
		this.w = 9;
		this.h = 8;
		this.amount = 10;
		this.currImg = ss.grabImage(1, 1, w, h, 16);
	}
	
	@Override
	public void pickup() {
		Game.player.gainHp(amount);
		if(Game.soundOn) {
			AudioGalery.heart.playClip();
		}
		this.pickedup = true;
	}

}
