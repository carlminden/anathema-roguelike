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

import com.anathema_roguelike.environment.{Location, LocationProperty}
import com.anathema_roguelike.environment.features.Feature.Priority.Priority
import com.anathema_roguelike.main.display.VisualRepresentation

object Feature {

  object Priority extends Enumeration {
    type Priority = Value
    val LOW, DEFAULT, HIGH, DEBUG = Value
  }
}

abstract class Feature(
    representation: VisualRepresentation,
    fogOfWarRepresentation: VisualRepresentation,
    foreground: Boolean,
    passable: Boolean,
    opacity: Double,
    damping: Double,
    renderPriority: Priority = Feature.Priority.DEFAULT)
  extends LocationProperty(representation, fogOfWarRepresentation, foreground, passable, opacity, damping) {

  def getRenderPriority: Priority = renderPriority

  override def setLocation(loc: Location): Unit = {
    super.setLocation(loc)
    getLocation.addFeature(this)
  }
}