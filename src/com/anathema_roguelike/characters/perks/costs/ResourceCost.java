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
package com.anathema_roguelike.characters.perks.costs;

import java.util.Optional;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.Perk;
import com.anathema_roguelike.stats.characterstats.resources.Damage;
import com.anathema_roguelike.stats.characterstats.resources.Resource;
import com.anathema_roguelike.stats.effects.Calculation;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;

public abstract class ResourceCost<T extends Resource> extends PerkCost implements HasEffect<Effect<Character, T>> {
	
	private Class<T> resource;
	private Calculation calculation;
	
	public ResourceCost(Perk perk, Class<T> resource, Calculation calculation) {
		super(perk);
		
		this.resource = resource;
		this.calculation = calculation;
	}

	@Override
	public void pay() {
		getPerk().getCharacter().applyEffect(Optional.of(new Damage<T>(getPerk().getCharacter(), this, resource, calculation)));
	}
	
}
