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
package com.anathema_roguelike.environment.features;

import java.util.Optional;

import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.locationstats.Brightness;
import com.anathema_roguelike.stats.locationstats.LocationStat;

public class Brazier extends Feature {

	public Brazier() {
		super(new VisualRepresentation('\u0436'), true, false, 1.0, 1.0);
	}
	
	@Override
	public Optional<Effect<Location, LocationStat>> getEffect() {
		Effect<Location, LocationStat> effect = new Effect<>(Optional.of(this),
			new Modifier<Location, LocationStat>(Brightness.class, AdditiveCalculation.fixed(20.0)));
		
		return Optional.of(effect);
	}
}
