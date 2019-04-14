package com.anathema_roguelike
package main.ui.charactercreation.attributeselectors

import com.anathema_roguelike.entities.characters.player.Player

class PointBuy extends AttributeSelector {
  def selectScores(player: Player): Unit = {
    new PointBuyScreen("Set your Ability Scores", player).run
  }
}
