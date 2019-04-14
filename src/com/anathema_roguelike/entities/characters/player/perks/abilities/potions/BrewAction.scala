package com.anathema_roguelike
package entities.characters.player.perks.abilities.potions

import com.anathema_roguelike.entities.characters.actions.CharacterAction
import com.anathema_roguelike.entities.characters.actions.costs.ActionCost
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost
import com.anathema_roguelike.entities.characters.Character

class BrewAction(val character: Character, val costs: ActionCost*)
//TODO this needs to be an interuptible action which isnt a thing yet
  extends CharacterAction(character, new EnergyCost(character, 5), costs:_*) {
  override protected def onTake(): Unit = ???
    // TODO Auto-generated method stub
}