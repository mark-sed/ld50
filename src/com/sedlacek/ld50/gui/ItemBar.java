package com.sedlacek.ld50.gui;

import java.awt.Graphics;

import com.sedlacek.ld50.entities.Player;
import com.sedlacek.ld50.items.Item;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Entity;

public class ItemBar extends GUIObject {
	
	public ItemBar(Entity e) {
		this.x = 20;
		this.y = 20+8*Config.SIZE_MULT+20;
	}

	@Override
	protected void update() {
		
		
	}

	@Override
	public void render(Graphics g) {
		if(hide)
			return;
		int space = 5;
		for(int i = 0; i < Player.items.size(); ++i) {
			Item it = Player.items.get(i);
			int w = it.getCurrImg().getWidth()*Config.P_MULT;
			it.renderAsIcon(g, x+i*w+i*space, y, Config.P_MULT);
		}
	}

}
