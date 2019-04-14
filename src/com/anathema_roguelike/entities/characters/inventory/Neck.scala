

package com.anathema_roguelike
package entities.characters.inventory

import com.anathema_roguelike.entities.items.Amulet
import com.anathema_roguelike.entities.characters.Character

import scala.collection.mutable

class Neck(character: Character) extends Slot[Amulet](character) {
  private val amulets: mutable.Set[Amulet] = mutable.Set[Amulet]()

  override def getEquippedItems: Iterable[Amulet] = amulets

  def equip(item: Option[Amulet]): Unit = {
    item.foreach(i => {
      amulets.add(i)
      i.equippedTo(getCharacter)
    })
  }

  override def remove(item: Option[Amulet]): Unit = {
    item.foreach(i => {
      if (amulets.contains(i)) {
        amulets.remove(i)
        i.removedFrom(getCharacter)
        getCharacter.getInventory.pickUp(i)
      }
    })
  }
}
