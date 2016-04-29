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
import com.anathema_roguelike.characters.events.ResourceChangedEvent;
import com.anathema_roguelike.characters.stats.tertiarystats.TertiaryStat;
import com.anathema_roguelike.main.utilities.Utils;

public abstract class Resource extends TertiaryStat {
	
	private Integer maximum = null;
	private boolean initiallyFull;
	private boolean initialized = false;
	
	public Resource(Character character, boolean initiallyFull) {
		super(character);
		this.initiallyFull = initiallyFull;
	}
	
	public Resource(Character character, int maximum, boolean initiallyFull) {
		super(character);
		
		this.maximum = maximum;
		this.initiallyFull = initiallyFull;
	}
	
	@Override
	public int getAmount() {
		
		if(!initialized) {
			initialized = true;
			
			if(initiallyFull) {
				set(null, getMaximum());
			}
		}
		
		return super.getAmount();
	}
	
	@Override
	public void set(Object source, int amount) {
		
		int currentAmount = getAmount();
		int newAmount;
		
		if(getMaximum() != null) {
			newAmount = Utils.clamp(amount, 0, getMaximum());
		} else {
			newAmount = Math.max(0, amount);
		}
		
		int difference = newAmount - currentAmount;
		
		getCharacter().postEvent(new ResourceChangedEvent(source, getClass(), difference));
		
		if(source != null) {
			printResourceChangedMessage(source, getCharacter(), difference);
		}
		
		super.set(source, newAmount);
	}
	
	public void reset(Object source) {
		set(source, getMaximum());
	}
	
	public Integer getMaximum() {
		return maximum;
	}

	protected void printResourceChangedMessage(Object source, Character target, int amount) {
		
	}
}
