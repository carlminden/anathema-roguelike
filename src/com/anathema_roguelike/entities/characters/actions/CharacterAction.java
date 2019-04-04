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
package com.anathema_roguelike.entities.characters.actions;

import com.anathema_roguelike.actors.Action;
import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCost;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCosts;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.entities.characters.perks.actions.ActionCostModificationPerk;

public abstract class CharacterAction extends Action<Character> {

	public CharacterAction(Character character, EnergyCost energyCost, ActionCost... costs) {
		super(character, energyCost, costs);
	}

	public CharacterAction(Character character, EnergyCost energyCost, ActionCosts costs) {
		super(character, energyCost, costs);
	}

	@Override
	public void take() {
		getBeforeCosts().map(c -> modifyCost(c)).forEach(c -> c.pay());
		
		onTake();
		
		getAfterCosts().map(c -> modifyCost(c)).forEach(c -> c.pay());
	}
	
	@SuppressWarnings("unchecked")
	private ActionCost modifyCost(ActionCost c) {
		if(c.getActor() != getActor()) {
			throw new RuntimeException("Cost Actor not equal to Action Actor");
		}
		
		for(ActionCostModificationPerk<?> acm : getActor().getPerks(ActionCostModificationPerk.class)) {
			if(acm.getActionType().isAssignableFrom(getClass())) {
				c = acm.getClass().cast(acm).modify(this, c);
			}
		}
		
		return c;
	}
}