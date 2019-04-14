package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.shadow

import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.perks.actions.SelfTargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Shadow
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.entities.characters.Character

class Shadowflame() extends Spell[SelfTargetedPerk](1, classOf[Shadow]) {
  override protected def createPerk: SelfTargetedPerk = new SelfTargetedPerk("Shadowflame") {
    override protected def createAction: TargetedAction[Character] = ??? // TODO Auto-generated method stub
  }
}