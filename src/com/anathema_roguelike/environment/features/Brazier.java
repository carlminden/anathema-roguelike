/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.environment.features;

import java.util.Optional;

import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.effects.AdditiveCalculation;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.Modifier;
import com.anathema_roguelike.stats.locationstats.Brightness;
import com.anathema_roguelike.stats.locationstats.LocationStat;

public class Brazier extends Feature {

	public Brazier(Environment level, Point point) {
		super(level, point, new VisualRepresentation('\u0436'), true, false, 1.0, 1.0);
	}
	
	@Override
	public Optional<Effect<Location, LocationStat>> getEffect() {
		Effect<Location, LocationStat> effect = new Effect<Location, LocationStat>(this,
			new Modifier<LocationStat>(Brightness.class, AdditiveCalculation.build(() -> 20.0))) {};
		
		return Optional.of(effect);
	}
}
