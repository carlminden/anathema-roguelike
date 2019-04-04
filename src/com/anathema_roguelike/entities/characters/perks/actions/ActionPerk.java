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
package com.anathema_roguelike.entities.characters.perks.actions;

import java.util.Optional;

import com.anathema_roguelike.actors.Action;
import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.perks.Perk;

public abstract class ActionPerk<T extends Action<Character>> extends Perk {
	
	public ActionPerk(String name) {
		super(name);
	}
	
	protected abstract T createAction();
	
	public Optional<Action<Character>> activate() {
		if(requirementsMet()) {
			return Optional.of(createAction());
		} else {
			printUnmetConditionMessages();
			return Optional.empty();
		}
	}
}