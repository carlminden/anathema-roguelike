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
package entities.items.weapons

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.items.{ItemFactory, ItemPropertyCache, ItemType}
import com.anathema_roguelike.entities.items.weapons.types.WeaponType
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.utilities.Utils

import scala.collection.JavaConverters._

class WeaponFactory() extends ItemFactory[Weapon] {
  Utils.getSubclasses[WeaponType]().foreach(t => {

    addFactory(new ItemFactory[Weapon]() {
      override def getSupportedType: Class[_ <: ItemType[_ <: Weapon]] = t

      override def generate(location: Either[Location, Character]): Weapon = {
        val itemType = Utils.getWeightedRandomSample(ItemPropertyCache.getProperties(t))
        val material = Utils.getWeightedRandomSample(ItemPropertyCache.getProperties(itemType.getMaterialType))

        new Weapon(itemType, material, location)
      }
    })
  })

  override def getSupportedType: Class[_ <: ItemType[_ <: Weapon]] = classOf[WeaponType]
}
