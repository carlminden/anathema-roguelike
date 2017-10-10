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
package com.anathema_roguelike.main;

import java.util.Optional;

import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.Renderable;
import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class Entity implements Renderable, Targetable {
	
	private Location location;
	
	private Optional<VisualRepresentation> representation;
	
	public Entity(Optional<VisualRepresentation> representation) {
		this.representation = representation; 
	}
	
	public Entity(char representation) {
		this.representation = Optional.of(new VisualRepresentation(representation)); 
	}
	
	protected abstract void renderThis();
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	public final Location getLocation() {
		return location;
	}
	
	public VisualRepresentation getRepresentation() {
		return representation.orElse(new VisualRepresentation('X', Color.ERROR));
	}

	public void setRepresentation(Optional<VisualRepresentation> representation) {
		this.representation = representation;
	}
	
	@Override
	public final void render() {
		if(isVisibleTo(Game.getInstance().getState().getPlayer())) {
			renderThis();
		}
	}

	public double getLightEmission() {
		return 0;
	}
}
