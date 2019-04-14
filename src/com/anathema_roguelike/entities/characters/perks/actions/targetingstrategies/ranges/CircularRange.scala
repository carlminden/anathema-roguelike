package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.ranges

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.{Circle, Shape}
import com.anathema_roguelike.entities.characters.Character

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

abstract class CircularRange[T <: Targetable : TypeTag](constraints: TargetConstraint[T, Character]*) extends Range[T] (constraints:_*) {

  protected def getShape (character: Character): Shape = new Circle(character, () => getRadius(character))
  protected def getRadius (character: Character): Double
}
