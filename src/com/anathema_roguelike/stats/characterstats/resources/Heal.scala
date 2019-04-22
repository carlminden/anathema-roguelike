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

import com.anathema_roguelike.stats.effects.{AdditiveCalculation, Calculation, Effect, HasEffect}
import com.anathema_roguelike.entities.characters.Character
import scala.reflect.runtime.universe._

class Heal[T <: Resource : TypeTag](initiator: Option[Character], source: Option[HasEffect[Effect[Character, T]]], calculation: Calculation)
  extends ResourceModification[T](initiator, source, calculation) {

  def this(initiator: Option[Character], source: Option[HasEffect[Effect[Character, T]]], amount: Int) {
    this(initiator, source, AdditiveCalculation.fixed(amount))
  }
}