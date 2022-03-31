package com.sedlacek.ld50.main;

import java.awt.Graphics;

import com.sedlacek.ld50.graphics.Animation;

public abstract class Entity extends GameObject {
	
	protected Animation currAnim;
	protected Animation idleAnim;
	protected Animation idleCombatAnim;
	protected Animation attackAnim;
	protected int widestAnim;
	
	
	public void update() {
		if(collisionBox != null) {
			collisionBox.update();
		}
	}
	
	public void nextTurnTrigger() {
		
	}
	
	public void nextLevelTriggered() {
		
	}
	
	public void skippedTurn() {
		
	}
	
	public void attack(Entity ...enemies) {
		
	}
	
	public abstract void fixedUpdate();
	
	public void render(Graphics g) {
		/*if(HP <= 0)
			return;
		if(currAnim != null) {
			currAnim.runAndDrawAnimation(g, x, y, Config.SIZE_MULT);
		}
		
		float hpMaxWidth = 50;
		float hpWidth = hpMaxWidth / maxHP * HP;
		
		// HP bar
		g.setColor(new Color(184, 19, 7));
		g.fillRect(x-20+hpBarOffset, y-20-hpBarOffsetY, (int)hpWidth, 10);
		g.setColor(Color.gray);
		g.drawRect(x-20+hpBarOffset, y-20-hpBarOffsetY, (int)hpMaxWidth, 10);*/
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
	
}
