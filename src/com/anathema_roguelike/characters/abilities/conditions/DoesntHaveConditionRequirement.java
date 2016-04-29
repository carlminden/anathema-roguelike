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
package com.anathema_roguelike.characters.abilities.conditions;

import com.anathema_roguelike.characters.abilities.Ability;
import com.anathema_roguelike.characters.effects.conditions.Condition;
import com.anathema_roguelike.main.utilities.BooleanCondition;
import com.anathema_roguelike.main.utilities.Utils;

public class DoesntHaveConditionRequirement extends AbilityRequirement {
	
	Class<? extends Condition> condition; 
	
	public DoesntHaveConditionRequirement(Ability ability, Class<? extends Condition> condition) {
		super(ability);
		
		this.condition = condition;
	}

	@Override
	public BooleanCondition getCondition() {
		return new BooleanCondition() {
			
			@Override
			public boolean isTrue() {
				return !getAbility().getCharacter().hasCondition(condition);
			}
		};
	}

	@Override
	public String getRequirementUnmetMessage() {
		return "You cannot use " + Utils.getName(getAbility()) + " while " + Utils.getName(condition);
	}
}
