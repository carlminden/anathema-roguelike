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
package com.anathema_roguelike.characters.events;

import com.anathema_roguelike.characters.stats.resources.Resource;

public class ResourceChangedEvent {

	private Object source;
	private double amount;
	private Class<? extends Resource> resource;
	
	public ResourceChangedEvent(Object source, Class<? extends Resource> resource, double difference) {
		this.source = source;
		this.amount = difference;
		this.resource = resource;
	}

	public Object getSource() {
		return source;
	}

	public double getAmount() {
		return amount;
	}
	
	public Class<? extends Resource> getResource() {
		return resource;
	}
}
