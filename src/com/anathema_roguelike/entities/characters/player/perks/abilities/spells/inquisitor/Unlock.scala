package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells.inquisitor

import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk
import com.anathema_roguelike.entities.characters.player.classes.Inquisitor
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.entities.characters.Character

//TODO: check to see if it should target doors directly
class Unlock() extends Spell[GenericTargetedPerk[Location, Character]](3, classOf[Inquisitor]) {
  override protected def createPerk: GenericTargetedPerk[Location, Character] = ??? // TODO Auto-generated method stub
}