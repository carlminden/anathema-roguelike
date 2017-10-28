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

import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.VisualRepresentation;

public class PerceivedStimulus {
	
	private double created;
	private Optional<Location> location;
	private Stimulus stimulus;
	private double magnitude;
	
	public PerceivedStimulus(Optional<Location> location, Stimulus stimulus, double magnitude) {
		this.stimulus = stimulus;
		this.location = location;
		this.magnitude = magnitude;
		this.created = Game.getInstance().getElapsedTime();
	}
	
	public double getRemainingMagnitude() {
		return  magnitude - ((Game.getInstance().getElapsedTime() - created) * 10);
	}
	
	protected double getMagnitude() {
		return magnitude;
	}
	
	public Optional<Location> getLocation() {
		return location;
	}
	
	public Stimulus getStimulus() {
		return stimulus;
	}
	
	public VisualRepresentation getVisualRepresentation() {
		VisualRepresentation vr = getStimulus().getVisualRepresentation();
		
		vr.setColor(Color.opacity(vr.getColor(), (float) (getRemainingMagnitude() / getMagnitude())));
		
		return vr;
	}
	
	@Override
	public String toString() {
		return getRemainingMagnitude() + " remaining of " + stimulus.toString() + (location.isPresent() ? " at: " + location.get() : "");
	}
	
	public static void main(String[] args) {
		System.out.println(new PerceivedStimulus(Optional.empty(), new Resonance(3), 4).toString());
	}
}
