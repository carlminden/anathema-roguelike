package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.SinglePoint
import com.anathema_roguelike.environment.Location
import scala.reflect.runtime.universe._

class LocationTargeted[T <: Targetable : TypeTag](val constraints: TargetConstraint[T, Location]*)
  extends TargetFilter[T, Location](constraints:_*) {

  override def getTargets(target: Option[Location]): Iterable[T] = {
    target.map(t => {
      getTargetsInShape(new SinglePoint(t), t.getEnvironment, t)
    }).getOrElse(List())
  }
}