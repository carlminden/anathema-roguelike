package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.druid

import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.perks.actions.SelfTargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Druid
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.entities.characters.Character

class NaturesRemedy() extends Spell[SelfTargetedPerk](3, classOf[Druid]) {
  override protected def createPerk: SelfTargetedPerk = new SelfTargetedPerk("Nature's Remedy") {
    override protected def createAction: TargetedAction[Character] = ??? // TODO Auto-generated method stub
  }
}