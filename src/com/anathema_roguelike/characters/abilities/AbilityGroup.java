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

import java.util.HashSet;

import com.anathema_roguelike.characters.Character;

public abstract class AbilityGroup extends Ability {
	
	public AbilityGroup() {
		super();
	}

	private HashSet<Ability> abilities = new HashSet<>();

	@Override
	public void grant(Character character) {
		for(Ability ability : abilities) {
			ability.grant(character);
		}
		super.grant(character);
	}

	@Override
	public void remove(Character character) {
		for(Ability ability : abilities) {
			ability.remove(character);
		}
		super.remove(character);
	}
	
	public void add(Ability ability) {
		
		if(ability != null) {
			abilities.add(ability);
		}
	}
}
