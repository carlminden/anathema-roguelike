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
package main.utilities.pathfinding

import com.anathema_roguelike.main.utilities.datastructures.ExtensibleIndexedSeq
import com.anathema_roguelike.main.utilities.position.Point

import scala.collection.mutable.ListBuffer
import util.control.Breaks._

//Took a bresenham line implementation from http://www.sanfoundry.com/java-program-bresenham-line-algorithm/ because I am lazy
object Path {
  def straightLine(a: Point, b: Point): Path = {
    val path = new Path
    var x0 = a.getX
    val x1 = b.getX
    var y0 = a.getY
    val y1 = b.getY
    val dx = Math.abs(x1 - x0)
    val dy = Math.abs(y1 - y0)
    val sx = if(x0 < x1) 1
    else -1
    val sy = if(y0 < y1) 1
    else -1
    var err = dx - dy
    var e2 = 0

    breakable {
      while (true) {
        path += Point(x0, y0)
        if(x0 == x1 && y0 == y1) {
          break
          e2 = 2 * err
          if(e2 > -dy) {
            err = err - dy
            x0 = x0 + sx
          }
          if(e2 < dx) {
            err = err + dx
            y0 = y0 + sy
          }
        }
      }
    }

    path
  }
}

class Path extends ExtensibleIndexedSeq[Point] {

  private val list: ListBuffer[Point] = ListBuffer[Point]()

  override def iterator: Iterator[Point] = list.iterator

  def+=(p: Point): Unit = list += p
}