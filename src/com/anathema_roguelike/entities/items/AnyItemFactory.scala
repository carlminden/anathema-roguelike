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
package entities.items

import com.anathema_roguelike.entities.items.ItemType.AnyItem
import com.anathema_roguelike.entities.items.armor.ArmorFactory
import com.anathema_roguelike.entities.items.weapons.WeaponFactory

class AnyItemFactory() extends ItemFactory[Item] {
  addFactory(new WeaponFactory)
  addFactory(new ArmorFactory)
  addFactory(new AmuletFactory)

  override def getSupportedType: Class[_ <: ItemType[_ <: Item]] = classOf[AnyItem]
}
