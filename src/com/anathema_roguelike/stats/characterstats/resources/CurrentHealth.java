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
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.characterstats.secondarystats.Health;

public class CurrentHealth extends BoundedResource {

	public CurrentHealth(Character character) {
		super(character, true);
	}
	
	@Override
	public void modify(Object source, int amount) {
		if(amount < 0) {
			
			Character character = getObject();
			
			double temphp = character.getStatAmount(TemporaryHealth.class);
			
			getObject().modifyResource(source, TemporaryHealth.class, amount);
			
			int remainder = (int) Math.min(0, amount + temphp);
			
			super.modify(source, remainder);
			
			if(getAmount() <= 0 && character.isAlive()) {
				character.onDeath();
			}
		} else if( amount > 0) {
			super.modify(source, amount);
		}
	}
	
	@Override
	public int getMaximum() {
		return (int) getObject().getStatAmount(Health.class);
	}
	
	@Override
	public double getAmount() {
		return super.getAmount() + getObject().getStatAmount(TemporaryHealth.class);
	}
	
	@Override
	protected void printResourceChangedMessage(Object source, Character target, int amount) {
		if(source != null && (target instanceof Player) && amount > 0) {
			Game.getInstance().getUserInterface().addMessage(new Message(Utils.getName(source) + " Has healed you for " + amount + " points of health", Color.GREEN));
		}
	}
}
