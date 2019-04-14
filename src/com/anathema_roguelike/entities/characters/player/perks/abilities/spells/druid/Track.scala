package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.druid

import com.anathema_roguelike.entities.characters.perks.actions.OffensiveTargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Druid
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell

class Track() extends Spell[OffensiveTargetedPerk](1, classOf[Druid]) {
  override protected def createPerk: OffensiveTargetedPerk = ??? // TODO Auto-generated method stub
}