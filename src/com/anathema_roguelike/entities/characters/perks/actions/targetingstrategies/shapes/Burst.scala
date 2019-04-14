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
package entities.characters.perks.actions.targetingstrategies.shapes

import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.main.utilities.position.HasPosition
import com.anathema_roguelike.main.utilities.position.Point
import com.anathema_roguelike.stats.effects.Calculation

class Burst(val origin: HasPosition, var sizeCalculation: Calculation) extends Shape(origin) {
  override def generatePoints(): Unit = {
    val x = getX
    val y = getY

    sizeCalculation().intValue match {
      case 1 =>
        for (d <- Direction.DIRECTIONS_4) {
          addPoint(Direction.offset(getPosition, d))
        }
      case 2 =>
        for (d <- Direction.DIRECTIONS_8) {
          addPoint(Direction.offset(getPosition, d))
        }
        addPoint(new Point(x + 2, y))
        addPoint(new Point(x - 2, y))
        addPoint(new Point(x, y + 2))
        addPoint(new Point(x, y - 2))
      case 3 =>
        addPoint(new Point(x + 2, y + 2))
        addPoint(new Point(x - 2, y - 2))
        addPoint(new Point(x + 2, y - 2))
        addPoint(new Point(x - 2, y + 2))
        addPoint(new Point(x + 1, y + 2))
        addPoint(new Point(x - 1, y + 2))
        addPoint(new Point(x + 1, y - 2))
        addPoint(new Point(x - 1, y - 2))
        addPoint(new Point(x + 3, y))
        addPoint(new Point(x - 3, y))
        addPoint(new Point(x, y + 3))
        addPoint(new Point(x, y - 3))
      case _ =>
    }
  }
}