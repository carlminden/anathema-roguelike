/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
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

import java.util.Arrays;
import java.util.LinkedList;

public class MultiAction extends CharacterAction {
	
	private LinkedList<CharacterAction> actions;

	public MultiAction(Character character, CharacterAction ...actions) {
		super(character, new EnergyCost(character, 0));
		
		this.actions = new LinkedList<>(Arrays.asList(actions));
	}

	@Override
	protected void onTake() {
		actions.pop().take();
		
		actions.forEach(a -> getActor().addPendingAction(a));
		
		getActor().addPendingActions(actions);
	}

}
