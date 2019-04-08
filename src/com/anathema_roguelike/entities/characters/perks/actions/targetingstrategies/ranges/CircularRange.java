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
package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges;

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Circle;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape;

public abstract class CircularRange<T extends Targetable> extends Range<T> {
	
	@SafeVarargs
	public CircularRange(TargetConstraint<T, Character> ...constraints) {
		super(constraints);
	}

	@Override
	protected Shape getShape(Character character) {
		return new Circle(character, () -> (getRadius(character)));
	}
	
	protected abstract double getRadius(Character character);
}
