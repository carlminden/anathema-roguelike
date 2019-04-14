package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells.inquisitor

import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.perks.actions.SelfTargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.entities.characters.Character

class Invisibility() extends Spell[SelfTargetedPerk](4, classOf[Inquisitor]) {
  override protected def createPerk: SelfTargetedPerk = new SelfTargetedPerk("Invisibility") {
    override protected def createAction: TargetedAction[Character] = ??? // TODO Auto-generated method stub
  }
}