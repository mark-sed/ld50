package com.sedlacek.ld50.entities;

import java.util.Random;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;
import com.sedlacek.ld50.main.Config;

public class Gnome extends Enemy{

	Random r = new Random();
	
	public Gnome(int col, int row) {
		super(col, row);
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/enemies.png"));
		this.w = 10;
		this.h = 12;
		this.currImg = ss.grabImage(2, 1, w, h, Config.TILE_SIZE);
		this.xOffset = 0;
		this.yOffset = 0;
		this.hp = 14;
		this.attackDmg = 3;
		
		this.dropChance = 0.45f;
		this.commonCh = 0.65f;
		this.rareCh = 0.23f;
		this.epicCh = 0.07f;
		this.legendaryCh = 0.05f;
	}

}
