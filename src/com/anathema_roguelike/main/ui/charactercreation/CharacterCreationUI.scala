package com.anathema_roguelike
package main.ui.charactercreation

import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.ui.charactercreation.attributeselectors.PointBuy

object CharacterCreationUI {
  def createCharacter(location: Location): Player = {

    val player = new Player(location)
    player.levelUp()

    new PointBuy().selectScores(player)

    player
  }
}
