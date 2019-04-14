/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

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
