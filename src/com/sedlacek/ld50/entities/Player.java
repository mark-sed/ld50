package com.sedlacek.ld50.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;
import com.sedlacek.ld50.gui.CoinBar;
import com.sedlacek.ld50.gui.GUIHandler;
import com.sedlacek.ld50.gui.HPBar;
import com.sedlacek.ld50.gui.InfoBox;
import com.sedlacek.ld50.gui.ItemBar;
import com.sedlacek.ld50.gui.Lightning;
import com.sedlacek.ld50.items.Bandages;
import com.sedlacek.ld50.items.EmptyHeart;
import com.sedlacek.ld50.items.Item;
import com.sedlacek.ld50.items.Orange;
import com.sedlacek.ld50.items.PiggyBank;
import com.sedlacek.ld50.items.Scissors;
import com.sedlacek.ld50.items.SharpeningStone;
import com.sedlacek.ld50.items.Shield;
import com.sedlacek.ld50.items.Wings;
import com.sedlacek.ld50.level.Entrance;
import com.sedlacek.ld50.level.Level;
import com.sedlacek.ld50.level.Mine;
import com.sedlacek.ld50.main.AudioGalery;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Config.Direction;
import com.sedlacek.ld50.main.Entity;
import com.sedlacek.ld50.main.Game;
import com.sedlacek.ld50.main.KeyManager;

public class Player extends Entity {

	private BufferedImage imgUp;
	private BufferedImage imgDown;
	public BufferedImage currImg;
	public int xOffset;
	public int yOffset;
	private Direction faceDir;
	public int col, row;
	private long lastBreathTime;
	private boolean exhale;
	private int attackDmg;
	private int coins;
	
	public static String name = "Heeroik";
	
	private int hpRegen;
	private int regenGain;
	private int lastRegen;
	public HPBar hpbar;
	public CoinBar coinBar;
	public ItemBar itemBar;
	
	public int movesDone;
	public int dmgDone;
	public int dmgTaken;
	
	Random r = new Random();
	public static ArrayList<Item> items = new ArrayList<Item>();
	
	public Player(int col, int row) {
		super(col, row);
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/player.png"));
		this.w = 11;
		this.h = 13;
		this.imgUp = ss.grabImage(2, 2, w, h, Config.TILE_SIZE);
		this.imgDown = ss.grabImage(2, 1, w, h, Config.TILE_SIZE);
		this.currImg = this.imgDown;
		faceDir = Direction.UP;
		this.x = Config.WIDTH/2;
		this.y = Config.HEIGHT/2;
		this.col = col;
		this.row = row;
		this.yOffset = 10;
		this.attackDmg = 6;
		this.maxHp = 10;
		this.hp = maxHp;
		this.hpRegen = 4;
		this.lastRegen = hpRegen;
		this.regenGain = 1;
		this.coins = 0;
		
		this.coinBar = new CoinBar(this);
		GUIHandler.objects.add(this.coinBar);
		this.hpbar = new HPBar(this);
		GUIHandler.objects.add(this.hpbar);
		this.itemBar = new ItemBar(this);
		GUIHandler.objects.add(this.itemBar);
	}

	@Override
	public void fixedUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		
	}
	
	public void addItem(Item item) {
		if(item.getId() == EmptyHeart.ID) {
			GUIHandler.objects.add(new InfoBox(null, Config.WIDTH/2, Config.HEIGHT-200, item.getName(), item.getDesc(), 2*1000));
			this.maxHp+=2;
			return;
		}
		for(Item i: items) {
			if(i.getId() == item.getId()) {
				i.setAmount(i.getAmount() + item.getAmount());
				GUIHandler.objects.add(new InfoBox(null, Config.WIDTH/2, Config.HEIGHT-200, item.getName(), item.getDesc(), 2*1000));
				if(Game.soundOn) 
					AudioGalery.pickup.playClip();
				if(i.getLimit() > i.getAmount()) {
					i.setAmount(i.getLimit());
				}
				return;
			}
		}
		items.add(item);
		GUIHandler.objects.add(new InfoBox(null, Config.WIDTH/2, Config.HEIGHT-200, item.getName(), item.getDesc(), 3*1000));
		if(Game.soundOn) 
			AudioGalery.pickup.playClip();
	}
	
	public boolean canGainItem(int id) {
		for(Item i: items) {
			if(i.getId() == id) {
				if(i.getLimit() > i.getAmount()) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return true;
	}
	
	public void move(Direction dir) {
		faceDir = dir;
		if(dir == Direction.UP && row > 0) {
			Enemy e = Level.getEnemyAt(col, row-1);
			if(e != null) {
				attack(e);
			}
			else {
				if(Level.getTileID(col, row-1) >= 0) {
					--row;
					Level.boss.distance++;
				}
				else {
					return;
				}
			}
		}
		else if(dir == Direction.DOWN && row < Game.level.height-1) {
			Enemy e = Level.getEnemyAt(col, row+1);
			if(e != null) {
				attack(e);
			}
			else {
				if(Level.getTileID(col, row+1) >= 0) {
					++row;
					if(!hasItem(Wings.ID) || hasItem(Wings.ID) && r.nextBoolean())
						Level.boss.distance--;
				}
				else {
					return;
				}
			}
		}
		else if(dir == Direction.LEFT && col > 0) {
			Enemy e = Level.getEnemyAt(col-1, row);
			if(e != null) {
				attack(e);
			}
			else {
				if(Level.getTileID(col-1, row) >= 0) {
					--col;
				}
				else {
					return;
				}
			}
		}
		else if(dir == Direction.RIGHT && col < Game.level.width-1) {
			Enemy e = Level.getEnemyAt(col+1, row);
			if(e != null) {
				attack(e);
			}
			else {
				if(Level.getTileID(col+1, row) >= 0) {
					++col;
				}
				else {
					return;
				}
			}
		}
		if(Level.getTileID(col, row) == Entrance.ID) {
			Game.level.nextLevel();
		}
		turnEnd();
	}
	
	public static int itemAmount(int id) {
		for(Item it: items) {
			if(it.getId() == id) {
				return it.getAmount();
			}
		}
		return 0;
	}
	
	public static boolean hasItem(int id) {
		for(Item it: items) {
			if(it.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public void attack(Enemy e) {
		int dmg = this.attackDmg + itemAmount(SharpeningStone.ID)*2;
		if(hasItem(Scissors.ID)) {
			dmg *= 2;
		}
		this.dmgDone += dmg;
		if(Game.soundOn) {
			AudioGalery.hit.playClip();
		}
		e.reduceHp(dmg);
		this.reduceHp(e.getAttackDmg());
	}
	
	public void turnStart() {
		if(hp < maxHp) {
			// HP regen
			if(lastRegen <= 1) {
				gainHp(regenGain+itemAmount(Orange.ID));
				lastRegen = this.hpRegen-itemAmount(Bandages.ID);
			}
			else {
				--lastRegen;
			}
		}
	}
	
	public void turnEnd() {
		if(Level.getTileID(col, row) == Mine.ID) {
			if(Game.soundOn) {
				AudioGalery.explosion.playClip();
			}
			GUIHandler.objects.add(new Lightning(col, row, 3));
			this.reduceHp(5);
		}
		movesDone++;
		Game.level.endTurn();
	}
	
 	public void input() {
		if(Game.getKeyManager().keys[KeyManager.S] || Game.getKeyManager().keys[KeyManager.DOWN]) {
			move(Direction.DOWN);
			Game.getKeyManager().keys[KeyManager.DOWN] = false;
			Game.getKeyManager().keys[KeyManager.S] = false;
		}
		else if(Game.getKeyManager().keys[KeyManager.W] || Game.getKeyManager().keys[KeyManager.UP]) {
			move(Direction.UP);
			Game.getKeyManager().keys[KeyManager.UP] = false;
			Game.getKeyManager().keys[KeyManager.W] = false;
		}
		else if(Game.getKeyManager().keys[KeyManager.A] || Game.getKeyManager().keys[KeyManager.LEFT]) {
			move(Direction.LEFT);
			Game.getKeyManager().keys[KeyManager.LEFT] = false;
			Game.getKeyManager().keys[KeyManager.A] = false;
		}
		else if(Game.getKeyManager().keys[KeyManager.D] || Game.getKeyManager().keys[KeyManager.RIGHT]) {
			move(Direction.RIGHT);
			Game.getKeyManager().keys[KeyManager.RIGHT] = false;
			Game.getKeyManager().keys[KeyManager.D] = false;
		}
		
		// Cheats
		if(Game.getKeyManager().keys[KeyManager.F8] && Game.getKeyManager().keys[KeyManager.C]) {
			gainCoins(10);
			Game.getKeyManager().keys[KeyManager.C] = false;
		}
		else if(Game.getKeyManager().keys[KeyManager.F8] && Game.getKeyManager().keys[KeyManager.N]) {
			Game.level.nextLevel();
			Game.getKeyManager().keys[KeyManager.N] = false;
		}
		else if(Game.getKeyManager().keys[KeyManager.F8] && Game.getKeyManager().keys[KeyManager.H]) {
			maxHp += 2;
			hp += 2;
			Game.getKeyManager().keys[KeyManager.H] = false;
		}
	}

	@Override
	public void render(Graphics g) {
		int jumpY = 0;
		if(System.currentTimeMillis() - this.lastHitTime < 200) {
			jumpY = 20;
		}
		else if(System.currentTimeMillis() - this.lastBreathTime > 500) {
			if(exhale) {
				h -= 1;
				y += 1*Config.P_MULT;
			}				
			else {
				h += 1;
				y -= 1*Config.P_MULT;
			}
			exhale = !exhale;
			lastBreathTime = System.currentTimeMillis();
		}
		if(faceDir == Direction.UP) {
			this.currImg = imgUp;
			this.xOffset = 0;
			x = Config.WIDTH/2-4*Config.P_MULT; // Move camera not player
			g.drawImage(this.imgUp, x, y+jumpY, w*Config.P_MULT, h*Config.P_MULT-jumpY, null);
		}
		else if(faceDir == Direction.DOWN || faceDir == Direction.RIGHT) {
			this.currImg = imgDown;
			this.xOffset = -1*Config.P_MULT;
			x = Config.WIDTH/2;
			g.drawImage(this.imgDown, x, y+jumpY, w*Config.P_MULT, h*Config.P_MULT-jumpY, null);
		}
		else {
			this.currImg = imgDown;
			this.xOffset = -1*Config.P_MULT;
			x = Config.WIDTH/2;
			g.drawImage(this.imgDown, x+w*Config.SIZE_MULT, y+jumpY, -w*Config.P_MULT, h*Config.P_MULT-jumpY, null);
		}
	}
	
	@Override
	public void reduceHp(int amount) {
		for(Item i: items) {
			if(i.getId() == Shield.ID) {
				amount -= i.getAmount();
				break;
			}
		}
		if(amount <= 0) {
			return;
		}
		this.dmgTaken += amount;
		this.lastHitTime = System.currentTimeMillis();
		this.hp -= amount;
		if(hp <= 0) {
			AudioGalery.death.playClip();
			Game.state = Game.State.GAME_OVER;
			Game.game.deathTime = System.currentTimeMillis();
			KeyManager.anyKeyPressed = false;
		}
	}
	
	@Override
	public void gainHp(int amount) {
		if(amount <= 0 || hp >= maxHp) {
			return;
		}
		if(hp + amount > maxHp) { 
			amount = maxHp - hp;
		}
		this.hp += amount;
	}

	public int getCoins() {
		return coins;
	}

	public void gainCoins(int amount) {
		for(Item it: items) {
			if(it.getId() == PiggyBank.ID) {
				amount *= 2;
				break;
			}
		}
		if(Game.soundOn)
			AudioGalery.coin.playClip();
		this.coins += amount;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	public void removeCoins(int amount) {
		this.coins -= amount;
	}
}
