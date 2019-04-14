package com.anathema_roguelike
package entities.characters.player.perks.abilities.techniques

import com.anathema_roguelike.entities.characters.perks.{PassivePerk, PassthroughPerk}
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability

class UnfetteredSpirit() extends PassthroughPerk[PassivePerk] with Ability {
  override protected def createPerk: PassivePerk = ??? // TODO Auto-generated method stub
}