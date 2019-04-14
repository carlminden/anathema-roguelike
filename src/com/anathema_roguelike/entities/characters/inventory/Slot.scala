

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

