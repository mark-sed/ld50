package com.sedlacek.ld50.entities;

import java.util.Random;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;
import com.sedlacek.ld50.main.Config;

public class HeavyKnight extends Enemy{

	Random r = new Random();
	
	public HeavyKnight(int col, int row) {
		super(col, row);
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/enemies.png"));
		this.w = 14;
		this.h = 12;
		this.currImg = ss.grabImage(3, 1, w, h, Config.TILE_SIZE);
		this.xOffset = -1*Config.SIZE_MULT;
		this.yOffset = 0;
		this.hp = 30;
		this.attackDmg = 3;
		
		this.dropChance = 1f;
		this.commonCh = 0.35f;
		this.rareCh = 0.53f;
		this.epicCh = 0.08f;
		this.legendaryCh = 0.04f;
	}

}
