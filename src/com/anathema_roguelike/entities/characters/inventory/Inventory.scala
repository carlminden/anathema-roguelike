package com.anathema_roguelike
package entities.characters.inventory

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.entities.items.armor.Armor
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen
import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap
import com.anathema_roguelike.stats.itemstats.ArmorStat

import scala.collection.mutable
import scala.reflect.runtime.universe._

class Inventory(var character: Character) {

  character.getEventBus.register(this)

  private val slots = new AutoClassToInstanceMap[Slot[_ <: Item]](List[Class[_]](classOf[Character]), character)

  private val backpack: mutable.Set[Item] = mutable.Set[Item]()

  def getSlot[T <: Slot[_ <: Item] : TypeTag]: T = slots.get[T]

  def getItems[T <: Item : TypeTag]: Iterable[T] = {
    (slots.getValues.flatMap(s => s.getEquippedItems) ++ backpack).filterByType[T]
  }

  def getEquippedItemsByType[T <: Item : TypeTag]: Iterable[T] = {
    slots.getValues.flatMap(s => s.getEquippedItems).filterByType[T]
  }

  def getEquippedItems: Iterable[Item] = getEquippedItemsByType[Item]

  def getUnequippedItems[T <: Item : TypeTag]: Iterable[T] = backpack.filterByType[T]

  def getUnequippedItems: Iterable[Item] = backpack

  def equip[T <: Item](slot: Class[_ <: Slot[T]], item: T): Unit = {
    getSlot(slot).equip(item)
    item.equippedTo(character)
  }

  def equip[T <: Item : TypeTag](item: T): Unit = {
    val validSlots: Iterable[Slot[T]] = getValidSlots(item)
    val slot: Option[Slot[T]] = new SelectionScreen("Equip to which Slot?", validSlots.toArray, cancellable = true).run

    slot.foreach(s => {
      s.equip(item)
      item.equippedTo(character)
    })
  }

  def getValidSlots[T <: Item : TypeTag](item: T): Iterable[Slot[T]] = {
    slots.getValues.collect {
      //validItem ensures this actually should work
      case s: Slot[T] if s.validItem(item) => s
    }
  }

  def remove[T <: Item : TypeTag](item: T): Unit = {
    for (s <- getValidSlots(item)) {
      s.remove(item)
    }
  }

  def pickUp(item: Item): Unit = {
    backpack += item
  }

  def getDefense[T <: ArmorStat : TypeTag]: Double = {
    getEquippedItemsByType[Armor].map(a => a.getStatAmount[T]).sum
  }
}
