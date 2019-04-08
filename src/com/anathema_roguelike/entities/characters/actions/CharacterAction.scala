

package com.anathema_roguelike
package entities.characters.actions

import com.anathema_roguelike.actors.Action
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.costs.{ActionCost, ActionCosts, EnergyCost}

abstract class CharacterAction(actor: Character, energyCost: EnergyCost, additionalCosts: ActionCost*)
  extends Action[Character](actor, energyCost, additionalCosts: _*) {

  override def take(): Unit = {
    getBeforeCosts.map(c => modifyCost(c)).foreach(c => c.pay())
    onTake()
    getAfterCosts.map(c => modifyCost(c)).foreach(c => c.pay())
  }

  private def modifyCost(c: ActionCost) = {
    if (c.getActor != getActor) {
      throw new RuntimeException("Cost Actor not equal to Action Actor")
    }

    c
  }
}
