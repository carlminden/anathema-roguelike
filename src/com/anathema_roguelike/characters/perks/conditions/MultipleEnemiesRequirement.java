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

import com.anathema_roguelike.characters.perks.Perk;
import com.anathema_roguelike.characters.perks.targetingstrategies.ranges.Range;
import com.anathema_roguelike.main.utilities.BooleanCondition;
import com.anathema_roguelike.main.utilities.Utils;

public class MultipleEnemiesRequirement extends PerkRequirement {
	
	private Range range;
	
	public MultipleEnemiesRequirement(Perk perk, Range range) {
		super(perk);
		
		this.range = range;
	}

	@Override
	public BooleanCondition getCondition() {
		return new BooleanCondition() {

			@Override
			public boolean isTrue() {
				if(range.getVisibleEnemies(getPerk().getCharacter()).size() > 1) {
					return true;
				} else {
					return false;
				}
			}
		};
	}
	
	@Override
	public String getRequirementUnmetMessage() {
		return Utils.getName(getPerk()) + " requires multiple enemies in " + Utils.getName(range);
	}

}