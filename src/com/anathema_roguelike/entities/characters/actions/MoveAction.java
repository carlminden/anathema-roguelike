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
package com.anathema_roguelike.entities.characters.actions;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.costs.ActionCost;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.entities.characters.actions.costs.StimulusCost;
import com.anathema_roguelike.entities.characters.events.MoveEvent;
import com.anathema_roguelike.entities.characters.stimuli.Sight;
import com.anathema_roguelike.environment.HasLocation;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.Visibility;

public class MoveAction extends CharacterAction implements HasLocation {
	
	private Location location;

	@SafeVarargs
	public MoveAction(Character character, EnergyCost energyCost, HasLocation location, ActionCost ...costs) {
		super(character, energyCost, costs);
		
		this.location = location.getLocation();
		
		addCost(new StimulusCost<>(character, Sight.class, character.getStatAmount(Visibility.class)));
		addCost(new StimulusCost<>(character, Sight.class, character.getStatAmount(Visibility.class), location));
	}
	
	@Override
	protected void onTake() {
		getActor().getEnvironment().getEventBus().post(new MoveEvent(getActor(), location));
		
		getEnvironment().moveEntityTo(getActor(), location);
	}

	@Override
	public Location getLocation() {
		return location;
	}
}
