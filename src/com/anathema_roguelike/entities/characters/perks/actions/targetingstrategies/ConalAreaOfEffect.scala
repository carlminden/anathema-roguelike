

package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.{Cone, Shape}
import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.stats.effects.AdditiveCalculation

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

abstract class ConalAreaOfEffect[TargetType <: Targetable : TypeTag : ClassTag, ArgType <: Targetable : TypeTag](
    var radius: AdditiveCalculation,
    constraints: TargetConstraint[TargetType, ArgType]*
  ) extends AreaOfEffect[TargetType, ArgType](constraints:_*) {

  override protected def getShape (target: ArgType): Shape = {
    new Cone(getOrigin, radius(), Direction.of(getOrigin, target) )
  }

  protected def getOrigin: ArgType
}
