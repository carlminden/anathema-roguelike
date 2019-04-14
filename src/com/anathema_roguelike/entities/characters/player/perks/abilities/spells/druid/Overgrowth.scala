package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.druid

import com.anathema_roguelike.entities.characters.perks.actions.TargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Druid
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.environment.Location

class Overgrowth() extends Spell[TargetedPerk[Location]](1, classOf[Druid]) {
  override protected def createPerk: TargetedPerk[Location] = ??? // TODO Auto-generated method stub
}