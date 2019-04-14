package com.anathema_roguelike
package entities.characters.player.perks.abilities.techniques

import com.anathema_roguelike.entities.characters.perks.PassthroughPerk
import com.anathema_roguelike.entities.characters.perks.actions.TargetedPerk
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability
import com.anathema_roguelike.environment.Location

class Lockpicking() extends PassthroughPerk[TargetedPerk[Location]] with Ability {
  override protected def createPerk: TargetedPerk[Location] = ??? // TODO Auto-generated method stub
}