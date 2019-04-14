package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells.inquisitor

import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.perks.actions.SelfTargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.entities.characters.Character

class Haste() extends Spell[SelfTargetedPerk](2, classOf[Inquisitor]) {
  override protected def createPerk: SelfTargetedPerk = new SelfTargetedPerk("Haste") {
    override protected def createAction: TargetedAction[Character] = ??? // TODO Auto-generated method stub
  }
}