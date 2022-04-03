package com.sedlacek.ld50.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sedlacek.ld50.entities.Player;
import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Game;
import com.sedlacek.ld50.main.Game.State;

public class CoinBar extends GUIObject {
	
	private Player e;
	private BufferedImage icon;
	
	public CoinBar(Player e) {
		this.e = e;
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
		this.icon = ss.grabImage(1, 2, 9, 9, 16);
		this.y = 20;
	}

	@Override
	protected void update() {
		
		
	}

	@Override
	protected void render(Graphics g) {
		if(hide || Game.state == State.INTRO)
			return;
		this.w = this.icon.getWidth()*Config.SIZE_MULT;
		this.h = this.icon.getHeight()*Config.SIZE_MULT;
		this.x = Config.WIDTH-w-65;
		g.drawImage(this.icon, x, y, w, h, null);
		g.setFont(new Font("DorFont03", Font.BOLD, 8*Config.SIZE_MULT));
		g.setColor(Color.WHITE);
		g.drawString(""+e.getCoins(), x+w+10, y+8*Config.SIZE_MULT);
	}

}
