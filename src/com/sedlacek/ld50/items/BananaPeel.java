package com.sedlacek.ld50.items;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.graphics.SpriteSheet;
import com.sedlacek.ld50.gui.GUIHandler;
import com.sedlacek.ld50.gui.InfoBox;
import com.sedlacek.ld50.level.Level;
import com.sedlacek.ld50.main.AudioGalery;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Game;

public class BananaPeel extends Item {

	public static final int ID = 10;
	private static SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/items.png"));
	
	public BananaPeel(int col, int row) {
		super(col, row);
		this.name = "Banana peel";
		this.desc = "Slows down Reaper.";
		this.id = ID;
		this.w = 8;
		this.h = 8;
		this.currImg = ss.grabImage(2, 2, w, h, 16);
		this.limit = 2;
	}
	
	@Override
	public void pickup() {
		Level.boss.distance += r.nextInt(60)+40;
		GUIHandler.objects.add(new InfoBox(null, Config.WIDTH/2, Config.HEIGHT-200, name, desc, 2*1000));
		if(Game.soundOn) {
			AudioGalery.banana.playClip();
		}
	}
}
