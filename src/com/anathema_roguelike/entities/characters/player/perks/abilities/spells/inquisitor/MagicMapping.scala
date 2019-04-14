package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells.inquisitor

import com.anathema_roguelike.actors.Action
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.perks.actions.ActionPerk

class MagicMapping() extends Spell[ActionPerk[Action[Character]]](3, classOf[Inquisitor]) {
  override protected def createPerk: ActionPerk[Action[Character]] = ??? // TODO Auto-generated method stub
}