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

import com.anathema_roguelike.environment.LocationProperty
import com.anathema_roguelike.main.utilities.pathfinding.PathFinder
import com.anathema_roguelike.main.utilities.position.{Direction, Point}

class ReachabilityPathfinder(var map: Array[Array[LocationProperty]]) extends PathFinder {
  override protected def isPassable(p: Point, direction: Direction): Boolean = {
    DungeonGenerator.validPoint(p) && map(p.getX)(p.getY).isPassable
  }

  override protected def getExtraCost(p: Point, direction: Direction, previousDirection: Direction) = 0
}