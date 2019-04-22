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
package main.utilities

import com.anathema_roguelike.stats.characterstats.attributes.Agility

import scala.collection.mutable
import scala.reflect.ClassTag
import scala.reflect.runtime._
import scala.reflect.runtime.universe._
import com.anathema_roguelike.entities.characters.Character

class AutoClassToInstanceMap[T : TypeTag](parameterTypes: List[Class[_]], args: Any*) {

  private val classToInstanceMap = mutable.Map[Class[_ <: T], T]()

  Utils.getSubclasses[T]().foreach(t => {
    classToInstanceMap.put(t, t.getConstructor(parameterTypes.toArray:_*).newInstance(args.map(_.asInstanceOf[AnyRef]):_*))
  })

  def this() {
    this(List())
  }

  def get[I <: T : TypeTag]: I = classToInstanceMap(typeTagToClass[I]).asInstanceOf[I]

  def getValues: Iterable[T] = classToInstanceMap.values
}
