package com.sedlacek.ld50.level;

import java.util.Random;

import com.sedlacek.ld50.items.BananaPeel;
import com.sedlacek.ld50.items.Item;
import com.sedlacek.ld50.main.Game;

public class Dungeon extends Room {

	private Random r = new Random();
	
	private void placeTile(int i, int j) {
		tiles[i][j] = new DungeonTile();
		occupied.add(new Pairs(i, j));
	}
	
	public Dungeon(int width, int height, int numTiles) {
		super(width, height);
		int x = width/2;
		int y = height;
		int placed = 0;
		boolean placeShop = r.nextFloat() < 0.3f;
		while(placed < numTiles) {
			int rw = r.nextInt(3)+1+(placed == 0 ? 2 : 0);
			int rh = r.nextInt(3)+1+(placed == 0 ? 2 : 0);
			int offsetX = 1+r.nextInt(rw);
			int offsetY = 1+r.nextInt(rh);
			if(r.nextBoolean()) {
				x -= rw-offsetX;
			}
			else {
				x += rw-2;
			}
			y -= rh-offsetY;
			for(int a = 0; a < rw; ++a) {
				for(int b = 0; b < rh; ++b) {
					if(x+a >= 0 && x+a < width-1 
							&& y+b >= 0 && y+b < height-1
							&& tiles[x+a][y+b] == null) {
						placeTile(x+a, y+b);
						++placed;
					}
				}
			}
		}
		tiles[x][y] = new Entrance();
		Game.player.col = occupied.get(0).getX();
		Game.player.row = occupied.get(0).getY();

		int mines = 0;
		if(Game.level != null)
			mines = r.nextInt(Game.level.number/6+2);
		for(int i = 0; i < mines; ++i) {
			Pairs p = occupied.get(r.nextInt(occupied.size()));
			if((p.getX() == Game.player.col && p.getY() == Game.player.row)
					|| (p.getX() == x || p.getY() == y)) {
				--i;
				continue;
			}
			tiles[p.getX()][p.getY()] = new Mine();
		}
		
		Pairs shopPos = null;
		while(shopPos == null 
				|| (shopPos.getX() == x && shopPos.getY() == y)
				|| (shopPos.getX() == Game.player.col && shopPos.getY() == Game.player.row)) {
			shopPos = occupied.get(r.nextInt(occupied.size()));
			shopPos.setX(shopPos.getX()-1);
		}
		int sX = shopPos.getX();
		int sY = shopPos.getY();
		Shop shop = null;
		Item item = null;
		if(placeShop) {
			int rar = r.nextInt(3);
			if(rar == 0) {
				item = Item.getItem(sX, sY, 0.f, 1.0f, 0.f, 0.f);
				shop = new Shop(item, 8);
			}
			else if(rar == 1) {
				item = Item.getItem(sX, sY, 0.f, .0f, 1.f, 0.f);
				shop = new Shop(item, 12);
			}
			else {
				item = Item.getItem(sX, sY, 0.f, 1.0f, 0.f, 1.f);
				shop = new Shop(item, 20);
			}
		}
		else {
			item = new BananaPeel(sX, sY);
			shop = new Shop(item, 1);
		}
		shop.setCol(sX);
		shop.setRow(sY);
		if(item != null) {
			tiles[sX][sY] = shop;
			occupied.add(new Pairs(sX, sY));
		}
	}
}
