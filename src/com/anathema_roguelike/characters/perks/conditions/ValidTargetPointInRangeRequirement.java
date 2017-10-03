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
package com.anathema_roguelike.characters.perks.conditions;

import com.anathema_roguelike.characters.perks.TargetedPerk;
import com.anathema_roguelike.characters.perks.targetingstrategies.TargetingStrategy;
import com.anathema_roguelike.main.utilities.BooleanCondition;
import com.anathema_roguelike.main.utilities.Utils;

public class ValidTargetPointInRangeRequirement extends PerkRequirement {
	
	TargetingStrategy strategy;
	
	public ValidTargetPointInRangeRequirement(TargetedPerk perk, TargetingStrategy strategy) {
		super(perk);
		
		this.strategy = strategy;
	}

	@Override
	public BooleanCondition getCondition() {
		return new BooleanCondition() {
			
			@Override
			public boolean isTrue() {
				return strategy.getValidTargetPoints(getPerk().getCharacter()).size() > 0;
			}
		};
	}
	
	@Override
	public String getRequirementUnmetMessage() {
		return "There are no valid targets in " + Utils.getName(strategy.getRange());
	}

}