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
package com.anathema_roguelike.characters.perks.specializations;

import java.util.HashMap;

import com.anathema_roguelike.characters.perks.abilities.Ability;

public class AbilitySpecializationSet {
	
	private HashMap<Class<? extends Ability>, Integer> specializations = new HashMap<>();
	
	public int getSpecializationLevel(Class<? extends Ability> ability) {
		return Math.max(0, specializations.get(ability));
	}
	
	public void specialize(Class<? extends Ability> ability, int amount) {
		specializations.put(ability, specializations.get(ability) + amount);
	}
}
