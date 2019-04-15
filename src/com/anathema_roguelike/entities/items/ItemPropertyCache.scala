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

import com.anathema_roguelike.entities.items.armor.{ArmorMaterial, ArmorType}
import com.anathema_roguelike.entities.items.weapons.types._
import com.google.common.collect.ImmutableTable
import org.supercsv.cellprocessor.ParseDouble
import org.supercsv.cellprocessor.constraint.NotNull
import org.supercsv.cellprocessor.ift.CellProcessor
import org.supercsv.io.CsvBeanReader
import org.supercsv.prefs.CsvPreference
import java.io.InputStreamReader

import com.anathema_roguelike.entities.items.weapons._
import com.anathema_roguelike.entities.items.weapons.types.MeleeWeaponType.{BluntWeapon, LongBlade, ShortBlade, Spear}

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._
import scala.collection.mutable

object ItemPropertyCache {

  private val builder = ImmutableTable.builder[Class[_ <: ItemProperty[_]], String, ItemProperty[_]]

  loadPropertyFile(builder, classOf[ArmorMaterial], "/items/materials/armor_materials.csv", cellProcessors(4))
  loadPropertyFile(builder, classOf[ArmorType], "/items/armor_types.csv", cellProcessors(4))
  loadPropertyFile(builder, classOf[MetalWeaponMaterial], "/items/materials/metal_weapon_materials.csv", cellProcessors(2))
  loadPropertyFile(builder, classOf[WoodWeaponMaterial], "/items/materials/wood_weapon_materials.csv", cellProcessors(3))
  loadPropertyFile(builder, classOf[ShortBlade], "/items/weapon_types/short_blade.csv", cellProcessors(3))
  loadPropertyFile(builder, classOf[LongBlade], "/items/weapon_types/long_blade.csv", cellProcessors(3))
  loadPropertyFile(builder, classOf[BluntWeapon], "/items/weapon_types/blunt.csv", cellProcessors(3))
  loadPropertyFile(builder, classOf[Spear], "/items/weapon_types/spear.csv", cellProcessors(3))
  loadPropertyFile(builder, classOf[Bow], "/items/weapon_types/bow.csv", cellProcessors(4))
  loadPropertyFile(builder, classOf[Crossbow], "/items/weapon_types/crossbow.csv", cellProcessors(4))
  loadPropertyFile(builder, classOf[ThrowingWeapon], "/items/weapon_types/throwing.csv", cellProcessors(3))

  private val propertyCache: ImmutableTable[Class[_ <: ItemProperty[_]], String, ItemProperty[_]] = builder.build

  private def cellProcessors(columns: Int) = {
    val ret = ListBuffer[CellProcessor]()
    ret += new NotNull

    (0 until columns).foreach(i => ret += new ParseDouble)

    ret.toArray
  }

  def getProperties[T <: ItemProperty[_]](itemType: Class[T]): Iterable[_ <: T] = {
    val properties = mutable.Set[T]()
    propertyCache.rowKeySet.forEach(r => {
      if(itemType.isAssignableFrom(r)) {
        val row = itemType.getClass.cast(r)
        properties.addAll(ItemPropertyCache.propertyCache.row(row).values.asScala.asInstanceOf[Iterable[T]])
      }
    })

    properties
  }

  def getProperty[T <: ItemProperty[_]](itemType: Class[T], name: String): T = {
    ItemPropertyCache.propertyCache.row(itemType).get(name).asInstanceOf[T]
  }

  private def loadPropertyFile[T <: ItemProperty[_]](
    builder: ImmutableTable.Builder[Class[_ <: ItemProperty[_]], String, ItemProperty[_]],
    itemType: Class[T],
    filename: String,
    processors: Array[CellProcessor]): Unit = {

      val reader = new InputStreamReader(getClass.getResourceAsStream(filename))
      val beanReader = new BeanReader(reader, CsvPreference.STANDARD_PREFERENCE)
      val header = beanReader.getHeader(true)

      var obj = beanReader.readByClass(itemType, header, processors:_*)

      while(obj != null) {

        builder.put(itemType, obj.getName, obj)
        obj = beanReader.read(itemType, header, processors:_*)
      }
    }

  def main(args: Array[String]): Unit = {
    ItemPropertyCache.getProperties(classOf[Spear]).foreach(p => System.out.println(p.getName))
  }
}