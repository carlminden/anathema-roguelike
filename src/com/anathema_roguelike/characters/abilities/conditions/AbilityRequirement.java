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
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.utilities.BooleanCondition;

public abstract class AbilityRequirement {
	private Ability ability;
	
	public AbilityRequirement(Ability ability) {
		this.ability = ability;
	}
	
	public abstract BooleanCondition getCondition();
	public abstract String getRequirementUnmetMessage();
	
	public Ability getAbility() {
		return ability;
	}

	public void printUnmetConditionMessage() {
		Game.getInstance().getUserInterface().addMessage(new Message(getRequirementUnmetMessage(), Color.FAILURE));
	}
}
