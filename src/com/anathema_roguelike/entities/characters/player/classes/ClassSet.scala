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
package entities.characters.player.classes

import com.anathema_roguelike.main.utilities.{AutoClassToInstanceMap, Utils}

import scala.collection.mutable
import scala.reflect.ClassTag
import com.anathema_roguelike.entities.characters.Character
import scala.reflect._
import scala.reflect.runtime._
import scala.reflect.runtime.universe._

object ClassSet {
  private val classes: AutoClassToInstanceMap[PlayerClass] = new AutoClassToInstanceMap[PlayerClass]
}

class ClassSet(var character: Character) {

  private val classLevels: mutable.Map[Class[_ <: PlayerClass], Int] = mutable.Map[Class[_ <: PlayerClass], Int]()

  Utils.getSubclasses[PlayerClass]().foreach(c => classLevels.put(c, 0))

  def getClassLevels[T <: PlayerClass : TypeTag]: Int = classLevels.getOrElse(typeTagToClass[T], 0)

  def grantClassLevel[T <: PlayerClass : TypeTag](): Unit = {

    val newLevel: Int = getClassLevels[T] + 1

    ClassSet.classes.get[T].getLevel(newLevel).grant(character)
    classLevels.put(typeTagToClass[T], newLevel)
  }

  def getClasses: Iterable[Class[_ <: PlayerClass]] = {
    classLevels.collect {
      case (k, v) if v > 0 => k
    }
  }
}
