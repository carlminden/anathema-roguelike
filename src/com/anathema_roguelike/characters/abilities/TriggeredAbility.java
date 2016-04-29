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

import com.google.common.eventbus.Subscribe;

public abstract class TriggeredAbility<T> extends Ability {
	
	Class<T> triggerEventType;
	
	protected abstract AbilityResults onTrigger(T trigger);
	
	public TriggeredAbility(Object source, Class<T> triggerEventType) {
		super(source);
		this.triggerEventType = triggerEventType;
	}
	
	@Subscribe
	public void trigger(Object trigger) {
		if(triggerEventType.isAssignableFrom(trigger.getClass()) && checkRequirementsAndExpendCostsIfTrue()) {
			processResults(onTrigger((T) trigger));
		}
	}
}
