package com.anathema_roguelike
package main.ui.charactercreation.attributeselectors

import com.anathema_roguelike.entities.characters.player.Player

abstract class AttributeSelector {
  def selectScores(player: Player): Unit
}
