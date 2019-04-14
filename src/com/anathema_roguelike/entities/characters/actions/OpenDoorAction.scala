package com.anathema_roguelike
package entities.characters.actions

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost
import com.anathema_roguelike.environment.features.Doorway

class OpenDoorAction(character: Character, door: Doorway) extends CharacterAction(character, EnergyCost.STANDARD(character)) {
  override protected def onTake(): Unit = {
    door.open
  }
}
