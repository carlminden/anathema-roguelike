package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.ranges

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint

import scala.reflect.runtime.universe._
import com.anathema_roguelike.entities.characters.Character

class ShortRange[T <: Targetable : TypeTag](val constraints: TargetConstraint[T, Character]*) extends CircularRange[T](constraints:_*) {
  protected def getRadius(character: Character) = 3.0
}