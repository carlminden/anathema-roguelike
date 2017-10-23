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
package com.anathema_roguelike.entities.characters.stimuli;

import java.util.Optional;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.main.display.VisualRepresentation;

public abstract class Stimulus {
	
	private double magnitude;
	Optional<Character> owner = Optional.empty();
	
	public Stimulus(double magnitude) {
		this.magnitude = magnitude;
	}
	
	public Stimulus(double magnitude, Character owner) {
		this.magnitude = magnitude;
		this.owner = Optional.of(owner);
	}
	
	public abstract VisualRepresentation getVisualRepresentation();
	
	public double getMagnitude() {
		return magnitude;
	}
	
	public Optional<Character> getOwner() {
		return owner;
	}
	
	public abstract Optional<PerceivedStimulus> computePerceivedStimulus(Location location, Character character);
}
