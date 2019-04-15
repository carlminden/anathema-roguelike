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
package main.utilities.position

import com.anathema_roguelike.main.utilities.position.Direction._

object Orientation {

  sealed trait Orientation

  case object HORIZONTAL extends Direction((LEFT | RIGHT).value) with Orientation
  case object VERTICAL extends Direction((UP | DOWN).value) with Orientation

  val ORIENTATIONS: Array[Orientation] = Array(VERTICAL, HORIZONTAL)

  def getOrientation(direction: Direction): Orientation = {
    direction match {
      case _ if(direction == Direction.UP || direction == Direction.DOWN) => VERTICAL
      case _ if(direction == Direction.LEFT || direction == Direction.RIGHT) => HORIZONTAL
      case _ => VERTICAL
    }
  }

  //Didnt want to port my shitty dungeon generator, so I needed this
  def fromJavaDirection(direction: Int): Orientation = {
    direction match {
      case LEFT.value | RIGHT.value => HORIZONTAL
      case UP.value | DOWN.value => VERTICAL
      case _ => HORIZONTAL
    }
  }
}