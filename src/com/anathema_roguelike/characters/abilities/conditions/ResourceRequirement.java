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
import com.anathema_roguelike.characters.effects.Calculation;
import com.anathema_roguelike.characters.stats.resources.Resource;
import com.anathema_roguelike.main.utilities.BooleanCondition;
import com.anathema_roguelike.main.utilities.Utils;

public class ResourceRequirement extends AbilityRequirement {
	
	private Class<? extends Resource> resource;
	private Calculation<Integer> calculation;

	public ResourceRequirement(Ability ability, Class<? extends Resource> resource, Calculation<Integer> calculation) {
		super(ability);
		
		this.resource = resource;
		this.calculation = calculation;
	}
	
	@Override
	public BooleanCondition getCondition() {
		return new BooleanCondition() {

			@Override
			public boolean isTrue() {
				return getAbility().getCharacter().getModifiedStatScore(resource) >= calculation.calculate().intValue();
			}
		};
	}
	
	@Override
	public String getRequirementUnmetMessage() {
		return "You do not have enough " + Utils.getName(resource);
	}
}
