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

import com.anathema_roguelike.actors.Duration;
import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.stats.effects.Calculation;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.FixedCalculation;
import com.anathema_roguelike.stats.effects.HasEffect;

public class ResourceModification<T extends Resource> extends Effect<Character, T> {
	
	private Calculation calculation;
	private Class<? extends Resource> resource;
	private Optional<Character> initiator;
	
	public ResourceModification(Optional<Character> initiator, Optional<HasEffect<? extends Effect<Character, ?>>> source, Class<? extends Resource> resource, int amount) {
		super(source.orElse(null), Duration.instant());
		
		this.resource = resource;
		this.calculation = new FixedCalculation(amount);
		this.initiator = initiator;
	}
	
	public ResourceModification(Optional<Character> initiator, Optional<HasEffect<? extends Effect<Character, ?>>> source, Class<? extends Resource> resource, Calculation calculation) {
		super(source.orElse(null), Duration.instant());
		this.resource = resource;
		this.calculation = calculation;
		this.initiator = initiator;
	}
	
	public Optional<Character> getInitiator() {
		return initiator;
	}
	
	@Override
	public void onApplicationCallback(Character character) {
		super.onApplicationCallback(character);
		
		character.modifyResource(getInitiator(), Optional.of(getSource()), resource, calculation.get().intValue());
	}
}
