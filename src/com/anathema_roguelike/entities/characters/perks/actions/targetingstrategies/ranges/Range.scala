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

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.{TargetFilter, Targetable}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.GetTargetInterface
import com.anathema_roguelike.main.utilities.Utils

import scala.collection.JavaConverters._
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

abstract class Range[T <: Targetable : TypeTag](constraints: TargetConstraint[T, Character]*)
  extends TargetFilter[T, Character](constraints:_*) {

  protected def getShape(character: Character): Shape

  override def getTargets(character: Option[Character]): Iterable[T] = {
    //This seems like it shouldnt be valid if there is no origin
    val c = character.get

    getTargetsInShape(getShape(c), c.getEnvironment, c)
  }

  def getTarget(character: Option[Character]): Option[T] = {
    character.flatMap(c => {
      val validTargets: Iterable[T] = getTargetsInShape(getShape(c), c.getEnvironment, c)

      if (validTargets.size == 1) {
        Some(validTargets.iterator.next)
      } else {
        val instructions: String = "Select a " + Utils.getName(getTargetType) + " within " + Utils.getName(this)

        new GetTargetInterface[T](validTargets, instructions).run
      }
    })
  }
}
