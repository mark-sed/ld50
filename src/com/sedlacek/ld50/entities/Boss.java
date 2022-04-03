package com.sedlacek.ld50.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;
import com.sedlacek.ld50.gui.GUIHandler;
import com.sedlacek.ld50.gui.Lightning;
import com.sedlacek.ld50.level.BossRoom;
import com.sedlacek.ld50.main.AudioGalery;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Game;

public class Boss extends Enemy{

	Random r = new Random();
	private BufferedImage scythe;
	private boolean up, up2;
	private int offY, offY2;
	private long lastBreathTime2;
	
	public static String name = "Grim \"Death\" Reaper";
	
	public int distance = 200;
	public int speed = 2;
	public int minGainDistance = 100;
	public int maxGainDistance = 200;
	
	public static int[][] damageCountdown = new int[BossRoom.W][BossRoom.H];
	public static boolean[][] damageCountdownHide = new boolean[BossRoom.W][BossRoom.H];
	public static boolean countdownSet;
	private int maxCountdown = 6;
	private int hideAmount = 40;
	
	public Boss(int col, int row) {
		super(col, row);
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/enemies.png"));
		this.w = 16;
		this.h = 16;
		this.currImg = ss.grabImage(1, 2, w, h, Config.TILE_SIZE);
		this.scythe = ss.grabImage(2, 2, 12, 14, Config.TILE_SIZE);
		this.xOffset = -2*Config.P_MULT;
		this.yOffset = -2*Config.P_MULT;
		this.hp = 300;
		this.attackDmg = 4;
		
		this.dropChance = 1f;
		this.commonCh = 0.0f;
		this.rareCh = 0.0f;
		this.epicCh = 0.0f;
		this.legendaryCh = 1f;
	}
	
	public void init() {
		for(int x = 0; x < BossRoom.W; ++x) {
			for(int y = 0; y < BossRoom.H; ++y) {
				damageCountdown[x][y] = r.nextInt(maxCountdown)+1;
				damageCountdownHide[x][y] = false;
			}
		}
		for(int i = 0; i < hideAmount; ++i) {
			int rx = r.nextInt(BossRoom.W);
			int ry = r.nextInt(BossRoom.H);
			damageCountdownHide[rx][ry] = true;
		}
		--maxCountdown;
		hideAmount += 4;
		if(maxCountdown < 2)
			maxCountdown = 2;
		countdownSet = true;
		
		if(hp <= 0) {
			Game.state = Game.State.GAME_WON;
		}
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void randomMove() {
		if(!countdownSet) {
			init();
		}
		else {
			for(int x = 0; x < BossRoom.W; ++x) {
				for(int y = 0; y < BossRoom.H; ++y) {
					damageCountdownHide[x][y] = false;
					if(damageCountdown[x][y] == 1) {
						GUIHandler.objects.add(new Lightning(x, y, 2));
						if(x == Game.player.col && y == Game.player.row) {
							Game.player.reduceHp(20);
						}
					}
					damageCountdown[x][y] -= 1;
					if(damageCountdown[x][y] <= 0 && r.nextBoolean()) {
						damageCountdown[x][y] = r.nextInt(maxCountdown)+1;
					}
				}
			}
			for(int i = 0; i < hideAmount; ++i) {
				int rx = r.nextInt(BossRoom.W);
				int ry = r.nextInt(BossRoom.H);
				damageCountdownHide[rx][ry] = true;
			}
			if(Game.soundOn)
				AudioGalery.explosion.playClip();
		}
	}
	
	public void render(Graphics g) {
		if(System.currentTimeMillis() - this.lastBreathTime > 25) {
			if(up) {
				offY+=1;
				if(offY >= 20) {
					up = !up;
				}
			}
			else {
				offY-=1;
				if(offY <= 0) {
					up = !up;
				}
			}
			lastBreathTime = System.currentTimeMillis();
		}
		g.drawImage(this.currImg, x+xOffset, y+offY+yOffset, w*Config.P_MULT, h*Config.P_MULT, null);
		if(System.currentTimeMillis() - this.lastBreathTime2 > 25) {
			if(up2) {
				offY2+=2;
				if(offY2 >= 40) {
					up2 = !up2;
				}
			}
			else {
				offY2-=2;
				if(offY2 <= 0) {
					up2 = !up2;
				}
			}
			lastBreathTime2 = System.currentTimeMillis();
		}
		g.drawImage(this.scythe, x+7*Config.P_MULT+xOffset, y+offY2+yOffset, w*Config.P_MULT, h*Config.P_MULT, null);
	}
}
