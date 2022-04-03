package com.sedlacek.ld50.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.sedlacek.ld50.entities.Boss;
import com.sedlacek.ld50.entities.Player;
import com.sedlacek.ld50.items.Item;
import com.sedlacek.ld50.level.Level;
import com.sedlacek.ld50.level.Room;
import com.sedlacek.ld50.main.AudioGalery;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Game;
import com.sedlacek.ld50.main.Game.State;
import com.sedlacek.ld50.main.KeyManager;

public class Story {

	private long startTime;
	private Boss boss;
	private int counter;
	private InfoBox latestBox;
	
	public Story() {
		boss = new Boss(270, 494);
	}
	
	public void start() {
		startTime = System.currentTimeMillis();
		Level.music.stopClip();
		Game.level.bossIndicator.setHide(true);
		boss.setX(Game.player.getX());
		boss.setY(Game.player.getY()-50*Config.SIZE_MULT);
		Game.player.itemBar.setHide(true);
		Game.player.hpbar.setHide(true);
		Game.player.coinBar.setHide(true);
		counter = 0;
	}
	
	
	public void update() {
		if(Game.getKeyManager().keys[KeyManager.ESC] || counter == 12) {
			Game.state = State.GAME;
			Game.level.bossIndicator.setHide(false);
			Random r = new Random();
			Game.player.setX(Config.WIDTH/2);
			Game.player.setY(Config.HEIGHT/2);
			Game.player.itemBar.setHide(false);
			Game.player.hpbar.setHide(false);
			Game.player.coinBar.setHide(false);
			if(latestBox != null && !latestBox.isDone()) {
				latestBox.setDone(true);
			}
			if(Game.musicOn) {
				Level.music.loop();
			}
			int ri = r.nextInt();
			if(ri == 0) {
				Game.player.gainCoins(12);
			}
			else {
				Game.player.addItem(Item.getItem(0, 0, 0f, 1f, 0f, 0f));
			}
			Game.getKeyManager().keys[KeyManager.ESC] = false;
		}
		
		switch(counter) {
		case 0:
			if(System.currentTimeMillis() - startTime > 800) {
				GUIHandler.objects.add(latestBox = new InfoBox(null, Config.WIDTH/2, Game.player.getY()+Game.player.getH()*Config.P_MULT+10,
						Player.name, "COME OUT! I AM HERE TO FIGHT YOU!", 3*1000));
				if(Game.soundOn) {
					AudioGalery.speaking.playClip();
				}
				++counter;
				startTime = System.currentTimeMillis();
			}
		break;
		case 1:
			if(System.currentTimeMillis() - startTime > 3000) {
				++counter;
				if(Game.soundOn) {
					AudioGalery.explosion.playClip();
				}
				Lightning l = new Lightning(Game.level.width/2, Game.player.col-4, 3, false);
				GUIHandler.objects.add(l);
				l.setX(boss.getX()+3*Config.P_MULT);
				l.setY(boss.getY()+Config.P_MULT*Config.TILE_SIZE+Config.P_MULT*Config.TILE_SIZE/2);
				
				startTime = System.currentTimeMillis();
			}
		break;
		case 2:
			if(System.currentTimeMillis() - startTime > 800) {
				GUIHandler.objects.add(latestBox = new InfoBox(null, Config.WIDTH/2, Game.player.getY()+Game.player.getH()*Config.P_MULT+10,
						Player.name, "...", 1500));
				if(Game.soundOn) {
					AudioGalery.speaking.playClip();
				}
				++counter;
				startTime = System.currentTimeMillis();
			}
		break;
		case 3:
			if(System.currentTimeMillis() - startTime > 2000) {
				GUIHandler.objects.add(latestBox = new InfoBox(null, Config.WIDTH/2, Game.player.getY()+Game.player.getH()*Config.P_MULT+10,
						Player.name, "Oh... I must have mistaken dungeon doors!", 3000));
				if(Game.soundOn) {
					AudioGalery.speaking.playClip();
				}
				++counter;
				startTime = System.currentTimeMillis();
			}
		break;
		case 4:
			if(System.currentTimeMillis() - startTime > 3500) {
				GUIHandler.objects.add(latestBox = new InfoBox(null, Config.WIDTH/2, Game.player.getY()+Game.player.getH()*Config.P_MULT+10,
						Player.name, "I'll see myself out, heh...", 2500));
				if(Game.soundOn) {
					AudioGalery.speaking.playClip();
				}
				++counter;
				startTime = System.currentTimeMillis();
			}
		break;
		case 5:
			if(System.currentTimeMillis() - startTime > 3000) {
				++counter;
				startTime = System.currentTimeMillis();
			}
		break;
		case 6:
			int speed = 2*Config.SIZE_MULT;
			if(System.currentTimeMillis() - startTime > 5) {
				Game.player.setY(Game.player.getY()+speed);
				startTime = System.currentTimeMillis();
			}
			if(Game.player.getY() > Config.HEIGHT+5) {
				startTime = System.currentTimeMillis();
				counter++;
			}
		break;
		case 7:
			if(System.currentTimeMillis() - startTime > 600) {
				GUIHandler.objects.add(latestBox = new InfoBox(null, Config.WIDTH/2, boss.getY()-boss.getH()*Config.P_MULT-4*Config.SIZE_MULT,
						Boss.name, "GET HERE!", 2300));
				if(Game.soundOn) {
					AudioGalery.reaperSpeaking.playClip();
				}
				++counter;
				startTime = System.currentTimeMillis();
			}
		break;
		case 8:
			if(System.currentTimeMillis() - startTime > 2800) {
				GUIHandler.objects.add(latestBox = new InfoBox(null, Config.WIDTH/2, boss.getY()-boss.getH()*Config.P_MULT-4*Config.SIZE_MULT,
						Boss.name, "Your DEATH is INEVITABLE!", 3000));
				if(Game.soundOn) {
					AudioGalery.reaperSpeaking.playClip();
				}
				++counter;
				startTime = System.currentTimeMillis();
			}
		break;
		case 9:
			if(System.currentTimeMillis() - startTime > 3500) {
				GUIHandler.objects.add(latestBox = new InfoBox(null, Config.WIDTH/2, boss.getY()-boss.getH()*Config.P_MULT-4*Config.SIZE_MULT,
						Boss.name, "Do not DELAY it!", 2500));
				if(Game.soundOn) {
					AudioGalery.reaperSpeaking.playClip();
				}
				++counter;
				startTime = System.currentTimeMillis();
			}
		break;
		case 10:
			if(System.currentTimeMillis() - startTime > 3500) {
				if(Game.soundOn) {
					AudioGalery.explosion.playClip();
				}
				Lightning l = new Lightning(Game.level.width/2, Game.player.col-4, 3, false);
				GUIHandler.objects.add(l);
				l.setX(boss.getX()+3*Config.P_MULT);
				l.setY(boss.getY()+Config.P_MULT*Config.TILE_SIZE+Config.P_MULT*Config.TILE_SIZE/2);
				++counter;
				startTime = System.currentTimeMillis();
			}
		break;
		case 11:
			if(System.currentTimeMillis() - startTime > 1000) {
				++counter;
				startTime = System.currentTimeMillis();
			}
		break;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Room.background, 0, 0, Config.WIDTH, Config.HEIGHT, null);
		g.setColor(new Color(0,0,0,00));
		g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);
		if(counter > 1 && counter < 11)
			boss.render(g);
		
		Game.player.render(g);
	}
}
