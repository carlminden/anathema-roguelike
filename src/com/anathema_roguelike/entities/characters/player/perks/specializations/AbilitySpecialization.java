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
package com.anathema_roguelike.entities.characters.player.perks.specializations;

import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability;
import com.anathema_roguelike.main.utilities.Utils;

public class AbilitySpecialization extends Perk {
	
	private Class<? extends Ability> ability;
	private int amount = 1;
	
	public AbilitySpecialization(Class<? extends Ability> ability, int amount) {
		this(ability);
		this.amount = amount;
	}
	
	public AbilitySpecialization(Class<? extends Ability> ability) {
		super(Utils.getName(ability) + " Specialization");
		this.ability = ability;
	}
	
	@Override
	public void grant(Character character) {
		character.specialize(ability, amount);
		super.grant(character);
	}
	
	@Override
	public void remove(Character character) {
		character.specialize(ability, amount * -1);
		super.remove(character);
	}
}
