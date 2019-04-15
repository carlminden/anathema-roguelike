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