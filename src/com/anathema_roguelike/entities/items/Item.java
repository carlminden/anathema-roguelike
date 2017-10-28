/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.anathema_roguelike.entities.items;

import java.util.Optional;

import com.anathema_roguelike.actors.Action;
import com.anathema_roguelike.entities.Entity;
import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.HasStats;
import com.anathema_roguelike.stats.StatSet;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;
import com.anathema_roguelike.stats.itemstats.ItemStat;
import com.anathema_roguelike.stats.itemstats.ItemStatSet;

public abstract class Item extends Entity implements HasStats<Item, ItemStat>, HasEffect<Effect<Character, CharacterStat>> {
	
	private ItemStatSet stats;
	private Optional<Character> wearer;
	
	public Item(Optional<VisualRepresentation> representation) {
		super(representation);
		
		this.stats = new ItemStatSet(this);
	}
	
	@Override
	protected void renderThis() {
		Game.getInstance().getMap().renderEntity(DungeonLayer.NORMAL, this);
	}
	
	public void equippedTo(Character character) {
		this.wearer = Optional.of(character);
		character.applyEffect(getEffect());
	}
	
	public void removedFrom(Character character) {
		this.wearer = null;
		character.removeEffectBySource(this);
	}
	
	public Optional<Character> getWearer() {
		return wearer;
	}
	
	@Override
	public StatSet<Item, ItemStat> getStatSet() {
		return stats;
	}
	
	@Override
	public Action<?> getNextAction() {
		//Most Items shouldnt do anything, but it could be interesting in some cases, maybe certain magical items will make noise/resonance
		return new Action<Item>(this, EnergyCost.STANDARD(this)) {
			@Override
			protected void onTake() {
				
			}
		};
	}
}
