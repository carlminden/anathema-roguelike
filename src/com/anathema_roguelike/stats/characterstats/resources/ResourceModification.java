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
package com.anathema_roguelike.stats.characterstats.resources;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.effects.Calculation;
import com.anathema_roguelike.stats.effects.Duration;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.FixedCalculation;
import com.anathema_roguelike.stats.effects.HasEffect;

public abstract class ResourceModification<T extends Resource> extends Effect<Character, T> {
	
	private Calculation calculation;
	private Class<? extends Resource> resource;
	private Object source;
	
	public ResourceModification(HasEffect<? extends Effect<Character, ?>> source, Class<? extends Resource> resource, int amount) {
		super(source, Duration.instant());
		
		this.resource = resource;
		this.calculation = new FixedCalculation(amount);
	}
	
	public ResourceModification(HasEffect<? extends Effect<Character, ?>> source, Class<? extends Resource> resource, Calculation calculation) {
		super(source, Duration.instant());
		this.resource = resource;
		this.calculation = calculation;
	}
	
	@Override
	public void onApplicationCallback(Character character) {
		super.onApplicationCallback(character);
		
		character.modifyResource(source, resource, calculation.get().intValue());
	}
}
