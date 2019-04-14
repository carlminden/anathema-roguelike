

package com.anathema_roguelike
package entities.characters.perks.actions

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.{TargetFilter, Targetable}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.Range
import com.anathema_roguelike.entities.characters.perks.requirements.PerkRequirement

abstract class TargetedPerk[T <: Targetable](
  name: String,
  range: Range[T],
  strategy: TargetFilter[T, T],
  requirements: PerkRequirement*)

  extends GenericTargetedPerk[T, T](name, range, strategy, requirements:_*) {

}
