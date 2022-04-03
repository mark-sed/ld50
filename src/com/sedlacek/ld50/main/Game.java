package com.sedlacek.ld50.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Random;

import javax.swing.JFrame;

import com.sedlacek.ld50.entities.Player;
import com.sedlacek.ld50.graphics.ImageLoader;
import com.sedlacek.ld50.gui.GUIHandler;
import com.sedlacek.ld50.gui.ItemBar;
import com.sedlacek.ld50.gui.Menu;
import com.sedlacek.ld50.gui.Story;
import com.sedlacek.ld50.level.BossRoom;
import com.sedlacek.ld50.level.Level;

/*
 * @Author: Marek Sedlacek
 * mr.mareksedlacek@gmail.com
 * Twitter: @Sedlacek
 * 
 */

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	public static Rectangle windowRect;
	
	private static int infoFPS;
	private static int infoTicks;
	
	public static boolean soundOn = true;
	public static boolean musicOn = true;
	
	public static Random r = new Random();
	
	public static Game game; 
	public static Level level;
	public static Player player;
	public static Menu menu;
	public static Story story;
	public static GUIHandler guiHandler;
	public static JFrame frame;
	public static Thread thread;
	
	public static enum State{
		GAME,
		INTRO,
		GAME_OVER,
		MENU,
		GAME_WON
	}
	
	public static enum Turn {
		PLAYER,
		ENEMIES
	}
	
	public static State state;
	public static Turn turn;
	
	private BufferStrategy bs;
	private Graphics g;
	private static KeyManager keyManager;
	private static MouseManager mouseManager;
	private long lastTime;

	public static boolean paused = false;

	private static boolean mute = false;
	private BufferedImage statsScroll;
	public long deathTime;

	public Game(){
		keyManager = new KeyManager();
		this.addKeyListener(keyManager);
		mouseManager = new MouseManager();
		this.addMouseListener(mouseManager);
		this.addMouseMotionListener(mouseManager);
		this.addMouseWheelListener(mouseManager);
		
		//Sheet = ImageLoader.loadNS("/furniture.png");
		
		//FONTS
		try{
			InputStream font = getClass().getResourceAsStream("/DorFont01.ttf");
			 GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, font)); 
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			InputStream font = getClass().getResourceAsStream("/DorFont02.ttf");
			 GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, font)); 
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			InputStream font = getClass().getResourceAsStream("/DorFont03.ttf");
			 GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, font)); 
		}catch(Exception e){
			e.printStackTrace();
		}
		
		statsScroll = ImageLoader.loadNS("/statsScroll.png");

		restart();
	}
	
	private void update(){
		if(state == State.GAME){
			GUIHandler.update();
			level.update();
			player.update();
		}else if(state == State.MENU){
			menu.update();
		}else if(state == State.GAME_OVER){
			GUIHandler.update();
		}else if(state == State.INTRO) {
			story.update();
			GUIHandler.update();
		}
		
		if(System.currentTimeMillis() - lastTime >= 1000){
			this.lastTime = System.currentTimeMillis();
			this.fixedUpdate();
		}
		if(mouseManager.LClicked){
			mouseManager.LClicked = false;
		}
	}
	
	public void restart() {
		state = State.MENU;
		turn = Turn.PLAYER;
		guiHandler = new GUIHandler();
		GUIHandler.objects.clear();
		Level.drops.clear();
		Level.enemies.clear();
		Player.items.clear();
		menu = new Menu();
		story = new Story();
		player = new Player(0,0);
		level = new Level();
	}
	
	public void resize() {
		Dimension size = new Dimension(Config.WIDTH, Config.HEIGHT);
		frame.setSize(size);
		frame.setResizable(false);
		frame.setPreferredSize(size);
		frame.setMaximumSize(size);
		frame.setMinimumSize(size);
		frame.pack();
		frame.setVisible(true);
	}

	private void fixedUpdate(){
		// Invoked every second
		if(state == State.GAME){
			// NOT USED 
		}
	}

	public static void mute(){
		if(mute){
			//AudioGalery.music.loop();
			if(level.bossFight) {
				BossRoom.music.loop();
			}
			else {
				Level.music.loop();
			}
			musicOn = true;
		}else{
			Level.music.stopClip();
			BossRoom.music.stopClip();
			musicOn = false;
		}
		mute = !mute;
	}
	
	private void input(){
		if(getKeyManager().keys[KeyManager.F1]){
			if(Config.showInfo) {
				Config.showInfo = false;
			}else {
				Config.showInfo = true;
			}
			getKeyManager().keys[KeyManager.F1] = false;
		}
		if(getKeyManager().keys[KeyManager.M] && Game.state == State.GAME){
			mute();
			getKeyManager().keys[KeyManager.M] = false;
		}
		if(state == State.GAME){
			if(turn == Turn.PLAYER) {
				player.input();
			}
		}
		if(state == State.GAME_OVER) {
			if(KeyManager.anyKeyPressed && System.currentTimeMillis() - deathTime > 1000) {
				restart();
			}
		}
	}
	
	private void render(){
		bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(2);
			return;
		}
		
		g = bs.getDrawGraphics();
		//Draw
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);

		if(state == State.GAME || state == State.GAME_OVER){
			level.render(g);
			if(state != State.GAME_OVER) {
				player.render(g);
			}
			GUIHandler.render(g);
		}
		else if(state == State.MENU){
			menu.render(g);
		}
		else if(state == State.INTRO) {
			story.render(g);
			GUIHandler.render(g);
		}
		
		int yStart = 38*Config.SIZE_MULT;
		if(state == State.GAME_OVER){
			g.setColor(new Color(0,0,0,150));
			g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);
			g.setColor(Color.white);
			g.setFont(new Font("DorFont03", Font.BOLD, 12*Config.SIZE_MULT));
			g.drawString("You died", Config.WIDTH/2-g.getFontMetrics().stringWidth("You died")/2, yStart);
			g.setColor(Color.white.darker());
			g.setFont(new Font("DorFont03", Font.BOLD, 8*Config.SIZE_MULT));
			g.drawString("The inevitable had come!", Config.WIDTH/2-g.getFontMetrics().stringWidth("The inevitable had come!")/2, yStart+10*Config.SIZE_MULT);
		
			if(System.currentTimeMillis() - deathTime > 1000 && (System.currentTimeMillis() - deathTime) % 1400 < 700 ) {
				g.setColor(menu.textColor);
				g.setFont(new Font("DorFont03", Font.BOLD, 10*Config.SIZE_MULT));
				g.drawString("Press any key to continue...", Config.WIDTH/2-g.getFontMetrics().stringWidth("Press any key to continue...")/2, yStart+12*Config.SIZE_MULT*2);
			}
			
			// Stats
			int statsX = 32*Config.SIZE_MULT;
			int statsY = 96*Config.SIZE_MULT;
			int fontH = 12*Config.SIZE_MULT;
			g.drawImage(statsScroll, 0, 0, Config.WIDTH, Config.HEIGHT, null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("DorFont03", Font.BOLD, 12*Config.SIZE_MULT));
			g.drawString("Rooms escaped: "+level.number, statsX, statsY+fontH);
			g.setColor(new Color(240,240,240,255));
			g.setFont(new Font("DorFont03", Font.PLAIN, 10*Config.SIZE_MULT));
			g.drawString("Reaper escapes: "+level.reaperEscapes, statsX, statsY+fontH*2+5*1);
			g.drawString("Moves made: "+player.movesDone, statsX, statsY+fontH*3+5*2);
			g.drawString("Damage done: "+player.dmgDone, statsX, statsY+fontH*4+5*3);
			g.drawString("Damage taken: "+player.dmgTaken, statsX, statsY+fontH*5+5*4);
			g.drawString("Items collected: ", statsX, statsY+fontH*6+5*5);
			ItemBar allItems = new ItemBar(Game.player);
			allItems.setX(statsX);
			allItems.setY(statsY+fontH*6+5*7);
			allItems.render(g);			
		}
		if(state == State.GAME_WON){
			// TODO: Could the player win by killing the boss?
			g.setColor(Color.black);
			g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);
			g.setColor(Color.white);
			g.setFont(new Font("DorFont03", Font.BOLD, 12*Config.SIZE_MULT));
			g.drawString("You won?! Impossible!", Config.WIDTH/2-g.getFontMetrics().stringWidth("You won?! Impossible!")/2, Config.HEIGHT/2-30);
		}
		if(state == State.GAME_WON || state == State.GAME_OVER) {
			g.setColor(Color.white.darker().darker());
			g.setFont(new Font("DorFont03", Font.PLAIN, 6*Config.SIZE_MULT));
			g.drawString("Made for Ludum Dare 50 by Marek Sedlacek (Twitter: @Sedlacek)", Config.WIDTH/2-g.getFontMetrics().stringWidth("Made for Ludum Dare 49 by Marek Sedlacek (Twitter: @Sedlacek)")/2, Config.HEIGHT-6*Config.SIZE_MULT*4);
		}
		
		if(Config.showInfo){
			g.setFont(new Font("Consolas", Font.PLAIN, 11));
			g.setColor(Color.WHITE);
			g.drawString("FPS/Ticks: " + infoFPS + "/" + infoTicks, Config.WIDTH-100, 12);
		}
		//Dispose
		bs.show();
		g.dispose();
	}
	
	public static KeyManager getKeyManager(){
		return keyManager;
	}
	
	public static MouseManager getMouseManager(){
		return mouseManager;
	}
	
	public static Rectangle getMouseRect(){
		return mouseManager.getMouseRect();
	}
	
	public void run(){
		this.requestFocus();
		double ns = 1000000000 / Config.fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		int frames = 0;
		
		while(Config.running){
			now = System.nanoTime();
			delta += (now - lastTime) / ns;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				input();
				update();
				ticks++;
				delta--;
			}
			
			render();
			frames++;
			
			if(timer >= 1000000000){
				//System.out.println(ticks + "   " + frames);
				infoTicks = ticks;
				infoFPS = frames;
				ticks = 0;
				frames = 0;
				timer = 0;
			}
		}
		
		stop();
	}
	
	
	
	public static void main(String [] args){
		if(args.length > 0){
			if(args[0].equals("-d") || args[0].equals("--debug")){
				Config.debugMode = true;
				Config.showInfo = true;
				Config.debug("Debug mode on");
			}
		}

		game = new Game();
		
		frame = new JFrame(Config.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Config.WIDTH=screenSize.Config.WIDTH;
		Config.HEIGHT=screenSize.Config.HEIGHT;
	    frame.setBounds(0,0,screenSize.Config.WIDTH, screenSize.Config.HEIGHT);
	    frame.setUndecorated(true);*/
	    frame.setBounds(0,0, Config.WIDTH, Config.HEIGHT);
	    windowRect = new Rectangle(0, 0, Config.WIDTH, Config.HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	    
		frame.add(game);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		
		game.start();
	}
	
	public void start(){
		if(Config.running)
			return;
		Config.running = true;
		thread = new Thread(this, "ld50");
		thread.start();
	}
	
	public void stop(){
		if(!Config.running)
			return;
		Config.running = false;
	}
}
