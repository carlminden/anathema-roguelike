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
package entities.characters.actions.costs

import com.anathema_roguelike.stats.characterstats.resources.{Resource, ResourceModification}
import com.anathema_roguelike.stats.effects.{Effect, HasEffect}
import com.anathema_roguelike.entities.characters.Character

import scala.reflect.runtime.universe._

class ResourceCost[T <: Resource : TypeTag](
    character: Character,
    var amount: Int) extends CharacterActionCost(character) with HasEffect[Effect[Character, T]] {

  override def pay(): Unit = {
    getEffect.foreach(e => e.applyTo(getCharacter))
  }

  override def getEffect: Option[Effect[Character, T]] = new ResourceModification[T](Option.empty, Option(this), amount)

  def getResource: Class[T] = typeTagToClass[T]

  def getAmount: Int = amount
}