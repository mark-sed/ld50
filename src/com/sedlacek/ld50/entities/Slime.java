package com.sedlacek.ld50.entities;

import java.util.Random;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;
import com.sedlacek.ld50.main.Config;

public class Slime extends Enemy{

	Random r = new Random();
	
	public Slime(int col, int row) {
		super(col, row);
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/enemies.png"));
		this.w = 9;
		this.h = 7;
		this.currImg = ss.grabImage(4, 1, w, h, Config.TILE_SIZE);
		this.xOffset = 0;
		this.yOffset = 12*Config.SIZE_MULT;
		this.hp = 3;
		this.attackDmg = 1;
		
		this.dropChance = 1f;
		this.commonCh = 0.95f;
		this.rareCh = 0.04f;
		this.epicCh = 0.01f;
		this.legendaryCh = 0f;
	}

}
