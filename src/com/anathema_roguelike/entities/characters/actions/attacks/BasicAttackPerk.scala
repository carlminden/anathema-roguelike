

package com.anathema_roguelike
package entities.characters.actions.attacks

import com.anathema_roguelike.entities.characters.perks.actions.OffensiveTargetedPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.SingleTargeted
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.PrimaryWeaponRange
import com.anathema_roguelike.entities.characters.Character

class BasicAttackPerk() extends OffensiveTargetedPerk(
  "Basic Attack",
  new PrimaryWeaponRange[Character],
  new SingleTargeted[Character]) {

    override protected def createAction = new BasicAttack(getCharacter)
}