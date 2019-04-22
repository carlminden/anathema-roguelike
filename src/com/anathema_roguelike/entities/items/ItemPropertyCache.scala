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
import kantan.csv._
import kantan.csv.ops._

import com.anathema_roguelike.entities.items.weapons._
import com.anathema_roguelike.entities.items.weapons.types.MeleeWeaponType.{BluntWeapon, LongBlade, ShortBlade, Spear}

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.reflect.runtime.universe._

object ItemPropertyCache {

  private val builder = ImmutableTable.builder[Class[_ <: ItemProperty[_]], String, ItemProperty[_]]

  loadPropertyFile[ArmorMaterial](builder, "/items/materials/armor_materials.csv", RowDecoder.decoder(0, 1, 2, 3, 4)(ArmorMaterial.apply))
  loadPropertyFile[ArmorType](builder, "/items/armor_types.csv", RowDecoder.decoder(0, 1, 2, 3, 4)(ArmorType.apply))
  loadPropertyFile[MetalWeaponMaterial](builder, "/items/materials/metal_weapon_materials.csv", RowDecoder.decoder(0, 1, 2)(MetalWeaponMaterial.apply))
  loadPropertyFile[WoodWeaponMaterial](builder, "/items/materials/wood_weapon_materials.csv", RowDecoder.decoder(0, 1, 2, 3)(WoodWeaponMaterial.apply))
  loadPropertyFile[ShortBlade](builder, "/items/weapon_types/short_blade.csv", RowDecoder.decoder(0, 1, 2, 3)(ShortBlade.apply))
  loadPropertyFile[LongBlade](builder, "/items/weapon_types/long_blade.csv", RowDecoder.decoder(0, 1, 2, 3)(LongBlade.apply))
  loadPropertyFile[BluntWeapon](builder, "/items/weapon_types/blunt.csv", RowDecoder.decoder(0, 1, 2, 3)(BluntWeapon.apply))
  loadPropertyFile[Spear](builder, "/items/weapon_types/spear.csv", RowDecoder.decoder(0, 1, 2, 3)(Spear.apply))
  loadPropertyFile[Bow](builder, "/items/weapon_types/bow.csv", RowDecoder.decoder(0, 1, 2, 3, 4)(Bow.apply))
  loadPropertyFile[Crossbow](builder, "/items/weapon_types/crossbow.csv", RowDecoder.decoder(0, 1, 2, 3, 4)(Crossbow.apply))
  loadPropertyFile[ThrowingWeapon](builder, "/items/weapon_types/throwing.csv", RowDecoder.decoder(0, 1, 2, 3)(ThrowingWeapon.apply))

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
        properties ++= ItemPropertyCache.propertyCache.row(row).values.asScala.asInstanceOf[Iterable[T]]
      }
    })

    properties
  }

  def getProperty[T <: ItemProperty[_]](itemType: Class[T], name: String): T = {
    ItemPropertyCache.propertyCache.row(itemType).get(name).asInstanceOf[T]
  }

  private def loadPropertyFile[T <: ItemProperty[_] : TypeTag](
        builder: ImmutableTable.Builder[Class[_ <: ItemProperty[_]], String, ItemProperty[_]],
        filename: String,
        decoder: RowDecoder[T]): Unit = {

    val rawData: java.net.URL = getClass.getResource(filename)

    implicit val d = decoder

    rawData.asCsvReader[T](rfc.withHeader).foreach {
      case Right(row) => builder.put(typeTagToClass[T], row.getName, row)
    }

  }

  def main(args: Array[String]): Unit = {
    ItemPropertyCache.getProperties(classOf[ArmorMaterial]).foreach(p => System.out.println(p))
  }
}