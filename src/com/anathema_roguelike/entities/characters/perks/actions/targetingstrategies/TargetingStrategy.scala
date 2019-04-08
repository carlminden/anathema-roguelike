

package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

abstract class TargetingStrategy[TargetType <: Targetable : TypeTag : ClassTag, OriginType <: Targetable : TypeTag](
    constraints: TargetConstraint[TargetType, OriginType]*) extends TargetFilter[TargetType, OriginType](constraints:_*) {
}
