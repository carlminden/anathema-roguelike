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
package com.anathema_roguelike.characters.abilities;

import java.util.Collection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.abilities.conditions.ValidTargetPointInRangeRequirement;
import com.anathema_roguelike.characters.abilities.targetingstrategies.TargetingStrategy;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.GetTargetInterface;
import com.google.common.base.Predicate;

public abstract class TargetedAbility extends ActivatedAbility {

	private TargetingStrategy strategy;
	
	protected abstract boolean onActivate(Character target);
	
	public abstract Predicate<Character> getTargetValidator();
	
	public TargetedAbility(TargetingStrategy strategy) {
		super();
		this.strategy = strategy;
		
		this.strategy.addTargetValidator(getTargetValidator());
		
		addRequirement(new ValidTargetPointInRangeRequirement(this, strategy));
	}
	
	public boolean activate(Point targetedPoint) {
		
		for(Character target : strategy.getTargets(getCharacter(), targetedPoint)) {
			onActivate(target);
		}
		
		return true;
	}
	
	@Override
	protected boolean onActivate() {
		
		Collection<Point> validTargets = strategy.getValidTargetPoints(getCharacter());
		Point targetedPoint;
		
		if(validTargets.size() == 1) {
			targetedPoint = validTargets.iterator().next();
		} else {
			targetedPoint = new GetTargetInterface(this, strategy).run();
		}
		
		if(targetedPoint != null) {
			return activate(targetedPoint);
		} else {
			return false;
		}
	}
	
	public TargetingStrategy getTargetingStrategy() {
		return strategy;
	}
	
}
