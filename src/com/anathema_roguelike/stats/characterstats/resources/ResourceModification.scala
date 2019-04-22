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
package stats.characterstats.resources

import com.anathema_roguelike.actors.Duration
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.stats.effects.{Calculation, Effect, FixedCalculation, HasEffect}
import scala.reflect.runtime.universe._

class ResourceModification[T <: Resource : TypeTag](
      initiator: Option[Character],
      source: Option[HasEffect[Effect[Character, T]]],
      calculation: Calculation
  ) extends Effect[Character, T](source, List(), Duration.INSTANT) {

  def this(initiator: Option[Character], source: Option[HasEffect[Effect[Character, T]]], amount: Int) {

    this(initiator, source, new FixedCalculation(amount))
  }

  def getResource: Class[T] = typeTagToClass
  def getInitiator: Option[Character] = initiator

  override def onApplicationCallback(character: Character): Unit = {
    super.onApplicationCallback(character)
    character.modifyResource[T](getInitiator, getSource, calculation().intValue)
  }
}
