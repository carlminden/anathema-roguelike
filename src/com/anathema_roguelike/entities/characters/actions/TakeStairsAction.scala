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
package entities.characters.actions

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost
import com.anathema_roguelike.environment.terrain.grounds.Stairs
import com.anathema_roguelike.main.{Config, Game}
import com.anathema_roguelike.main.utilities.position.Direction

class TakeStairsAction(character: Character, stairs: Stairs) extends CharacterAction(character, EnergyCost.STANDARD(character)) {
  override protected def onTake(): Unit = {
    var zOffset = 0

    val newStairDirection = stairs.getDirection match {
      case Direction.UP => {
        zOffset = -1

        Direction.DOWN
      }
      case Direction.DOWN => {
        zOffset = 1

        Direction.UP
      }
    }

    val newDepth = getActor.getEnvironment.getDepth + zOffset
    if (newDepth < 0 || newDepth >= Config.DUNGEON_DEPTH) {
      throw new RuntimeException("Dungeon is not deep enough, these stairs shouldn't have been made")
    }

    val newEnvironment = Game.getInstance.getState.getEnvironment(newDepth)
    val stairsLocation = newEnvironment.getStairs(newStairDirection).get.getLocation

    newEnvironment.addEntity(getActor, stairsLocation)
  }
}
