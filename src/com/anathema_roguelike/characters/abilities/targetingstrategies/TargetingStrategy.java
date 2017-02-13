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
package com.anathema_roguelike.characters.abilities.targetingstrategies;

import java.util.Collection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.abilities.targetingstrategies.ranges.Range;
import com.anathema_roguelike.characters.abilities.targetingstrategies.targetmodes.TargetMode;
import com.anathema_roguelike.environment.Point;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public abstract class TargetingStrategy {
	
	private Range range;
	Predicate<Character> targetValidator;
	private TargetMode targetMode;
	
	public TargetingStrategy(Range range, TargetMode targetMode, Predicate<Character> targetValidator) {
		this.range = range;
		this.targetMode = targetMode;
		this.targetValidator = targetValidator;
	}
	
	public TargetingStrategy(Range range, TargetMode targetMode) {
		this.range = range;
		this.targetMode = targetMode;
		this.targetValidator = Predicates.alwaysTrue();
	}
	
	public abstract Collection<Character> getTargets(Character character, Point targetedPoint);
	public abstract Collection<Point> getValidTargetPoints(Character character);
	
	public Range getRange() {
		return range;
	}
	
	public TargetMode getTargetMode() {
		return targetMode;
	}
	
	public Predicate<Character> getTargetValidator() {
		return targetValidator;
	}
	
	public void addTargetValidator(Predicate<Character> validator) {
		targetValidator = Predicates.and(targetValidator, validator);
	}
	
	public Collection<Character> getValidTargets(Character character) {
		return range.getVisibleTargets(character, targetValidator);
	}
}
