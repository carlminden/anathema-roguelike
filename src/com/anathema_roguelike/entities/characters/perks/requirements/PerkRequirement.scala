

package com.anathema_roguelike
package entities.characters.perks.requirements

import com.anathema_roguelike.entities.characters.perks.Perk
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.ui.messages.Message

abstract class PerkRequirement(perk: Perk) {

  def getCondition: () => Boolean

  def getRequirementUnmetMessage: String

  def getPerk: Perk = perk

  def printUnmetConditionMessage(): Unit = {
    Game.getInstance.getUserInterface.addMessage(new Message(getRequirementUnmetMessage, Color.FAILURE))
  }
}

