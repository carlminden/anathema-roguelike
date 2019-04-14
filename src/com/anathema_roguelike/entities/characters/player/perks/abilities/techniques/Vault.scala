package com.anathema_roguelike
package entities.characters.player.perks.abilities.techniques

import com.anathema_roguelike.entities.characters.perks.PassthroughPerk
import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability

class Vault() extends PassthroughPerk[GenericTargetedPerk[_, _]] with Ability {
  override protected def createPerk: GenericTargetedPerk[_, _] = ??? // TODO Auto-generated method stub
}