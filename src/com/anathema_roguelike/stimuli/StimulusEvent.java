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
import com.anathema_roguelike.environment.HasLocation;
import com.anathema_roguelike.environment.Location;

public class StimulusEvent implements HasLocation {
	
	private Stimulus stimulus;
	private Location location;
	
	public StimulusEvent(Location location, Stimulus stimulus) {
		this.stimulus = stimulus;
		this.location = location;
	}
	
	public Optional<PercievedStimulus> getPercievedStimulus(Character character) {
		return stimulus.computePercievedStimulus(location, character);
	}
	
	public Stimulus getStimulus() {
		return stimulus;
	}
	
	public Location getLocation() {
		return location;
	}
}
