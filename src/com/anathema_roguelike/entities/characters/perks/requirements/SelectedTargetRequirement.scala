package com.anathema_roguelike
package entities.characters.perks.requirements

import java.util.function.Supplier

import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.Range
import com.anathema_roguelike.main.utilities.Utils

abstract class SelectedTargetRequirement[TargetType <: Targetable, OriginType <: Targetable](
    perk: GenericTargetedPerk[TargetType, OriginType],
    var range: Range[OriginType]) extends PerkRequirement(perk) {

  protected def targeted(target: OriginType): Unit

  override def getCondition = () => range.getTarget(getPerk.getCharacter).foreach(t => targeted(t)).isDefined

  override def getRequirementUnmetMessage: String = Utils.getName(getPerk) + " activation canceled, no Target was selected"
}