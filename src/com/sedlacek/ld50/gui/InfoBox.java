package com.sedlacek.ld50.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.sedlacek.ld50.entities.Player;
import com.sedlacek.ld50.items.Item;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Entity;

public class InfoBox extends GUIObject {
	
	private Entity e;
	private String header, text;
	private long timeoutms;
	private long startTime;
	
	public InfoBox(Entity e, int x, int y, String header, String text, long timeoutms) {
		this.e = e;
		this.x = x;
		this.y = y;
		this.header = header;
		this.text = text;
		this.timeoutms = timeoutms;
		this.startTime = System.currentTimeMillis();
	}

	@Override
	protected void update() {
		if(System.currentTimeMillis() - startTime >= timeoutms) {
			this.done = true;
		}
	}

	@Override
	protected void render(Graphics g) {
		g.setColor(new Color(40,40,40,200));
		g.setFont(new Font("DorFont03", Font.PLAIN, 8*Config.SIZE_MULT));
		int w = g.getFontMetrics().stringWidth(text)+20;
		g.setFont(new Font("DorFont03", Font.BOLD, 8*Config.SIZE_MULT));
		int hw = g.getFontMetrics().stringWidth(header)+20;
		int mw = hw > w ? hw : w;
		g.fillRect(x-mw/2, y, mw, 8*Config.SIZE_MULT*2+20);
		g.setColor(new Color(210,210,210,255));
		g.drawString(header, x-mw/2+10+mw/2-hw/2, y+8*Config.SIZE_MULT);
		g.setFont(new Font("DorFont03", Font.PLAIN, 8*Config.SIZE_MULT));
		g.setColor(new Color(250,250,250,255));
		g.drawString(text, x-mw/2+10+mw/2-w/2, y+8*Config.SIZE_MULT*2+5);
	}


}
