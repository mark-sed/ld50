package com.sedlacek.ld50.entities;

import java.util.Random;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;
import com.sedlacek.ld50.items.Item;
import com.sedlacek.ld50.main.Config;

public class Skeleton extends Enemy{

	Random r = new Random();
	
	public Skeleton(int col, int row) {
		super(col, row);
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/enemies.png"));
		this.w = 11;
		this.h = 12;
		this.currImg = ss.grabImage(1, 1, w, h, Config.TILE_SIZE);
		this.xOffset = -1*Config.P_MULT;
		this.yOffset = 0;
		this.hp = 10;
		this.attackDmg = 2;
		
		this.dropChance = 0.35f;
		this.commonCh = 0.88f;
		this.rareCh = 0.1f;
		this.epicCh = 0.02f;
		this.legendaryCh = 0f;
	}

}
