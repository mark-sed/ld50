package com.sedlacek.ld50.level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import com.sedlacek.ld50.entities.Boss;
import com.sedlacek.ld50.main.AudioPlayer;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Game;

public class BossRoom extends Room {

	public static final int W = 16;
	public static final int H = 16;
	private Random r = new Random();
	
	public static final AudioPlayer music = new AudioPlayer("/at1.wav");
	
	private void placeTile(int i, int j) {
		tiles[i][j] = new DungeonTile();
		occupied.add(new Pairs(i, j));
	}
	
	public BossRoom(int width, int height, int numTiles) {
		super(width, height);
		int exitX = r.nextInt(W);
		for(int x = 0; x < width; ++x) {
			for(int y = 0; y < height; ++y) {
				if(y == 0 && x == exitX) {
					tiles[x][y] = new Entrance();
					occupied.add(new Pairs(x, y));
				}
				else {
					placeTile(x, y);
				}
			}
		}
		if(Game.musicOn) {
			music.reset();
			music.loop();
		}
	}
	
	@Override
	public void render(Graphics g, int leftCornerX, int leftCornerY) {
		super.render(g, leftCornerX, leftCornerY);
		if(Boss.countdownSet) {
			for(int x = 0; x < W; ++x) {
				for(int y = 0; y < H; ++y) {
					int v = Boss.damageCountdown[x][y];
					if(Boss.damageCountdownHide[x][y]) {
						g.setColor(new Color(0,0,0,180));
						g.fillRect(leftCornerX+x*Config.TILE_SIZE*Config.SIZE_MULT, leftCornerY+y*Config.TILE_SIZE*Config.SIZE_MULT,
								Config.TILE_SIZE*Config.SIZE_MULT, Config.TILE_SIZE*Config.SIZE_MULT);
						g.setColor(new Color(245,245,245));
						g.setFont(new Font("DorFont03", Font.BOLD, 8*Config.SIZE_MULT));
						g.drawString("?", leftCornerX+x*Config.TILE_SIZE*Config.SIZE_MULT+Config.TILE_SIZE*Config.SIZE_MULT/2-g.getFontMetrics().stringWidth("?")/2, 
								leftCornerY+y*Config.TILE_SIZE*Config.SIZE_MULT+Config.TILE_SIZE*Config.SIZE_MULT-8*Config.SIZE_MULT/2);
					}
					else {
						if(v <= 0) {
							continue;
						}
						int op = 180;
						op -= v*20;
						if(tiles[x][y].getID() == Entrance.ID) {
							op -= 50;
						}
						if(op < 0) {
							op = 10;
						}
						g.setColor(new Color(255,0,0,op));
						g.fillRect(leftCornerX+x*Config.TILE_SIZE*Config.SIZE_MULT, leftCornerY+y*Config.TILE_SIZE*Config.SIZE_MULT,
								Config.TILE_SIZE*Config.SIZE_MULT, Config.TILE_SIZE*Config.SIZE_MULT);
						g.setColor(new Color(245,245,245));
						g.setFont(new Font("DorFont03", Font.BOLD, 8*Config.SIZE_MULT));
						g.drawString(""+v, leftCornerX+x*Config.TILE_SIZE*Config.SIZE_MULT+Config.TILE_SIZE*Config.SIZE_MULT/2-g.getFontMetrics().stringWidth(""+v)/2, 
								leftCornerY+y*Config.TILE_SIZE*Config.SIZE_MULT+Config.TILE_SIZE*Config.SIZE_MULT-8*Config.SIZE_MULT/2);
					}
				}
			}
		}
	}

}
