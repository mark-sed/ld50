package com.sedlacek.ld50.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sedlacek.ld50.entities.Boss;
import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;
import com.sedlacek.ld50.main.Config;

public class BossIndicator extends GUIObject {
	
	private Boss boss;
	private BufferedImage arrow;
	
	public BossIndicator(Boss boss) {
		this.boss = boss;
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
		this.arrow = ss.grabImage(1, 3, 5, 4, 16);
		this.w = 5*Config.SIZE_MULT*2;
		this.h = 4*Config.SIZE_MULT*2;
		this.x = Config.WIDTH/2-w/2;
		this.y = Config.HEIGHT-h-50;
	}

	@Override
	protected void update() {
		
	}

	@Override
	protected void render(Graphics g) {
		if(hide)
			return;
		g.drawImage(arrow, x, y, w, h, null);
		g.setColor(new Color(230,50,50));
		g.setFont(new Font("DorFont03", Font.BOLD, 8*Config.SIZE_MULT));
		g.drawString(boss.distance+" m", x+w/2-g.getFontMetrics().stringWidth(boss.distance+" m")/2, y-10);
	}

}
