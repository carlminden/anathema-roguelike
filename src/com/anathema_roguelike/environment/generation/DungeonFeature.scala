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
package environment.generation

import java.util

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.main.utilities.position.Point

import scala.collection.mutable

abstract class DungeonFeature(var position: Point = new Point(0, 0)) {

  private val points = mutable.Set[Point]()

  addPoint(position)

  def getX: Int = position.getX

  def getY: Int = position.getY

  def getPosition: Point = position

  def setPosition(p: Point): Unit = position = new Point(p)

  def intersects(targetPoint: Point): Boolean = points.contains(targetPoint)

  def intersects(targetFeature: DungeonFeature): Boolean = {
    points.exists(p => targetFeature.getPoints.contains(p))
  }

  def getPoints: mutable.Set[Point] = points

  protected def addPoint(p: Point): Unit = points.add(p)

  def placeIfValidates(generator: DungeonGenerator): Boolean = if(!validate(generator)) false
  else {
    place(generator)
    true
  }

  def validate(generator: DungeonGenerator): Boolean

  def place(generator: DungeonGenerator): Unit
}