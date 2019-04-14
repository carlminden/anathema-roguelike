package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.constraints

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable

trait TargetConstraint[TargetType <: Targetable, ArgType] {
  def apply(target: TargetType, arg: ArgType): Boolean
}
