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


import java.util.Optional;

import com.anathema_roguelike.characters.abilities.Buff;
import com.anathema_roguelike.characters.ai.Faction;
import com.anathema_roguelike.characters.classes.Rogue;
import com.anathema_roguelike.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.items.StandardItemFactory;
import com.anathema_roguelike.items.weapons.types.MeleeWeaponType;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer;
import com.anathema_roguelike.main.display.InputHandler;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.characterstats.attributes.Perception;
import com.anathema_roguelike.stats.effects.AdditiveCalculation;
import com.anathema_roguelike.stats.effects.Modifier;

import squidpony.squidgrid.gui.gdx.SColor;

public class Player extends Character {
	
	private String name;
	
	private InputHandler inputHandler = new InputHandler(new PlayerKeyHandler(this));
	
	public static int getRequiredExp(int level) {
		return (level * level);
	}
	
	public Player() {
		super(Optional.of(new VisualRepresentation('@')));
		
		setFaction(Faction.PLAYER);
		
		setClass(new Rogue());
		
		getInventory().equip(new StandardItemFactory().generate(MeleeWeaponType.class), PrimaryWeapon.class);
		
		Optional<Buff> testModifiers = Optional.of(new Buff(
				null,
				new Modifier<Perception>(Perception.class, AdditiveCalculation.build(() -> 20.0))));
		
		
		applyEffect(testModifiers);
		
		setName("Carl");
		
	}
	
	@Override
	public String toString() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void onDeath() {
		System.out.println("YOU WERE KILLED");
	}
	
	@Override
	public void onTurn() {
		Game.getInstance().getUserInterface().newLine();
		
		computeVisibility();
		
		Game.getInstance().getDisplay().unlock();
		
		Game.getInstance().getInput().proccessInput(inputHandler, () -> { return !getActionRemaining(); }, null );
		
		Game.getInstance().getDisplay().lock();
	}
	
	@Override
	protected void renderThis() {
		Game.getInstance().getMap().renderEntity(DungeonLayer.PLAYER, this);
		
		double greatestVisibility = 0;
		
		for(NPC character : getEnvironment().getEntities(NPC.class)) {
			if(canSee(character)) {
				greatestVisibility = Math.max(greatestVisibility, character.visibilityOf(this));
			}
		}
		
		SColor color = Color.factory.blend(Color.NO_LIGHT_PLAYER, Color.WHITE, greatestVisibility + .2);
		
		Game.getInstance().getMap().renderChar(DungeonLayer.PLAYER, getX(), getY(), getRepresentation().getChar(), color);
	}
	
	@Override
	public void setFacing(double facing) {
		super.setFacing(facing);
	}
}
