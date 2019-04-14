package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.shadow

import com.anathema_roguelike.entities.characters.perks.actions.SelfTargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Shadow
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell

class Darkvision() extends Spell[SelfTargetedPerk](2, classOf[Shadow]) {
  override protected def createPerk: SelfTargetedPerk = ??? // TODO Auto-generated method stub
}