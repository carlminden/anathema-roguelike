package com.anathema_roguelike
package entities.characters.player.perks.abilities.techniques

import com.anathema_roguelike.entities.characters.actions.MoveAction
import com.anathema_roguelike.entities.characters.perks.PassthroughPerk
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability

//TODO I need to rebuild modification perks
class WallRunning extends PassthroughPerk[Nothing] with Ability { //TODO Probably actually needs to be a PerkGroup that grants a cost and target modification, may need additional infrastructure
  override protected def createPerk: Nothing = ???  // TODO Auto-generated method stub
}