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

import com.anathema_roguelike.environment.generation.rooms.Room

object HasPosition {
  implicit def toPoint(hasPosition: HasPosition): Point = hasPosition.getPosition

  implicit def ordering[T <: HasPosition]: Ordering[T] = Ordering.by(p => (p.getX, p.getY))
}

trait HasPosition extends Ordered[HasPosition] {
  def getPosition: Point

  def getX: Int = getPosition.x

  def getY: Int = getPosition.y

  override def compare(other: HasPosition): Int = {
    val dx = this.getX - other.getX
    val dy = this.getY - other.getY
    if(dx != 0) dx
    else dy
  }

  def isAdjacentTo(other: HasPosition): Boolean = {
    val dx = getX - other.getX
    val dy = getY - other.getY
    !(this == other) && Math.abs(dx) <= 1 && Math.abs(dy) <= 1
  }

  /**
    * Returns the linear distance between this coordinate point and the provided one.
    *
    * @param other
    * @return
    */
  //Stole the following methods from the Squidlib Point3D class
  def distance(other: HasPosition): Double = Math.sqrt(squareDistance(other))

  /**
    * Returns the square of the linear distance between this coordinate
    * point and the provided one.
    *
    * @param other
    * @return
    */
  def squareDistance(other: HasPosition): Int = {
    val dx = Math.abs(getX - other.getX)
    val dy = Math.abs(getY - other.getY)
    dx * dx + dy * dy
  }

  /**
    * Returns the Manhattan distance between this point and the provided one.
    * The Manhattan distance is the distance between each point on each separate
    * axis all added together.
    *
    * @param other
    * @return
    */
  def manhattanDistance(other: HasPosition): Int = {
    var distance = Math.abs(getX - other.getX)
    distance += Math.abs(getY - other.getY)
    distance
  }

  /**
    * Returns the largest difference between the two pointSet along any one axis.
    *
    * @param other
    * @return
    */
  def maxAxisDistance(other: HasPosition): Int = Math.max(Math.abs(getX - other.getX), Math.abs(getY - other.getY))
}