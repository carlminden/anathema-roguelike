

package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.ranges

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint
import com.anathema_roguelike.entities.characters.Character

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

class VeryShortRange[T <: Targetable : TypeTag : ClassTag](constraints: TargetConstraint[T, Character]*) extends CircularRange[T] (constraints:_*) {

  override protected def getRadius (character: Character): Double = 1.0
}
