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
package com.anathema_roguelike.characters.stats.tertiarystats.resources;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.Player;
import com.anathema_roguelike.characters.stats.secondarystats.Health;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.utilities.Listed;
import com.anathema_roguelike.main.utilities.Utils;

@Listed
public class CurrentHealth extends Resource {

	public CurrentHealth(Character character) {
		super(character, true);
	}
	
	@Override
	public void modify(Object source, int amount) {
		if(amount < 0) {
			int temphp = getCharacter().getModifiedStatScore(TemporaryHealth.class);
			
			getCharacter().modifyTertiaryStat(source, TemporaryHealth.class, amount);
			
			int remainder = Math.min(0, amount + temphp);
			
			super.modify(source, remainder);
		} else if( amount > 0) {
			super.modify(source, amount);
		}
	}
	
	@Override
	public Integer getMaximum() {
		return getCharacter().getModifiedStatScore(Health.class);
	}
	
	@Override
	public int getAmount() {
		return super.getAmount() + getCharacter().getModifiedStatScore(TemporaryHealth.class);
	}
	
	@Override
	protected void printResourceChangedMessage(Object source, Character target, int amount) {
		if(source != null && (target instanceof Player) && amount > 0) {
			Game.getInstance().getUserInterface().addMessage(new Message(Utils.getName(source) + " Has healed you for " + amount + " points of health", Color.GREEN));
		}
	}
}
