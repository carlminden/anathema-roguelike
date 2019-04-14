package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.shadow

import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.player.classes.Shadow
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.environment.Location

class ConsumeLight() extends Spell[GenericTargetedPerk[Targetable, Location]](2, classOf[Shadow]) {
  override protected def createPerk: GenericTargetedPerk[Targetable, Location] = ??? // TODO Auto-generated method stub
}