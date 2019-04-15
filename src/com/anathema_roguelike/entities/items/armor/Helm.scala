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

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.items.ItemPropertyCache
import com.anathema_roguelike.entities.items.ItemType
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.display.VisualRepresentation

class Helm(material: ArmorMaterial, location: Either[Location, Character])
  extends Armor(ItemPropertyCache.getProperty(classOf[ArmorType], "Helm"), material, location) with ItemType[Helm] {

  def this(material: String, location: Either[Location, Character]) {
    this( ItemPropertyCache.getProperty(classOf[ArmorMaterial], material), location)
  }

  override def getVisualRepresentation = new VisualRepresentation('^', getColor)
}