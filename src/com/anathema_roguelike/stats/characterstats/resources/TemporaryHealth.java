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
package com.anathema_roguelike.stats.characterstats.resources;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.Player;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.utilities.Listed;

@Listed
public class TemporaryHealth extends Resource {

	public TemporaryHealth(Character character) {
		super(character);
	}
	
	@Override
	public void modify(Object source, int modification) {
		if(modification > 0) {
			if(getAmount() < modification) {
				set(source, modification);
			}
		} else {
			set(source, Math.max(0, (int) getAmount() + modification));
		}
		
	}
	
	@Override
	protected void printResourceChangedMessage(Object source, Character target, int amount) {
		if(target instanceof Player && amount > 0) {
			Game.getInstance().getUserInterface().addMessage(new Message("You have gained " + amount + " points of temporary health", Color.GREEN));
		}
	}
}
