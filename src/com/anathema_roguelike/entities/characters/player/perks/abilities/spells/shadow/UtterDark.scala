package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.shadow

import com.anathema_roguelike.entities.characters.perks.actions.TargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Shadow
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.environment.Location

class UtterDark() extends Spell[TargetedPerk[Location]](3, classOf[Shadow]) {
  override protected def createPerk: TargetedPerk[Location] = ??? // TODO Auto-generated method stub
}