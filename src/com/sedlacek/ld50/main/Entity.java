package com.sedlacek.ld50.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.sedlacek.ld50.graphics.Animation;

public abstract class Entity extends GameObject {
	
	protected Animation currAnim;
	protected Animation idleAnim;
	protected Animation idleCombatAnim;
	protected Animation attackAnim;
	protected int widestAnim;
	protected BufferedImage currImg;
	protected int col;
	protected int row;
	protected int xOffset;
	protected int yOffset;
	
	protected int maxHp;
	protected int hp;
	protected int attackDmg;
	
	protected long lastBreathTime;
	protected long lastHitTime;
	private boolean exhale;
	private Random r = new Random();
	
	public Entity(int col, int row) {
		this.col = col;
		this.row = row;
		//lastBreathTime = System.currentTimeMillis() + r.nextInt(500);
	}
	
	public void update() {
		/*if(collisionBox != null) {
			collisionBox.update();
		}*/
	}
	
	public void fixedUpdate() {
		
	}
	
	public void render(Graphics g) {
		int jumpY = 0;
		if(System.currentTimeMillis() - this.lastHitTime < 200) {
			jumpY = 35;
		}
		else if(System.currentTimeMillis() - this.lastBreathTime > 500) {
			exhale = !exhale;
			lastBreathTime = System.currentTimeMillis();
		}
		g.drawImage(this.currImg, x, y+(exhale ? -1*Config.P_MULT : 0)+jumpY, w*Config.P_MULT, (h+(!exhale ? -1 : 0))*Config.P_MULT-jumpY, null);
	}
	
	public void dropLoot() {
		// Overridable method for entities that drop items
	}
	
	public void reduceHp(int amount) {
		if(amount <= 0) {
			return;
		}
		this.lastHitTime = System.currentTimeMillis();
		this.hp -= amount;
	}
	
	public void gainHp(int amount) {
		if(amount <= 0 || hp >= maxHp) {
			return;
		}
		if(hp + amount > maxHp) { 
			amount = maxHp - hp;
		}
		this.hp += amount;
	}

	public Animation getCurrAnim() {
		return currAnim;
	}

	public void setCurrAnim(Animation currAnim) {
		this.currAnim = currAnim;
	}

	public Animation getIdleAnim() {
		return idleAnim;
	}

	public void setIdleAnim(Animation idleAnim) {
		this.idleAnim = idleAnim;
	}

	public Animation getIdleCombatAnim() {
		return idleCombatAnim;
	}

	public void setIdleCombatAnim(Animation idleCombatAnim) {
		this.idleCombatAnim = idleCombatAnim;
	}

	public Animation getAttackAnim() {
		return attackAnim;
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

	public BufferedImage getCurrImg() {
		return currImg;
	}

	public void setCurrImg(BufferedImage currImg) {
		this.currImg = currImg;
	}

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}

	public void setAttackDmg(int attackDmg) {
		this.attackDmg = attackDmg;
	}
	
	
	
	
}
