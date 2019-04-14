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

import scala.reflect.runtime.universe._
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.items.armor.{Boots, Chestpiece, Helm, Pants}
import com.anathema_roguelike.entities.items.weapons.Weapon

object SingleSlot {
  type Chest = SingleSlot[Chestpiece]
  type Feet = SingleSlot[Boots]
  type Head = SingleSlot[Helm]
  type Legs = SingleSlot[Pants]
  type SecondaryWeapon = SingleSlot[Weapon]
}

abstract class SingleSlot[T <: Item : TypeTag](character: Character) extends Slot[T](character) {
  private val defaultItem: Option[T] = getDefaultItem
  private var item: Option[T] = None

  equip(defaultItem)

  override def getEquippedItems: Iterable[T] = {
    List(getEquippedItem).flatten
  }

  def getEquippedItem: Option[T] = item

  override def equip(newItem: Option[T]): Unit = {
    newItem.foreach(i => {
      remove(getEquippedItem)
      i.equippedTo(getCharacter)
    })

    item = newItem
  }

  override def remove(remove: Option[T]): Unit = {
    remove.foreach(i => {

      if (i == getEquippedItem && !(getEquippedItem == defaultItem)) {
        i.removedFrom(getCharacter)
        getCharacter.getInventory.pickUp(i)
        item = defaultItem
      }
    })
  }

  protected def getDefaultItem: Option[T] = None
}
