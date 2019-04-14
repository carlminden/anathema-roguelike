package com.anathema_roguelike
package entities.characters.player.perks.abilities.techniques

import com.anathema_roguelike.entities.characters.perks.PassthroughPerk
import com.anathema_roguelike.entities.characters.perks.actions.OffensiveTargetedPerk
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability

class Assassinate() extends PassthroughPerk[OffensiveTargetedPerk] with Ability {
  override protected def createPerk: OffensiveTargetedPerk = ??? // TODO Auto-generated method stub
}