package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.ranges

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.entities.characters.Character


class NearbyTargeted (
    var range: Range[Location],
    var shapeSupplier: Location => Shape,
    val constraints: TargetConstraint[Location, Character]*) extends Range[Location](constraints:_*) {

  private var target: Option[Location] = None

  override protected def getShape(character: Character): Shape = {
    new Shape(target.get) {
      override protected def generatePoints(): Unit = {
        addPoint(shapeSupplier(target.get).getRandomPoint)
      }
    }
  }

  override def getTarget(character: Option[Character]): Option[Location] = {
    target = range.getTarget(character)
    super.getTarget(character)
  }

  override def getTargets(character: Option[Character]): Iterable[Location] = range.getTargets(character)
}