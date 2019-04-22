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
package environment

import com.anathema_roguelike.environment.features.Feature
import com.anathema_roguelike.environment.terrain.Terrain
import com.anathema_roguelike.main.Config
import com.anathema_roguelike.main.utilities.position.Direction.Direction2
import com.anathema_roguelike.main.utilities.position.Point

import scala.annotation.varargs

class EnvironmentFactory(defaultTerrain: () => Terrain) {
  private val map = Array.ofDim[MapTile](Config.DUNGEON_WIDTH, Config.DUNGEON_HEIGHT)

  private var upStairs: Option[Point] = None
  private var downStairs: Option[Point] = None

  for (i <- map.indices; j <- map(i).indices) {
    map(i)(j) = new MapTile(defaultTerrain())
  }

  def setTerrain(point: Point, terrain: Terrain): Unit = {
    map(point.getX)(point.getY).terrain = terrain
  }

  def getTerrainAt(point: Point): Terrain = {
    map(point.getX)(point.getY).terrain
  }

  @varargs
  def addFeatures(point: Point, features: Feature*): Unit = {
    map(point.getX)(point.getY).addFeatures(features:_*)
  }

  def setDownStairs(point: Point): Unit = {
    downStairs = point
  }

  def setUpStairs(point: Point): Unit = {
    upStairs = point
  }

  def build(depth: Int): Environment = new Environment(map, upStairs.get, downStairs.get, depth)
}
