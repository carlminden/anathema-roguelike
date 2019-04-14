package com.anathema_roguelike
package entities.characters.player.perks.abilities.techniques

import com.anathema_roguelike.entities.characters.perks.PassthroughPerk
import com.anathema_roguelike.entities.characters.perks.actions.SelfTargetedPerk
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability

class Vanish() extends PassthroughPerk[SelfTargetedPerk] with Ability {
  override protected def createPerk: SelfTargetedPerk = ??? // TODO Auto-generated method stub
}