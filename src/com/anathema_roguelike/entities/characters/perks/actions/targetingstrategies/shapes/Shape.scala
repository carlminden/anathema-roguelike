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

import com.anathema_roguelike.environment.{Environment, Location}
import com.anathema_roguelike.main.utilities.position.{HasPosition, Point}

import scala.collection.mutable
import scala.util.Random

abstract class Shape(var center: HasPosition) extends HasPosition {
  private val pointSet: mutable.Set[Point] = mutable.Set[Point]()
  private val pointArray: Array[Point] = Array[Point]()

  protected def generatePoints(): Unit

  def validPoint(point: Point): Boolean = getPoints.contains(point)

  final def getPoints: mutable.Set[Point] = {
    if (pointSet.isEmpty) generatePoints()
    pointSet
  }

  final def getPointsArray: Array[Point] = {
    if (pointSet.isEmpty) generatePoints()
    pointArray
  }

  def getRandomPoint: Point = {
    getPointsArray(new Random().nextInt(pointArray.length))
  }

  def getRandomPassablePoint(env: Environment): Point = {
    val passablePoints: Array[Point] = getPointsArray.filter(p => env.getLocation(p).isPassable)

    passablePoints.get(new Random().nextInt(passablePoints.length))
  }

  protected def addPoint(p: Point): Unit = {
    pointSet += p
    pointArray :+ p
  }

  override def getPosition: Point = center.getPosition

  def getLocations(env: Environment): Iterable[Location] = {
    getPoints.filter(p => env.isInBounds(p)).map(p => env.getLocation(p))
  }
}