package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape
import com.anathema_roguelike.environment.{Environment, Location}
import com.anathema_roguelike.main.utilities.Utils

import scala.collection.JavaConverters._
import scala.reflect._
import scala.reflect.runtime.universe._

abstract class TargetFilter[TargetType <: Targetable : TypeTag : ClassTag, ArgType : TypeTag](constraints: TargetConstraint[TargetType, ArgType]*) {


  def getTargets (arg: Option[ArgType]): Iterable[TargetType]

  protected def getTargetsInShape (shape: Shape, environment: Environment, arg: ArgType): Iterable[TargetType] = {
    shape.getLocations(environment).asScala.flatMap(l => getTargetsAt(l, arg))
  }

  private def getTargetsAt(location: Location, arg: ArgType): Iterable[TargetType] = {

    Utils.filterBySubclass[TargetType](location.getEntities.asScala.toList :+ location)
      .filter(t => constraints.forall(c => c.apply(t, arg)))
  }

  protected def getTargetType: Class[TargetType] = typeTagToClass
  protected def getOriginType: Class[ArgType] = typeTagToClass
}
