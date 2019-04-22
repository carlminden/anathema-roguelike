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
package main.display

import com.anathema_roguelike.main.utilities.position.Point
import squidpony.squidgrid.gui.gdx.SColor

import scala.collection.mutable.ListBuffer

class BufferMaskOutline(position: Point, mask: BufferMask, color: SColor) extends Outline(color, position) {
  override def getPoints: Iterable[Point] = {
    val points = ListBuffer[Point]()

    for (x <- 0 until mask.getWidth; y <- 0 until mask.getHeight) {
      if(mask.get(x, y)) {
        points += Point(x, y)
      }
    }

    points
  }

  override def validPoint(point: Point): Boolean = mask.get(point.getX, point.getY)
}