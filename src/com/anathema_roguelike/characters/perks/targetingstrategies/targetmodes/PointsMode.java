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
package com.anathema_roguelike.characters.perks.targetingstrategies.targetmodes;

import java.util.Collection;
import java.util.HashSet;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Point;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class PointsMode extends TargetMode {

	@Override
	public Collection<Character> getTargets(Shape shape, Character character, Predicate<Character> targetValidator) {
		
		HashSet<Character> ret = new HashSet<>();
		Environment level = character.getEnvironment();
		
		for(Point point : shape.getPoints()) {
			ret.addAll(Collections2.filter(level.getEntitiesAt(point, Character.class), targetValidator));
		}
		
		return ret;
	}
	
}
