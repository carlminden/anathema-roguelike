/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
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
 * along with this program.  If not, see <http:></http:>//www.gnu.org/licenses/>.
 */
package com.anathema_roguelike.entities.characters.inventory

import java.util.HashSet

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.entities.items.armor.Armor
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen
import com.anathema_roguelike.main.utilities.AutoKClassToInstanceMap
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.stats.itemstats.ArmorStat

class Inventory(@PublishedApi internal val character: Character) {

    @PublishedApi internal val slots: AutoKClassToInstanceMap<Slot<out Item>>

    private val backpack = HashSet<Item>()

    init {
        character.eventBus.register(this)
        slots = AutoKClassToInstanceMap.create<Slot<out Item>>(arrayOf(Character::class.java), character)

    }

    fun getEquippedItems() : Collection<Item> {
        return getEquippedItems(Item::class.java)
    }

    fun getUnequippedItems() : Collection<Item> {
        return backpack
    }

    inline fun <reified T : Slot<out Item>> getSlot(): T {
        return slots.get()
    }

    fun <T : Item> getItems(type: Class<T>): Collection<T> {
        val items = HashSet(backpack)

        slots.getValues().forEach { s -> items.addAll(s.equippedItems) }

        return Utils.filterBySubclass(items, type)
    }

    fun <T : Item> getEquippedItems(type: Class<T>): Collection<T> {
        val items = HashSet<Item>()

        slots.getValues().forEach { s -> items.addAll(s.equippedItems) }

        return Utils.filterBySubclass(items, type)
    }

    fun <T : Item> getUnequippedItems(type: Class<T>): Collection<T> {
        return Utils.filterBySubclass(backpack, type)
    }

    inline fun <reified T : Item> equip(item: T) {
        val validSlots = getValidSlots(item)

        val slot = SelectionScreen("Equip to which Slot?", validSlots, true).run()

        slot.equip(item)

        item.equippedTo(character)
    }

    inline fun <reified T : Item> remove(item: T) {

        for (s in getValidSlots(item)) {
            s.remove(item)
        }
    }

    inline fun <reified T : Item> getValidSlots(item: T): Collection<Slot<T>> {
        return slots.getValues().filter { s -> s.validItem<T>(item) }.map { s -> s as Slot<T> }
    }

    fun pickUp(item: Item) {
        backpack.add(item)
    }

    fun getDefense(stat: Class<out ArmorStat>): Double? {
        return getEquippedItems(Armor::class.java).parallelStream().mapToDouble { a -> a.getStatAmount(stat) }.sum()
    }
}
