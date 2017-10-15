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
package com.anathema_roguelike.stimuli;

import java.util.Optional;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.main.utilities.position.HasPosition;

public abstract class Stimulus {
	
	private int magnitude;
	Optional<Character> source = Optional.empty();
	
	public Stimulus(int magnitude) {
		this.magnitude = magnitude;
	}
	
	public Stimulus(int magnitude, Character source) {
		this.magnitude = magnitude;
		this.source = Optional.of(source);
	}
	
	public int getMagnitude() {
		return magnitude;
	}
	
	public Optional<Character> getSource() {
		return source;
	}
	
	public void render(HasPosition point) {
		
	}
	
	public abstract Optional<PercievedStimulus> computePercievedStimulus(Location location, Character character);
}
