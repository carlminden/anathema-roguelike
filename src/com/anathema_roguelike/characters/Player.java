/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.characters;


import com.anathema_roguelike.characters.ai.Faction;
import com.anathema_roguelike.characters.classes.Rogue;
import com.anathema_roguelike.characters.effects.Duration;
import com.anathema_roguelike.characters.effects.FixedDuration;
import com.anathema_roguelike.characters.effects.buffs.Buff;
import com.anathema_roguelike.characters.effects.modifiers.Modifier;
import com.anathema_roguelike.characters.stats.secondarystats.Concealment;
import com.anathema_roguelike.characters.stats.tertiarystats.NormalVision;
import com.anathema_roguelike.items.consumables.HealingPotion;
import com.anathema_roguelike.items.weapons.Battleaxe;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.InputHandler;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.display.DungeonMap.Layer;

import squidpony.squidgrid.gui.gdx.SColor;

public class Player extends Character {
	
	private String name;
	private int experience = 0;
	
	private InputHandler inputHandler = new InputHandler(new PlayerKeyHandler(this));
	
	public static int getRequiredExp(int level) {
		return (level * level);
	}
	
	public Player() {
		super(new VisualRepresentation('@'));
		
		setFaction(Faction.PLAYER);
		
		setClass(new Rogue());
		
		setTertiaryStat(null, NormalVision.class, 20);
		
		Battleaxe weapon = new Battleaxe();
		
		getInventory().equip(weapon);
		getInventory().pickUp(new HealingPotion());
		
		Buff concealment = new Buff(this, new FixedDuration(Duration.PERMANENT));
		concealment.addModifier(new Modifier(this, Concealment.class, .5));
		applyEffect(concealment);
		
		setName("Carl");
		
	}
	
	@Override
	public String toString() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getExp() {
		return experience;
	}
	
	@Override
	public void killedBy(Character attacker) {
		System.out.println("YOU WERE KILLED BY " + attacker);
	}
	
	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onTurn() {
		Game.getInstance().getUserInterface().newLine();
		
		computeVisibility();
		
		Game.getInstance().getDisplay().unlock();
		
		Game.getInstance().getInput().proccessInput(inputHandler, () -> { return !getActionRemaining(); }, null );
		
		Game.getInstance().getDisplay().lock();
	}

	public void grantExperience(int exp) {
		experience += exp;
		
		if(experience >= getRequiredExp(getLevel() + 1) && (getLevel() < 20)) {
			levelUp();
			experience = 0;
		}
	}
	
	@Override
	protected void renderThis() {
		Game.getInstance().getMap().renderEntity(Layer.PLAYER, this);
		
		double greatestVisibility = 0;
		
		for(NPC character : getDungeonLevel().getEntities(NPC.class)) {
			if(canSee(character)) {
				greatestVisibility = Math.max(greatestVisibility, character.visibilityOf(this));
			}
		}
		
		SColor color = Color.factory.blend(Color.NO_LIGHT_PLAYER, Color.WHITE, greatestVisibility + .2);
		
		Game.getInstance().getMap().renderChar(Layer.PLAYER, getX(), getY(), getRepresentation().getChar(), color);
	}

	@Override
	public double getLightEmission() {
		return 0;
	}
	
	@Override
	public void setFacing(double facing) {
		super.setFacing(facing);
	}
}
