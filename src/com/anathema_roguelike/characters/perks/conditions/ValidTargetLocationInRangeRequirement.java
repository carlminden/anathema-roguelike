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
package com.anathema_roguelike.characters.perks.conditions;

import com.anathema_roguelike.characters.perks.TargetedPerk;
import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;
import com.anathema_roguelike.characters.perks.targetingstrategies.ranges.Range;
import com.anathema_roguelike.main.utilities.BooleanCondition;
import com.anathema_roguelike.main.utilities.Utils;

public class ValidTargetLocationInRangeRequirement<TargetType extends Targetable, OriginType extends Targetable> extends PerkRequirement {
	
	Range<OriginType> range;
	
	public ValidTargetLocationInRangeRequirement(TargetedPerk<TargetType, OriginType> perk, Range<OriginType> range) {
		super(perk);
		
		this.range = range;
	}

	@Override
	public BooleanCondition getCondition() {
		return new BooleanCondition() {
			
			@Override
			public boolean isTrue() {
				return range.getTargets(getPerk().getCharacter()).size() > 0;
			}
		};
	}
	
	@Override
	public String getRequirementUnmetMessage() {
		return "There are no valid targets in " + Utils.getName(range);
	}

}
