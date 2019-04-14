package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.druid

import com.anathema_roguelike.actors.Action
import com.anathema_roguelike.entities.characters.player.classes.Druid
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.perks.actions.ActionPerk

class ObscuringFog() extends Spell[ActionPerk[Action[Character]]](4, classOf[Druid]) {
  override protected def createPerk: ActionPerk[Action[Character]] = ??? // TODO Auto-generated method stub
}