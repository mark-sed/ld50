package com.sedlacek.ld50.level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.sedlacek.ld50.items.BananaPeel;
import com.sedlacek.ld50.items.Item;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Game;

public class Shop extends Tile {

	public static final int ID = 2;
	private int price;
	private Item item;
	private int col, row;
	
	public Shop(Item item, int price) {
		this.item = item;
		this.price = price;
		if(item != null)
			item.setInShop(true);
		this.img = ss.grabImage(1, 4, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
	}
	
	@Override
	public void update() {
		if(item == null)
			return;
		if(!Game.player.canGainItem(item.getId())) {
			item = null;
			return;
		}
		
		if(this.col == Game.player.col && this.row == Game.player.row) {
			if(Game.player.getCoins() >= price) {
				if(item.getId() == BananaPeel.ID) {
					item.pickup();
				}
				else {
					Game.player.addItem(item);
				}
				Game.player.removeCoins(price);
				this.item = null;
			}
		}
		if(item != null) {
			item.update();
		}
	}
	
	@Override
	public void render(Graphics g, int x, int y) {
		g.drawImage(img, x, y, Config.TILE_SIZE*Config.SIZE_MULT, Config.TILE_SIZE*Config.SIZE_MULT, null);
		if(item != null) {
			item.setX(x);
			item.setY(y-Config.TILE_SIZE*Config.SIZE_MULT/2-5);
			item.render(g);
			g.setFont(new Font("DorFont03", Font.BOLD, 6*Config.SIZE_MULT));
			g.setColor(Color.WHITE);
			g.drawString("$"+price, x+Config.TILE_SIZE*Config.SIZE_MULT/2-g.getFontMetrics().stringWidth("$"+price)/2, y+Config.TILE_SIZE*Config.SIZE_MULT);
		}
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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
	
	
}
