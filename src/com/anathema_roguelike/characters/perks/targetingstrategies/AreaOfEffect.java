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
package com.anathema_roguelike.characters.perks.targetingstrategies;

import java.util.Collection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.targetingstrategies.ranges.Range;
import com.anathema_roguelike.characters.perks.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.characters.perks.targetingstrategies.targetmodes.TargetMode;
import com.anathema_roguelike.environment.Point;
import com.google.common.base.Predicate;

public abstract class AreaOfEffect extends TargetingStrategy {
	
	public AreaOfEffect(Range range, TargetMode targetMode, Predicate<Character> targetValidator) {
		super(range, targetMode, targetValidator);
	}
	
	public AreaOfEffect(Range range, TargetMode targetMode) {
		super(range, targetMode);
	}
	
	protected abstract Shape getShape(Point origin);
	
	@Override
	public Collection<Character> getTargets(Character character, Point targetedPoint) {
		return getTargetMode().getTargets(getShape(targetedPoint), character, getTargetValidator());
	}
	
	@Override
	public Collection<Point> getValidTargetPoints(Character character) {
		return getRange().getPointsInRange(character);
	}
}
