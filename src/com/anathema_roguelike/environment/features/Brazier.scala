/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
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

package com.anathema_roguelike
package environment.features

import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.display.VisualRepresentation
import com.anathema_roguelike.stats.effects.{AdditiveCalculation, Effect, Modifier}
import com.anathema_roguelike.stats.locationstats.Brightness

import com.anathema_roguelike.stats.Stat.LocationStat

class Brazier(location: Location) extends Feature(
  location,
  new VisualRepresentation('\u0436'),
  new VisualRepresentation('\u0436'),
  foreground = true, passable = false, opacity = 1.0, damping = 1.0) {
    override def getEffect: Option[Effect[Location, LocationStat]] = {
      new Effect[Location, LocationStat](this, List(new Modifier[Brightness](AdditiveCalculation.fixed(20.0))))
    }
}