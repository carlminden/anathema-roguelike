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
import com.anathema_roguelike.characters.events.ResourceChangedEvent;
import com.anathema_roguelike.stats.characterstats.CharacterStat;

public abstract class Resource extends CharacterStat {
	
	private int amount = 0;
	
	public Resource(Character character) {
		super(character);
		
	}
	
	public void modify(Object source, int amount) {
		set(source, this.amount + amount);
	}
	@Override
	public double getAmount() {
		return amount;
	}
	
	public void set(Object source, int amount) {
		
		int currentAmount = (int) getAmount();
		
		int difference = amount - currentAmount;
		
		getObject().postEvent(new ResourceChangedEvent(source, getClass(), difference));
		
		if(source != null) {
			printResourceChangedMessage(source, getObject(), (int)difference);
		}
		
		this.amount = amount;
	}

	protected void printResourceChangedMessage(Object source, Character target, int amount) {
		
	}
}
