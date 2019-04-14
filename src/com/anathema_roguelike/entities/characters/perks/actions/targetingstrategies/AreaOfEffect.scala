package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

abstract class AreaOfEffect[TargetType <: Targetable : TypeTag : ClassTag, ArgType  <: Targetable : TypeTag](
    val constraints: TargetConstraint[TargetType, ArgType]*
  ) extends TargetFilter[TargetType, ArgType] (constraints:_*) {

  protected def getShape (arg: ArgType): Shape

  override def getTargets (arg: Option[ArgType]): Iterable[TargetType] = {

    arg.map(a => {
      getTargetsInShape(getShape(a), a.getEnvironment, a)
    }).getOrElse(List())
  }
}
