package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.shadow

import com.anathema_roguelike.entities.characters.perks.actions.OffensiveTargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Shadow
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell

class HallucinatoryApparitions() extends Spell[OffensiveTargetedPerk](2, classOf[Shadow]) {
  override protected def createPerk: OffensiveTargetedPerk = ??? // TODO Auto-generated method stub
}