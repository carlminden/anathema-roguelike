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
package com.anathema_roguelike.stats.characterstats.resources;

import java.util.Optional;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.stats.characterstats.secondarystats.Health;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;

public class CurrentHealth extends BoundedResource {

	public CurrentHealth(Character character) {
		super(character, true);
	}
	
	@Override
	public void modify(Optional<Character> initiator, Optional<HasEffect<? extends Effect<Character, ?>>> source, int amount) {
		if(amount < 0) {
			
			Character character = getObject();
			
			int remainder = Math.min(0, amount);
			
			super.modify(initiator, source, remainder);
			
			if(getAmount() <= 0 && character.isAlive()) {
				character.onDeath();
			}
		} else if( amount > 0) {
			super.modify(initiator, source, amount);
		}
	}
	
	@Override
	public int getMaximum() {
		return (int) getObject().getStatAmount(Health.class);
	}
	
	@Override
	public double getAmount() {
		return super.getAmount();
	}
}
