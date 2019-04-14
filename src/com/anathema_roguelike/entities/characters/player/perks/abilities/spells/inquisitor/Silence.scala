package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells.inquisitor

import com.anathema_roguelike.entities.characters.perks.actions.OffensiveTargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell

class Silence() extends Spell[OffensiveTargetedPerk](1, classOf[Inquisitor]) {
  override protected def createPerk: OffensiveTargetedPerk = ??? // TODO Auto-generated method stub
}