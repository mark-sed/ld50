package com.sedlacek.ld50.items;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import com.sedlacek.ld50.level.Level;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Entity;
import com.sedlacek.ld50.main.Game;

public abstract class Item extends Entity {

	protected String name, desc;
	protected int id;
	protected int amount;
	private int offY;
	private boolean up;
	protected boolean pickedup;
	static Random r = new Random();
	protected int limit;
	protected boolean inShop;
	
	public Item(int col, int row) {
		super(col, row);
		this.col = col;
		this.row = row;
		this.amount = 1;
		this.limit = 1;
	}
	
	public void update() {
		if(!inShop && this.col == Game.player.col && this.row == Game.player.row && !pickedup) {
			pickup();
		}
	}
	
	public static Item getItem(int col, int row, float common, float rare, float epic, float legendary) {
		float ch = r.nextFloat();
		Item item = null;
		if(ch < legendary) {
			int i = r.nextInt(2);
			if(i == 0 && Game.player.canGainItem(Wings.ID)) {
				item = new Wings(col, row);
			}
			else {
				item = new Scissors(col, row);
			}
		}
		else if(ch < epic) {
			int i = r.nextInt(3);
			if(i == 0 && Game.player.canGainItem(Shield.ID)) {
				item = new Shield(col, row);
			}
			if(i == 1 && Game.player.canGainItem(PiggyBank.ID)) {
				item = new PiggyBank(col, row);
			}
			else {
				item = new Orange(col, row);
			}
		}
		else if(ch < rare) {
			int i = r.nextInt(3);
			if(i == 0 && Game.player.canGainItem(EmptyHeart.ID)) {
				item = new EmptyHeart(col, row);
			}
			else if(i == 1 && Game.player.canGainItem(Bandages.ID)) {
				item = new Bandages(col, row);
			}
			else {
				item = new SharpeningStone(col, row);
			}
		}
		else if(ch < common) {
			int i = r.nextInt(5);
			if(i == 0)
				item = new Heart(col, row);
			else
				item = new Coins(col, row, r.nextInt(1)+1);
		}
		return item;
	}
	
	public static void dropItem(int col, int row, float common, float rare, float epic, float legendary) {
		Item item = getItem(col, row, common, rare, epic, legendary);
		if(item == null)
			item = new Coins(col, row, 1);
		Level.dropItem(item);
	}
	
	public void pickup() {
		this.pickedup = true;
		Game.player.addItem(this);
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
		g.drawImage(this.currImg, x, y+offY, w*Config.P_MULT, h*Config.P_MULT, null);
	}

	public void renderAsIcon(Graphics g, int nx, int ny, int mult) {
		g.drawImage(this.currImg, nx, ny, w*mult, h*mult, null);
		if(amount > 1) {
			g.setFont(new Font("DorFont03", Font.BOLD, 8*Config.SIZE_MULT));
			g.setColor(Color.WHITE);
			g.drawString(""+amount, nx+w*mult-g.getFontMetrics().stringWidth(""+amount), ny+h*mult);
		}
	}
	
	public boolean isPickedup() {
		return pickedup;
	}

	public void setPickedup(boolean pickedup) {
		this.pickedup = pickedup;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isInShop() {
		return inShop;
	}

	public void setInShop(boolean inShop) {
		this.inShop = inShop;
	}
	
	
}
