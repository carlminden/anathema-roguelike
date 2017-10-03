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
package com.anathema_roguelike.characters.perks.targetingstrategies;

import java.util.Collection;
import java.util.HashSet;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.targetingstrategies.ranges.Range;
import com.anathema_roguelike.characters.perks.targetingstrategies.targetmodes.PointsMode;
import com.anathema_roguelike.environment.Point;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class SingleTargeted extends TargetingStrategy {

	public SingleTargeted(Range range, Predicate<Character> targetValidator) {
		super(range, new PointsMode(), targetValidator);
	}
	
	public SingleTargeted(Range range) {
		super(range, new PointsMode());
	}

	@Override
	public Collection<Character> getTargets(Character character, Point targetedPoint) {
		HashSet<Character> ret = new HashSet<>();
		Collection<Character> targets = character.getEnvironment().getEntitiesAt(targetedPoint, Character.class);
		
		if(!targets.isEmpty()) {
			ret.add(targets.iterator().next());
		}
		
		return ret;
	}

	@Override
	public Collection<Point> getValidTargetPoints(Character character) {
		return Collections2.transform(getRange().getTargets(character, getTargetValidator()), new Function<Character, Point>() {

			@Override
			public Point apply(Character target) {
				return target.getPosition();
			}
			
		});
	}

}
