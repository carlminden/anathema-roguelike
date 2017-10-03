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
import com.anathema_roguelike.main.utilities.BooleanCondition;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.characterstats.resources.Resource;
import com.anathema_roguelike.stats.effects.Calculation;

public class ResourceRequirement extends PerkRequirement {
	
	private Class<? extends Resource> resource;
	private Calculation calculation;

	public ResourceRequirement(Perk perk, Class<? extends Resource> resource, Calculation calculation) {
		super(perk);
		
		this.resource = resource;
		this.calculation = calculation;
	}
	
	@Override
	public BooleanCondition getCondition() {
		return new BooleanCondition() {

			@Override
			public boolean isTrue() {
				return getPerk().getCharacter().getStatAmount(resource) >= calculation.get().intValue();
			}
		};
	}
	
	@Override
	public String getRequirementUnmetMessage() {
		return "You do not have enough " + Utils.getName(resource);
	}
}
