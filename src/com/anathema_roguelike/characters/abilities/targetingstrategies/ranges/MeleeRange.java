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
package com.anathema_roguelike.characters.abilities.targetingstrategies.ranges;

import java.util.Collection;
import java.util.HashSet;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.abilities.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.characters.abilities.targetingstrategies.shapes.Square;
import com.anathema_roguelike.characters.abilities.targetingstrategies.targetmodes.PointsMode;
import com.anathema_roguelike.environment.Direction;
import com.anathema_roguelike.environment.Point;

public class MeleeRange extends Range {

	public MeleeRange() {
		super(new PointsMode());
	}

	@Override
	public Collection<Point> getPointsInRange(Character character) {
		HashSet<Point> ret = new HashSet<>();
		
		for(int direction : Direction.DIRECTIONS_8) {
			Point position = Direction.offset(character.getPosition(), direction);
			
			ret.add(position);
		}
		
		return ret;
	}

	@Override
	protected Shape getShape(Character character) {
		//TODO should probably have a melee range stat that is used but that will require other infrastructure
		return Square.aroundPoint(character.getPosition(), 1);
	}

}
