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
package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies;

import java.util.Collection;

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape;

public abstract class AreaOfEffect<TargetType extends Targetable, OriginType extends Targetable> extends TargetingStrategy<TargetType, OriginType> {
	
	
	@SafeVarargs
	public AreaOfEffect(Class<TargetType> targetType, TargetConstraint<TargetType, OriginType> ...constraints) {
		super(targetType, constraints);
	}
	
	protected abstract Shape getShape(OriginType origin);
	
	@Override
	public Collection<TargetType> getTargets(OriginType origin) {
		return getTargetsInShape(getShape(origin), origin.getEnvironment(), origin);
	}
}
