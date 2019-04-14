package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells.inquisitor

import com.anathema_roguelike.entities.characters.perks.actions.OffensiveTargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.entities.characters.Character

class Deafen() extends Spell[OffensiveTargetedPerk](2, classOf[Inquisitor]) {
  override protected def createPerk: OffensiveTargetedPerk = ??? // TODO Auto-generated method stub
}