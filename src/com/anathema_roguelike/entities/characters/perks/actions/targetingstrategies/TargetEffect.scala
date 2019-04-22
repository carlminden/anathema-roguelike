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
package entities.characters.perks.actions.targetingstrategies

import com.anathema_roguelike.stats.Stat
import com.anathema_roguelike.stats.effects.{Effect, HasEffect}

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

abstract class TargetEffect[T <: Targetable : TypeTag, +S <: Stat[T]](var name: String) extends HasEffect[Effect[T, S]] {

  def getTargetType: Class[T] = typeTagToClass

  def applyTo(target: T): Unit = {
    getEffect.foreach(e => e.applyTo(target))
  }

  override def toString: String = name
}