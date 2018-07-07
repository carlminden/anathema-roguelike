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
package com.anathema_roguelike.entities;

import com.anathema_roguelike.actors.Actor;
import com.anathema_roguelike.actors.Duration;
import com.anathema_roguelike.actors.Energy;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Renderable;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.google.common.eventbus.EventBus;

public abstract class Entity implements Renderable, Targetable, Actor {
	
	private Location location;
	private Energy energy = new Energy();
	
	private EventBus eventBus = new EventBus();
	
	public Entity() {
		Game.getInstance().getEventBus().register(this);
		eventBus.register(this);
		Game.getInstance().getState().registerActor(this);
	}
	
	protected abstract void renderThis();
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	public final Location getLocation() {
		return location;
	}
	
	//	public VisualRepresentation getVisualRepresentation() {
	//	return new VisualRepresentation('X', Color.ERROR);
	//}

	public abstract VisualRepresentation getVisualRepresentation();
	
	@Override
	public final void render() {
		if(isVisibleTo(Game.getInstance().getState().getPlayer())) {
			renderThis();
		}
	}

	public double getLightEmission() {
		return 0;
	}
	
	public EventBus getEventBus() {
		return eventBus;
	}
	
	public void postEvent(Object obj) {
		eventBus.post(obj);
	}
	
	@Override
	public Duration getDuration() {
		return Duration.permanent();
	}
	
	@Override
	public Energy getEnergy() {
		return energy;
	}
}
