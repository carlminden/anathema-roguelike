

package com.anathema_roguelike
package entities.characters.perks.actions

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.{Targetable, TargetingStrategy}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.Range
import com.anathema_roguelike.entities.characters.perks.requirements.PerkRequirement

abstract class TargetedPerk[T <: Targetable](
  name: String,
  source: Any,
  range: Range[T],
  strategy: TargetingStrategy[T, T],
  requirements: PerkRequirement*)

  extends GenericTargetedPerk[T, T](name, source, range, strategy, requirements:_*) {

}
