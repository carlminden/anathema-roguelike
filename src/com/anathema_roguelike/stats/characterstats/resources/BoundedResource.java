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
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;

public abstract class BoundedResource extends Resource {
	
	private boolean initiallyFull;
	private boolean initialized = false;
	
	public BoundedResource(Character character, boolean initiallyFull) {
		super(character);
		
		this.initiallyFull = initiallyFull;
	}
	
	@Override
	public double getAmount() {
		if(!initialized) {
			initialized = true;
			
			if(initiallyFull) {
				set(Optional.empty(), Optional.empty(), getMaximum());
			}
		}
		
		return super.getAmount();
	}
	
	@Override
	public void set(Optional<Character> initiator, Optional<? extends HasEffect<? extends Effect<Character, ?>>> source, int amount) {
		super.set(initiator, source, Utils.clamp(amount, 0, getMaximum()));
	}
	
	public void reset() {
		set(Optional.empty(), Optional.empty(), getMaximum());
	}
	
	public abstract int getMaximum();
}
