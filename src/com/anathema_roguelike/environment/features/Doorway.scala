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
import com.anathema_roguelike.fov.ObstructionChangedEvent
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.VisualRepresentation
import com.anathema_roguelike.main.utilities.position.Orientation.{HORIZONTAL, Orientation, VERTICAL}

object Doorway {
  protected def getRepresentaion(open: Boolean, orientation: Orientation): VisualRepresentation = {

    if(open) {
      new VisualRepresentation('+', Color.BROWN)
    } else {
      orientation match {
        case VERTICAL => new VisualRepresentation('\u2015', Color.BROWN)
        case HORIZONTAL => new VisualRepresentation('|', Color.BROWN)
      }
    }
  }
}

class Doorway(var orientation: Orientation, var isOpen: Boolean = false)
  extends Feature(
    Doorway.getRepresentaion(isOpen, orientation),
    Doorway.getRepresentaion(isOpen, orientation),
    true,
    isOpen,
    if(isOpen) 0.0 else 1.0,
    if(isOpen) 0.0 else 1.0
  ) {

  override def getRepresentation: VisualRepresentation = {
    Doorway.getRepresentaion(isOpen, orientation)
  }

  def open: Boolean = {
    if(!isOpen) {
      isOpen = true
      getLocation.getEnvironment.getEventBus.post(new ObstructionChangedEvent(getPosition))
      true
    } else {
      false
    }
  }

  override def isPassable: Boolean = isOpen

  def close(): Unit = isOpen = false

  def getOrientation: Orientation = orientation

  def setOrientation(orientation: Orientation): Unit = this.orientation = orientation
}