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
package entities.characters.foes.ai

import com.anathema_roguelike.environment.HasLocation
import com.anathema_roguelike.main.utilities.pathfinding.Path
import com.anathema_roguelike.main.utilities.pathfinding.PathFinder
import com.anathema_roguelike.main.utilities.position.{Direction, Point}
import com.anathema_roguelike.entities.characters.Character

class AIPathFinder(var character: Character) extends PathFinder {
  private lazy val level = character.getEnvironment

  def getPath(src: HasLocation, dst: HasLocation): Option[Path] = getPath(src.getPosition, dst.getPosition)

  override def getPath(src: Point, dst: Point): Option[Path] = {
    super.getPath(src, dst)
  }

  override protected def isPassable(p: Point, direction: Direction): Boolean = level.isPassable(p)

  override protected def getExtraCost(p: Point, direction: Direction, previousDirection: Direction): Int = {
    val location = level.getLocation(p)

    if(location.getEntities[Character].exists(c => Faction.friendly(character, c))) {
      50
    } else 0
  }
}