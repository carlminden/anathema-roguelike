package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.shadow

import com.anathema_roguelike.entities.characters.perks.AuraPerk
import com.anathema_roguelike.entities.characters.player.classes.Shadow
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.environment.Location

class UmbralAura() extends Spell[AuraPerk[Location]](3, classOf[Shadow]) {
  override protected def createPerk: AuraPerk[Location] = ??? // TODO Auto-generated method stub
}