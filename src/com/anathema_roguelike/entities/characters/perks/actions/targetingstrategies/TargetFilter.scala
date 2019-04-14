package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape
import com.anathema_roguelike.environment.{Environment, Location}

import scala.reflect.runtime.universe._

abstract class TargetFilter[TargetType <: Targetable : TypeTag, ArgType : TypeTag](constraints: TargetConstraint[TargetType, ArgType]*) {

  private var constraintList: List[TargetConstraint[TargetType, ArgType]] = constraints.toList

  def getTargets (arg: Option[ArgType]): Iterable[TargetType]


  def addConstraint(constraint: TargetConstraint[TargetType, ArgType]): Unit = {
    constraintList = constraintList :+ constraint
  }

  def getConstraints: List[TargetConstraint[TargetType, ArgType]] = constraintList

  protected def getTargetsInShape (shape: Shape, environment: Environment, arg: ArgType): Iterable[TargetType] = {
    shape.getLocations(environment).flatMap(l => getTargetsAt(l, arg))
  }

  private def getTargetsAt(location: Location, arg: ArgType): Iterable[TargetType] = {

    (location.getEntities.toList :+ location).filterByType[TargetType]
      .filter(t => getConstraints.forall(c => c.apply(t, arg)))
  }

  protected def getTargetType: Class[TargetType] = typeTagToClass
  protected def getOriginType: Class[ArgType] = typeTagToClass
}
