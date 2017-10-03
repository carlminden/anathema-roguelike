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
import com.anathema_roguelike.environment.Point;

public abstract class Stimulus {
	
	private int magnitude;
	private Point origin;
	Optional<Character> source = Optional.empty();
	
	public Stimulus(Point origin, int magnitude) {
		this.origin = origin;
		this.magnitude = magnitude;
	}
	
	public Stimulus(Point origin, int magnitude, Character source) {
		this.origin = origin;
		this.magnitude = magnitude;
		this.source = Optional.of(source);
	}
	
	public int getMagnitude() {
		return magnitude;
	}
	
	public Point getOrigin() {
		return origin;
	}
	
	public Optional<Character> getSource() {
		return source;
	}
	
	public abstract Optional<PercievedStimulus> computePercievedStimulus(Character character);
}
