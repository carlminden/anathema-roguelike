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
package com.anathema_roguelike.characters.perks.targetingstrategies.ranges;

import java.util.Collection;
import java.util.function.BiFunction;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.targetingstrategies.TargetFilter;
import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.GetTargetInterface;
import com.anathema_roguelike.main.utilities.Utils;

public abstract class Range<T extends Targetable> extends TargetFilter<T, Character> {

	@SafeVarargs
	public Range(Class<T> targetType, BiFunction<T, Character, Boolean> ...constraints) {
		super(targetType, constraints);
	}
	
	protected abstract Shape getShape(Character character);
	
	public Collection<T> getTargets(Character character) {
		return getTargetsInShape(getShape(character), character.getEnvironment(), character);
	}
	
	public T getTarget(Character character) {
		Collection<T> validTargets = getTargetsInShape(getShape(character), character.getEnvironment(), character);
		
		if(validTargets.size() == 1) {
			return validTargets.iterator().next();
		} else {
			String instructions = "Select a " + Utils.getName(getTargetType()) + " within " + Utils.getName(this);
			
			return new GetTargetInterface<>(validTargets, instructions).run();
		}
	}
}
