/** *****************************************************************************
  * Copyright (C) 2017 Carl Minden
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
  * *****************************************************************************//*******************************************************************************
  * Copyright (C) 2017 Carl Minden
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
  * *****************************************************************************/

package com.anathema_roguelike
package entities.characters.foes.ai

import com.anathema_roguelike.environment.Environment
import com.anathema_roguelike.environment.HasLocation
import com.anathema_roguelike.main.utilities.pathfinding.Path
import com.anathema_roguelike.main.utilities.pathfinding.PathFinder
import com.anathema_roguelike.main.utilities.position.Point
import com.anathema_roguelike.entities.characters.Character

class AIPathFinder(var character: Character) extends PathFinder {
  private val level = character.getEnvironment

  def getPath(src: HasLocation, dst: HasLocation): Path = getPath(src.getPosition, dst.getPosition)

  override def getPath(src: Point, dst: Point): Path = {
    super.getPath(src, dst)
  }

  override protected def isPassable(p: Point, direction: Int): Boolean = level.isPassable(p)

  override protected def getExtraCost(p: Point, direction: Int, previousDirection: Int): Int = {
    val location = level.getLocation(p)

    if(location.getEntities[Character].exists(c => Faction.friendly(character, c))) 50 else 0
  }
}