

package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

class SingleTargeted[T <: Targetable : TypeTag : ClassTag](constraints: TargetConstraint[T, T]*) extends TargetingStrategy[T, T](constraints:_*) {

  override def getTargets (target: Option[T]): Iterable[T] = List() ++ target
}