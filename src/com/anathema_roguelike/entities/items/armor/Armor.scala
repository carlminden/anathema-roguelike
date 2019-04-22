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

import com.anathema_roguelike.entities.items.{Item, ItemPropertyCache}
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.display.Color
import squidpony.squidgrid.gui.gdx.SColor
import com.anathema_roguelike.entities.characters.Character

abstract class Armor(armorType: ArmorType, material: ArmorMaterial) extends Item {


  applyEffect(armorType.getEffect)
  applyEffect(material.getEffect)

  def this(armorType: String, material: String) {
    this(
      ItemPropertyCache.getProperty(classOf[ArmorType], armorType),
      ItemPropertyCache.getProperty(classOf[ArmorMaterial], material)
    )
  }

  def this(armorType: String, material: ArmorMaterial) {
    this(ItemPropertyCache.getProperty(classOf[ArmorType], armorType), material)
  }

  def this(armorType: ArmorType, material: String) {
    this(armorType, ItemPropertyCache.getProperty(classOf[ArmorMaterial], material))
  }

  def getType: ArmorType = armorType

  def getMaterial: ArmorMaterial = material

  override def toString: String = material.getName + " " + armorType.getName

  def getColor: SColor = material.getName match {
    case ArmorMaterial.UMBRALSILK | ArmorMaterial.SHADOWEAVE | ArmorMaterial.BLACKSTEEL =>
      Color.DARK_GRAY
    case ArmorMaterial.CLOTH | ArmorMaterial.SILENAI_CRYSTAL =>
      Color.WHITE
    case ArmorMaterial.LEATHER | ArmorMaterial.DRAGONHIDE =>
      Color.LIGHT_BROWN
    case ArmorMaterial.CHAINMAIL | ArmorMaterial.COLD_IRON | ArmorMaterial.MITHRIL | ArmorMaterial.MAGEPLATE | ArmorMaterial.PLATE =>
      Color.GRAY
    case _ =>
      Color.ERROR
  }
}
