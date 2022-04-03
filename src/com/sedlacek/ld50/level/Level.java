package com.sedlacek.ld50.level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.sedlacek.ld50.entities.Boss;
import com.sedlacek.ld50.entities.Enemy;
import com.sedlacek.ld50.entities.Gnome;
import com.sedlacek.ld50.entities.HeavyKnight;
import com.sedlacek.ld50.entities.Player;
import com.sedlacek.ld50.entities.Skeleton;
import com.sedlacek.ld50.entities.Slime;
import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.gui.BossIndicator;
import com.sedlacek.ld50.gui.GUIHandler;
import com.sedlacek.ld50.items.Item;
import com.sedlacek.ld50.items.Wings;
import com.sedlacek.ld50.main.AudioGalery;
import com.sedlacek.ld50.main.AudioPlayer;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Game;
import com.sedlacek.ld50.main.Game.Turn;

public class Level {

	public int number = 0;
	public static Room currentRoom;
	public int width;
	public int height;
	private int tileAmount;
	private int enemyAmount;
	
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public static ArrayList<Item> drops = new ArrayList<Item>();
	public static Boss boss;
	public boolean bossFight;
	public int reaperEscapes;
	private Random r = new Random();
	public BossIndicator bossIndicator;
	private BufferedImage reaperAura;
	private boolean noiseOff;
	
	public static final AudioPlayer music = new AudioPlayer("/at3.wav");
	
	public Level() {
		width = 500;
		height = 500;
		tileAmount = 35;
		enemyAmount = 1;
		this.number = 0;
		boss = new Boss(BossRoom.W/2,BossRoom.H/2);
		bossIndicator = new BossIndicator(boss);
		GUIHandler.objects.add(bossIndicator);
		bossFight = false;
		nextLevel();
		reaperAura = ImageLoader.loadNS("/reaperAura.png");
		reaperEscapes = 0;
		if(Game.musicOn)
			music.loop();
	}
	
	public void nextLevel() {
		for(Item d: drops) {
			if(d.getCol() == Game.player.col && d.getRow() == Game.player.row) {
				d.pickup();
			}
		}
		if(Game.soundOn && number > 0) {
			AudioGalery.exit.playClip();
		}
		++number;
		if(bossFight) {
			bossFight = false;
			BossRoom.music.stopClip();
			if(Game.musicOn) {
				music.reset();
				music.loop();
			}
			reaperEscapes++;
			boss.distance = r.nextInt(boss.maxGainDistance - boss.minGainDistance)+boss.minGainDistance;
		}
		Boss.countdownSet = false;
		bossIndicator.setHide(false);
		enemies.clear();
		drops.clear();
		tileAmount += r.nextInt(10)+10;
		enemyAmount += r.nextInt(3)+2;
		currentRoom = new Dungeon(width, height, tileAmount);
		spawnEnemies(enemyAmount);
	}
	
	public static void dropItem(Item i) {
		Level.drops.add(i);
	}
	
	public void addEnemy(Enemy e) {
		// Does not fully takes care of correct rendering
		// It aint much but its honest work
		if(enemies.isEmpty())
			enemies.add(e);
		else {
			for(int i = 0; i < enemies.size(); ++i) {
				if(enemies.get(i).getY() >= e.getY()) {
					enemies.add(i, e);
					break;
				}
			}
		}
	}
	
	public void spawnEnemies(int amount) {
		int placed = 0;
		int slimeCount = 0;
		while(placed < amount) {
			Pairs p = currentRoom.occupied.get(r.nextInt(currentRoom.occupied.size()));
			if(getEnemyAt(p.getX(), p.getY()) == null && p.getX() != Game.player.col && p.getY() != Game.player.getY()) { 
				float fr = r.nextFloat();
				if(number < 3) {
					if(fr < 0.1f && slimeCount < 1) {
						++slimeCount;
						addEnemy(new Slime(p.getX(), p.getY()));
					}
					else
						addEnemy(new Skeleton(p.getX(), p.getY()));
				}
				else if(number < 10){
					if(fr < 0.05 && slimeCount < 2) {
						++slimeCount;
						addEnemy(new Slime(p.getX(), p.getY()));
					}
					else if(fr < 0.33)
						addEnemy(new Gnome(p.getX(), p.getY()));
					else
						addEnemy(new Skeleton(p.getX(), p.getY()));
				}
				else {
					if(fr < 0.03 && slimeCount < 3) {
						++slimeCount;
						addEnemy(new Slime(p.getX(), p.getY()));
					}
					else if(fr < 0.2)
						addEnemy(new HeavyKnight(p.getX(), p.getY()));
					else if(fr < 0.5) {
						addEnemy(new Gnome(p.getX(), p.getY()));
					}
					else {
						addEnemy(new Skeleton(p.getX(), p.getY()));
					}
				}
				++placed;
			}
		}
	}
	
	public void spawnBoss() {
		bossFight = true;
		bossIndicator.setHide(true);
		enemies.clear();
		drops.clear();
		music.stopClip();
		currentRoom = new BossRoom(BossRoom.W, BossRoom.H, tileAmount);
		enemies.add(boss);
		Game.turn = Turn.PLAYER;
	}
	
	public void endTurn() {
		if(boss.distance <= 0 && !bossFight) {
			spawnBoss();
		}
		else {
			if(Game.turn == Turn.PLAYER) {
				Game.turn = Turn.ENEMIES;
				// Enemies turn
				for(Enemy e: enemies) {
					e.randomMove();
				}
				if(!Player.hasItem(Wings.ID) || Player.hasItem(Wings.ID) && r.nextBoolean())
					boss.distance-=boss.speed;
				endTurn();
			}
			else {
				Game.turn = Turn.PLAYER;
				Game.player.turnStart();
			}
		}
	}
	
	public static Enemy getEnemyAt(int col, int row) {
		for(Enemy e: enemies) {
			if(e.getCol() == col && e.getRow() == row) {
				return e;
			}
		}
		return null;
	}
	
	public static boolean isEnemyAt(int col, int row) {
		for(Enemy e: enemies) {
			if(e.getCol() == col && e.getRow() == row) {
				return true;
			}
		}
		return false;
	}
	
	public static int getTileID(int col, int row) {
		return currentRoom.getTileID(col, row);
	}
	
	public void update() {
		currentRoom.update();
		for(int i = 0; i < enemies.size(); ++i) {
			Enemy e = enemies.get(i);
			if(e.getHp() <= 0) {
				e.dropLoot();
				enemies.remove(e);
				--i;
				continue;
			}
			e.update();
			e.setX(Config.WIDTH/2-Game.player.col*Config.TILE_SIZE*Config.SIZE_MULT+Game.player.xOffset+e.getCol()*Config.TILE_SIZE*Config.SIZE_MULT+e.getxOffset());
			e.setY(Config.HEIGHT/2-Game.player.row*Config.TILE_SIZE*Config.SIZE_MULT+Game.player.yOffset+e.getRow()*Config.TILE_SIZE*Config.SIZE_MULT+e.getyOffset());
		}
		for(int i = 0; i < drops.size(); ++i) {
			Item e = drops.get(i);
			if(e.isPickedup()) {
				drops.remove(e);
				--i;
				continue;
			}
			e.update();
			e.setX(Config.WIDTH/2-Game.player.col*Config.TILE_SIZE*Config.SIZE_MULT+Game.player.xOffset+e.getCol()*Config.TILE_SIZE*Config.SIZE_MULT+e.getxOffset());
			e.setY(Config.HEIGHT/2-Game.player.row*Config.TILE_SIZE*Config.SIZE_MULT+Game.player.yOffset+e.getRow()*Config.TILE_SIZE*Config.SIZE_MULT+e.getyOffset());
		}
	}
	
	public void render(Graphics g) {
		//System.out.println("C:R = "+Game.player.col+":"+Game.player.row);
		currentRoom.render(g, Config.WIDTH/2-Game.player.col*Config.TILE_SIZE*Config.SIZE_MULT+Game.player.xOffset,
				Config.HEIGHT/2-Game.player.row*Config.TILE_SIZE*Config.SIZE_MULT+Game.player.currImg.getWidth()*Config.P_MULT/2+Game.player.yOffset);
		for(Item i: Level.drops) {
			i.render(g);
		}
		for(Enemy e: enemies) {
			e.render(g);
		}
		if(bossFight || boss.distance < 30) {
			AudioGalery.reaperNoise.continueLoop();
			music.stopClip();
			noiseOff = false;
			g.drawImage(reaperAura, 0, 0, Config.WIDTH, Config.HEIGHT, null);
		}
		else {
			if(!noiseOff) {
				AudioGalery.reaperNoise.stopClip();
				noiseOff = true;
			}
		}
	}
	
}
