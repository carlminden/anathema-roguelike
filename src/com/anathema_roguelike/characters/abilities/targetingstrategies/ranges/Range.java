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

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.abilities.targetingstrategies.shapes.Shape;
import com.anathema_roguelike.characters.abilities.targetingstrategies.targetmodes.TargetMode;
import com.anathema_roguelike.environment.Point;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

public abstract class Range {
	
	private TargetMode targetMode;
	
	public Range(TargetMode targetMode) {
		this.targetMode = targetMode;
	}
	
	protected abstract Shape getShape(Character character);
	
	protected Collection<Character> getTargetsInRange(Character character, Predicate<Character> targetValidator) {
		
		return targetMode.getTargets(getShape(character), character, targetValidator);
	}
	
	public Collection<Point> getPointsInRange(Character character) {
		return getShape(character).getPoints();
	}
	
	public boolean isInRange(Character character, Point point) {
		return getShape(character).validPoint(point);
	}
	
	public boolean isInRange(Character character, Character target) {
		return isInRange(character, target.getPosition());
	}

	public Collection<Character> getEnemies(final Character character) {
		return getTargets(character, new Predicate<Character>() {

			@Override
			public boolean apply(Character target) {
				return target.getFaction() != character.getFaction();
			}
		});
	}
	
	public Collection<Character> getVisibleEnemies(final Character character) {
		return getVisibleTargets(character, new Predicate<Character>() {

			@Override
			public boolean apply(Character target) {
				return target.getFaction() != character.getFaction();
			}
		});
	}
	
	public Collection<Character> getTargets(final Character character, Predicate<Character> predicate) {
		
		Predicate<Character> visible = new Predicate<Character>() {

			@Override
			public boolean apply(Character target) {
				return character.canSee(target);
			}
		};
		
		return getTargetsInRange(character, Predicates.and(visible, predicate));
	}
	
	public Collection<Character> getVisibleTargets(final Character character, Predicate<Character> predicate) {
		return getTargets(character, Predicates.and(predicate, new Predicate<Character>() {

			@Override
			public boolean apply(Character target) {
				return character.canSee(target);
			}
			
		}));
	}
	
	public Collection<Character> getVisibleTargets(final Character character) {
		return getVisibleTargets(character, Predicates.<Character>alwaysTrue());
	}
	
	public boolean isCharacterInRange(Character character, Character target) {
		return Iterables.contains(getTargets(character, Predicates.<Character>alwaysTrue()), target);
	}
}
