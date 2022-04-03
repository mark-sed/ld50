package com.sedlacek.ld50.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.level.Level;
import com.sedlacek.ld50.level.Room;
import com.sedlacek.ld50.main.Config;
import com.sedlacek.ld50.main.Game;
import com.sedlacek.ld50.main.Game.State;

public class Menu {

	private BufferedImage background;
	
	private Button playB, musicB, soundB, exitB;
	
	private long lastMove;
	public Color bckgColor = new Color(76, 34, 98);
	public Color textColor = new Color(247, 226, 107);
	
	public Menu() {
		background = ImageLoader.loadNS("/menu2.png");
		int space = 5*Config.SIZE_MULT;
		try {
			playB = new Button("New game", -10*Config.SIZE_MULT, 108*Config.SIZE_MULT, 100*Config.SIZE_MULT, 20*Config.SIZE_MULT, bckgColor, textColor, this.getClass().getDeclaredMethod("newGameClicked"), this);
			musicB = new Button("Music: ON", -40*Config.SIZE_MULT, 108*Config.SIZE_MULT+20*Config.SIZE_MULT*1+space*1, 100*Config.SIZE_MULT, 20*Config.SIZE_MULT, bckgColor, textColor, this.getClass().getDeclaredMethod("musicClicked"), this);
			soundB = new Button("Sound: ON", -70*Config.SIZE_MULT, 108*Config.SIZE_MULT+20*Config.SIZE_MULT*2+space*2, 100*Config.SIZE_MULT, 20*Config.SIZE_MULT, bckgColor, textColor, this.getClass().getDeclaredMethod("soundClicked"), this);
			exitB = new Button("Scale: "+Config.SIZE_MULT, -100*Config.SIZE_MULT, 108*Config.SIZE_MULT+20*Config.SIZE_MULT*3+space*3, 100*Config.SIZE_MULT, 20*Config.SIZE_MULT, bckgColor, textColor, this.getClass().getDeclaredMethod("scaleClicked"), this);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!Game.musicOn) {
			musicB.setText("Music: OFF");
		}
		if(!Game.soundOn) {
			soundB.setText("Sound: OFF");
		}
	}
	
	public void update() {
		int speed = 4*Config.SIZE_MULT;
		if(System.currentTimeMillis() - lastMove > 1) {
			if(playB.getX() < Config.WIDTH/2-50*Config.SIZE_MULT) {
				playB.setX(playB.getX()+speed);
			}
			else if(playB.getX() > Config.WIDTH/2-50*Config.SIZE_MULT) {
				playB.setX(Config.WIDTH/2-50*Config.SIZE_MULT);
			}
			
			if(musicB.getX() < Config.WIDTH/2-50*Config.SIZE_MULT) {
				musicB.setX(musicB.getX()+speed+2);
			}
			else if(musicB.getX() > Config.WIDTH/2-50*Config.SIZE_MULT) {
				musicB.setX(Config.WIDTH/2-50*Config.SIZE_MULT);
			}
			
			if(soundB.getX() < Config.WIDTH/2-50*Config.SIZE_MULT) {
				soundB.setX(soundB.getX()+speed+4);
			}
			else if(soundB.getX() > Config.WIDTH/2-50*Config.SIZE_MULT) {
				soundB.setX(Config.WIDTH/2-50*Config.SIZE_MULT);
			}
			
			if(exitB.getX() < Config.WIDTH/2-50*Config.SIZE_MULT) {
				exitB.setX(exitB.getX()+speed+6);
			}
			else if(exitB.getX() > Config.WIDTH/2-50*Config.SIZE_MULT) {
				exitB.setX(Config.WIDTH/2-50*Config.SIZE_MULT);
			}
			lastMove = System.currentTimeMillis();
		}
		
		playB.update();
		musicB.update();
		soundB.update();
		exitB.update();
	}
	
	public void newGameClicked() {
		Game.state = State.INTRO;
		Game.story.start();
	}
	
	public void musicClicked() {
		Game.musicOn = !Game.musicOn;
		if(Game.musicOn) {
			Level.music.loop();
			musicB.setText("Music: ON");
		}
		else {
			Level.music.stopClip();
			musicB.setText("Music: OFF");
		}
	}
	
	public void soundClicked() {
		Game.soundOn = !Game.soundOn;
		if(Game.soundOn) {
			soundB.setText("Sound: ON");
		}
		else {
			soundB.setText("Sound: OFF");
		}
	}
	
	public void scaleClicked() {
		if(Config.SIZE_MULT >= 4)
			Config.SIZE_MULT = 1;
		else
			Config.SIZE_MULT++;
		Config.P_MULT = Config.SIZE_MULT*2;
		Config.WIDTH = 240*Config.SIZE_MULT;
		Config.HEIGHT = 240*Config.SIZE_MULT;
		Game.game.resize();
		Game.game.restart();
	}
	
	public void render(Graphics g) {
		g.drawImage(Room.background, 0, 0, Config.WIDTH, Config.HEIGHT, null);
		g.drawImage(background, 0, 0, Config.WIDTH, Config.HEIGHT, null);
		

		playB.render(g);
		musicB.render(g);
		soundB.render(g);
		exitB.render(g);
	}
}
