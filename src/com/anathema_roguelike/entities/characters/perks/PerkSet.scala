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
package entities.characters.perks

import java.util.function.Predicate

import scala.collection.mutable
import scala.reflect.runtime.universe._

class PerkSet {
  private val perks: mutable.Set[Perk] = mutable.Set[Perk]()

  def get[T <: Perk : TypeTag]: Iterable[T] = perks.filterByType[T]

  def get[T <: Perk : TypeTag](predicate: T => Boolean): Iterable[T] = perks.filterByType[T].filter(predicate)

  def add(perk: Perk): Unit = {
    perks.add(perk)
  }

  def remove(perk: Perk): Unit = {
    perks.remove(perk)
  }
}
