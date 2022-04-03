package com.sedlacek.ld50.entities;

import java.util.Random;

import com.sedlacek.ld50.items.Item;
import com.sedlacek.ld50.level.Level;
import com.sedlacek.ld50.main.Entity;
import com.sedlacek.ld50.main.Game;

public abstract class Enemy extends Entity {

	Random r = new Random();
	protected float dropChance;
	protected float commonCh, rareCh, epicCh, legendaryCh;
	
	public Enemy(int col, int row) {
		super(col, row);
	}

	public void randomMove() {
		if(r.nextBoolean() || System.currentTimeMillis() - lastHitTime < 200)
			return;
		int d = r.nextInt(4);
		int add_c = 0;
		int add_r = 0;
		if(d == 0) {
			add_r = -1;
		}
		else if(d == 1) {
			add_r = 1;
		}
		else if(d == 2) {
			add_c = -1;
		}
		else {
			add_c = 1;
		}
		if(Level.getTileID(col+add_c, row+add_r) >= 0 && Level.getEnemyAt(col+add_c, row+add_r) == null) {
			if(!(Game.player.getCol() == col+add_c && Game.player.getRow() == row + add_r)) {
				this.col += add_c;
				this.row += add_r;
			}
		}
	}
	
	@Override
	public void dropLoot() {
		if(r.nextFloat() < this.dropChance) {
			Item.dropItem(col, row, commonCh, rareCh, epicCh, legendaryCh);
		}
	}
	
}
