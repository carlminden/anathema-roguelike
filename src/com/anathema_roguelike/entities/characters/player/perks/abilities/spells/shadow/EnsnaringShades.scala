package com.anathema_roguelike.entities.characters.player.perks.abilities.spells.shadow

import com.anathema_roguelike.entities.characters.perks.actions.LingeringTargetedActionPerk
import com.anathema_roguelike.entities.characters.player.classes.Shadow
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.entities.characters.Character

class EnsnaringShades() extends Spell[LingeringTargetedActionPerk[Character, Location]](1, classOf[Shadow]) {
  override protected def createPerk: LingeringTargetedActionPerk[Character, Location] = ??? // TODO Auto-generated method stub
}