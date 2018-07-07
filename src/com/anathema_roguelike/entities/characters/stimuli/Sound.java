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
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Circle;
import com.anathema_roguelike.entities.characters.player.Player;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.display.animations.Ripple;
import com.anathema_roguelike.main.ui.UIConfig;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.senses.Hearing;

public class Sound extends Stimulus {
	
	public Sound(double magnitude) {
		super(magnitude);
	}
	
	public Sound(double magnitude, Character owner) {
		super(magnitude, owner);
	}

	@Override
	public Optional<PerceivedStimulus> computePerceivedStimulus(Location location, Character character) {
		
		if(getOwner().orElseGet(() -> null) instanceof Player && character instanceof Player) {
			new Ripple(location, getMagnitude() / 5, 0.2f).create(DisplayLayer.DUNGEON_OVERLAY, UIConfig.DUNGEON_OFFSET);
		}
		
		//TODO determine how to handle sound being stopped/reduced by walls etc
		if(location.distance(character) > getMagnitude() / 5) {
			return Optional.empty();
		}
		
		double hearing = character.getStatAmount(Hearing.class);
		
		double perceivedSound = getMagnitude() * (4*hearing)/(hearing + 10);
		
		System.out.println(perceivedSound);
		
		if(perceivedSound >= 100) {
			return Optional.of(new PerceivedStimulus(Optional.of(location), this, perceivedSound));
		} else if(perceivedSound >= 50) {
			Location percievedLocation = location.getEnvironment().getLocation(new Circle(location, () -> 2.0).getRandomPassablePoint(location.getEnvironment()));
			
			return Optional.of(new PerceivedStimulus(Optional.of(percievedLocation), this, perceivedSound));
		} else if(perceivedSound >= 25) {
			Location percievedLocation = location.getEnvironment().getLocation(new Circle(location, () -> 3.0).getRandomPassablePoint(location.getEnvironment()));
			
			return Optional.of(new PerceivedStimulus(Optional.of(percievedLocation), this, perceivedSound));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public VisualRepresentation getVisualRepresentation() {
		return new VisualRepresentation('!', Color.WHITE);
	}
}
