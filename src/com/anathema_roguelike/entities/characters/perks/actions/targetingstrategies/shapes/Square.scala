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

import com.anathema_roguelike.main.utilities.position.HasPosition
import com.anathema_roguelike.main.utilities.position.Point
import com.anathema_roguelike.stats.effects.Calculation

class Square(val origin: HasPosition, var sideLengthCalculation: Calculation) extends Shape(origin) {
  override def generatePoints(): Unit = {
    val x = getX
    val y = getY
    val sideLength = sideLengthCalculation().toInt

    for (i <- 0 until sideLength; j <- 0 until sideLength) {
      addPoint(new Point(x + i, y + j))
    }
  }
}