package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells.inquisitor

import com.anathema_roguelike.entities.characters.perks.actions.TargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.entities.characters.Character

class Calm() extends Spell[TargetedPerk[Character]](1, classOf[Inquisitor]) {
  override protected def createPerk: TargetedPerk[Character] = ??? // TODO Auto-generated method stub
}