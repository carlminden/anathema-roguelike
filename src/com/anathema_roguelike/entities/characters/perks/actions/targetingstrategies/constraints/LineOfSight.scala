package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.constraints

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.Character

class LineOfSight[T <: Targetable] extends TargetConstraint[T, Character] {
  override def apply(target: T, character: Character): Boolean = character.hasLineOfSightTo(target)
}