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
package entities.items.armor

import com.anathema_roguelike.entities.items.ItemFactory
import com.anathema_roguelike.entities.items.ItemPropertyCache
import com.anathema_roguelike.entities.items.ItemType
import com.anathema_roguelike.main.utilities.Utils

class ArmorFactory() extends ItemFactory[Armor] {
  Utils.getSubclasses[Armor]().foreach(t => {

    addFactory(new ItemFactory[Armor]() {
      override def getSupportedType: Class[_ <: ItemType[_ <: Armor]] = t.asInstanceOf[Class[_ <: ItemType[_ <: Armor]]]

      override def generate: Armor = {
        val material = Utils.getWeightedRandomSample(ItemPropertyCache.getProperties(classOf[ArmorMaterial]))

        t.getConstructor(classOf[ArmorMaterial]).newInstance(material)
      }
    })
  })

  override def getSupportedType: Class[_ <: ItemType[_ <: Armor]] = classOf[ArmorType]
}