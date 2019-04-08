

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
    var newStairDirection = 0

    if (stairs.getDirection == Direction.UP) {
      zOffset = -1
      newStairDirection = Direction.DOWN
    } else if (stairs.getDirection == Direction.DOWN) {
      zOffset = 1
      newStairDirection = Direction.UP
    }

    val newDepth = getActor.getEnvironment.getDepth + zOffset
    if (newDepth < 0 || newDepth >= Config.DUNGEON_DEPTH) {
      throw new RuntimeException("Dungeon is not deep enough, these stairs shouldn't have been made")
    }

    val newEnvironment = Game.getInstance.getState.getEnvironment(newDepth)
    val stairsLocation = newEnvironment.getStairs(newStairDirection).getLocation

    newEnvironment.addEntity(getActor, stairsLocation)
  }
}
