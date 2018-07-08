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
package com.anathema_roguelike.entities.items.armor

import com.anathema_roguelike.entities.items.ItemPropertyCache
import com.anathema_roguelike.entities.items.ItemType
import com.anathema_roguelike.main.display.VisualRepresentation

class Chestpiece : Armor, ItemType<Chestpiece> {

    constructor(material: ArmorMaterial) : super(ItemPropertyCache.getProperty<ArmorType>(ArmorType::class.java, "Chestpiece"), material) {}

    constructor(material: String) : super(
            ItemPropertyCache.getProperty<ArmorType>(ArmorType::class.java, "Chestpiece"),
            ItemPropertyCache.getProperty<ArmorMaterial>(ArmorMaterial::class.java, material)
        ) {}

    override fun getVisualRepresentation(): VisualRepresentation {
        return VisualRepresentation(']', color)
    }
}
