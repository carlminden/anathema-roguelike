package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells.inquisitor

import com.anathema_roguelike.entities.characters.perks.actions.TargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.environment.Location

class IllusoryWall() extends Spell[TargetedPerk[Location]](2, classOf[Inquisitor]) {
  override protected def createPerk: TargetedPerk[Location] = ??? // TODO Auto-generated method stub
}