

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
