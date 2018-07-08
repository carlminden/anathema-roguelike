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
import com.anathema_roguelike.entities.items.Amulet

class Neck(character: Character) : Slot<Amulet>(character) {

    internal var amulets = HashSet<Amulet>()

    override val equippedItems: Collection<Amulet>
        get() = amulets

    override fun equipItem(item: Amulet) {
        amulets.add(item)
        item.equippedTo(character)
    }

    override fun removeItem(item: Amulet) {
        if (amulets.contains(item)) {
            amulets.remove(item)
            item.removedFrom(character)

            character.inventory.pickUp(item)
        }

    }
}
