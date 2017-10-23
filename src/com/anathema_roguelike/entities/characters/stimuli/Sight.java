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
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.VisibilityLevel;

public class Sight extends Stimulus {

	public Sight(double magnitude, Character owner) {
		super(magnitude, owner);
	}

	@Override
	public Optional<PerceivedStimulus> computePerceivedStimulus(Location location, Character character) {
		
		if(character.getVisibilityOf(getOwner().get()) == VisibilityLevel.PARTIALLYCONCEALED) {
			Location percievedLocation = character.getEnvironment().getLocation(new Circle(character, () -> 3.0).getRandomPoint());
			
			return Optional.of(new PerceivedStimulus(Optional.of(percievedLocation), this, getMagnitude()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public VisualRepresentation getVisualRepresentation() {
		return new VisualRepresentation('?', Color.WHITE);
	}
}
