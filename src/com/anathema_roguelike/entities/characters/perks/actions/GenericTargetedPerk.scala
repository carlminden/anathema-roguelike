

package com.anathema_roguelike
package entities.characters.perks.actions

import com.anathema_roguelike.actors.Action
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetingStrategy
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.Range
import com.anathema_roguelike.entities.characters.perks.requirements.{PerkRequirement, SelectedTargetRequirement, ValidTargetLocationInRangeRequirement}

abstract class GenericTargetedPerk[TargetType <: Targetable, OriginType <: Targetable](
      name: String,
      source: Any,
      range: Range[OriginType],
      strategy: TargetingStrategy[_ <: TargetType, OriginType],
      requirements: PerkRequirement*
  ) extends ActionPerk[TargetedAction[TargetType]](name, source, requirements:_*) {

  private var origin: Option[OriginType] = Option.empty

  addRequirement(new ValidTargetLocationInRangeRequirement[TargetType, OriginType](this, range))
  addRequirement(new SelectedTargetRequirement[TargetType, OriginType](this, range) {
    override protected def targeted(target: OriginType): Unit = {
      origin = target
    }
  })

  override def activate: Option[Action[Character]] = {
    if (requirementsMet()) {
      val t = createAction
      t.setTargets(strategy.getTargets(origin))

      t
    }
    else {
      printUnmetConditionMessages()
      Option.empty
    }
  }
}
