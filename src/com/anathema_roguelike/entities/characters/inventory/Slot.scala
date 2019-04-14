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
package entities.characters.inventory

import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.entities.characters.Character
import scala.reflect.runtime.universe._

abstract class Slot[T <: Item : TypeTag](var character: Character) {
  def validItem(item: Item): Boolean = typeTagToClass[T].isAssignableFrom(item.getClass)

  def getCharacter: Character = character

  def getEquippedItems: Iterable[T]

  def equip(item: Option[T]): Unit

  def remove(item: Option[T]): Unit
}

