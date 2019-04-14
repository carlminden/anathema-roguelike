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

import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.utilities.{HasWeightedProbability, Utils}
import com.google.common.collect.HashMultimap
import com.anathema_roguelike.entities.characters.Character

import scala.collection.mutable.ListBuffer
import scala.reflect.runtime.universe._

abstract class ItemFactory[T <: Item] extends HasWeightedProbability {

  private val factories: ListBuffer[ItemFactory[_ <: T]] = ListBuffer()

//  private val factories: HashMultimap[Class[_ <: ItemType[_ <: T]], ItemFactory[_ <: T]] = {
//
//    HashMultimap.create[Class[_ <: ItemType[_ <: T]], ItemFactory[_ <: T]]
//  }

  protected def addFactory[F <: ItemFactory[_ <: T]](factory: F): Unit = {
    factories :+ factory

    factory.getSubFactories.foreach(t => addFactory(t))
  }

  def getSubFactories: Iterable[ItemFactory[_ <: T]] = factories

  private def getFactories[P <: ItemType[_ <: T] : TypeTag]: Iterable[ItemFactory[T]] = {
    factories.collect{
      case f: ItemFactory[T] if f.getSupportedType.isAssignableFrom(typeTagToClass[P]) => f
    }
  }

  def generateByType[S <: ItemType[_ <: T] : TypeTag](location: Either[Location, Character]): S = {

    val cls: Class[S] = typeTagToClass[S]

    if (cls == getSupportedType) {
      generate(location).asInstanceOf[S]
    } else if (getFactories[S].isEmpty) {
      throw new RuntimeException("This Factory does not support that type")
    } else {
      val f: ItemFactory[T] = Utils.getWeightedRandomSample(getFactories(cls))

      if (f.getSupportedType == cls) {
        f.generate(location).asInstanceOf[S]
      } else {
        f.generateByType[S](location)
      }
    }
  }

  def generate(location: Either[Location, Character]): T = {
    Utils.getWeightedRandomSample(factories).generate(location)
  }

  def getSupportedType: Class[_ <: ItemType[_ <: T]]
}
