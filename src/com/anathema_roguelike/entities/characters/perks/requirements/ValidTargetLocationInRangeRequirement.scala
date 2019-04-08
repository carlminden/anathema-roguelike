

package com.anathema_roguelike
package entities.characters.perks.requirements

import java.util.function.Supplier

import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.Range
import com.anathema_roguelike.main.utilities.Utils

class ValidTargetLocationInRangeRequirement[TargetType <: Targetable, OriginType <: Targetable](
      val perk: GenericTargetedPerk[TargetType, OriginType],
      var range: Range[OriginType]
    ) extends PerkRequirement(perk) {

  override def getCondition = {
    () => range.getTargets(getPerk.getCharacter.get).nonEmpty
  }
  override def getRequirementUnmetMessage: String = "There are no valid targets in " + Utils.getName(range)
}
