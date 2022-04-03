package com.sedlacek.ld50.gui;

import java.awt.Color;
import java.awt.Graphics;

import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Game;

public class Lightning extends GUIObject {
	
	private int opac;
	private int decay = 2;
	private long lastTime;
	
	public Lightning(int col, int row, int decay) {
		this.col = col;
		this.row = row;
		this.x = 2*Config.SIZE_MULT+Config.WIDTH/2-Game.player.col*Config.TILE_SIZE*Config.SIZE_MULT+Game.player.xOffset+col*Config.TILE_SIZE*Config.SIZE_MULT;
		this.y = 30+Config.HEIGHT/2-Game.player.row*Config.TILE_SIZE*Config.SIZE_MULT+Game.player.yOffset+row*Config.TILE_SIZE*Config.SIZE_MULT+Config.TILE_SIZE*Config.SIZE_MULT+Config.TILE_SIZE*Config.SIZE_MULT/2;
		this.decay = decay;
		opac = 180;
		this.move = true;
	}
	
	public Lightning(int col, int row, int decay, boolean move) {
		this.col = col;
		this.row = row;
		this.x = 2*Config.SIZE_MULT+Config.WIDTH/2-Game.player.col*Config.TILE_SIZE*Config.SIZE_MULT+Game.player.xOffset+col*Config.TILE_SIZE*Config.SIZE_MULT;
		this.y = 30+Config.HEIGHT/2-Game.player.row*Config.TILE_SIZE*Config.SIZE_MULT+Game.player.yOffset+row*Config.TILE_SIZE*Config.SIZE_MULT+Config.TILE_SIZE*Config.SIZE_MULT+Config.TILE_SIZE*Config.SIZE_MULT/2;
		this.decay = decay;
		opac = 180;
		this.move = move;
	}

	@Override
	protected void update() {
		if(move) {
			this.x = 2*Config.SIZE_MULT+Config.WIDTH/2-Game.player.col*Config.TILE_SIZE*Config.SIZE_MULT+Game.player.xOffset+col*Config.TILE_SIZE*Config.SIZE_MULT;
			this.y = 30+Config.HEIGHT/2-Game.player.row*Config.TILE_SIZE*Config.SIZE_MULT+Game.player.yOffset+row*Config.TILE_SIZE*Config.SIZE_MULT+Config.TILE_SIZE*Config.SIZE_MULT+Config.TILE_SIZE*Config.SIZE_MULT/2;
		}
		if(System.currentTimeMillis() - lastTime > 10) {
			opac -= decay;
			lastTime = System.currentTimeMillis();
		}
		if(opac < 0) {
			opac = 0;
			done = true;
		}
	}

	@Override
	protected void render(Graphics g) {
		g.setColor(new Color(227, 227, 123, opac));
		g.fillRect(x, -30, Config.TILE_SIZE*Config.SIZE_MULT-4*Config.SIZE_MULT, y);
		g.fillRect(x+4*Config.SIZE_MULT, -30, Config.TILE_SIZE*Config.SIZE_MULT-12*Config.SIZE_MULT, y-2*Config.SIZE_MULT);
	}
}
