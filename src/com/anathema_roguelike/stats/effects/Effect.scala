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
package stats.effects

import com.anathema_roguelike.actors.Duration
import com.anathema_roguelike.stats.Stat

import scala.collection.mutable.ListBuffer
import scala.reflect.runtime.universe._

class Effect[T, +S <: Stat[_ <: T]](
    source: Option[_ <: HasEffect[_ <: Effect[T, S]]],
    modifiers: List[Modifier[_ <: Stat[_ <: T]]],
    duration: Duration = Duration.permanent(),
    var target: Option[T] = None) {

  private val modifiersListBuffer: ListBuffer[Modifier[_ <: Stat[_ <: T]]] = ListBuffer[Modifier[_ <: Stat[_ <: T]]]() ++ modifiers


  def getSource: Option[_ <: HasEffect[_ <: Effect[T, S]]] = source

  def getModifiers: Iterable[Modifier[_ <: Stat[_ <: T]]] = modifiersListBuffer

  def addModifier(modifier: Modifier[_ <: Stat[_ <: T]]): Unit = {
    modifiersListBuffer += modifier
  }

  def getAdditiveBonus[B <: Stat[_] : TypeTag]: Double = {
    getModifiers.filter(m => m.getAffectedStat == typeTagToClass[B]).foldLeft(0.0) {
      (ret, modifier) => ret + modifier.getAdditiveAmount
    }
  }

  def getMultiplier[M <: Stat[_] : TypeTag]: Double = {
    getModifiers.filter(m => m.getAffectedStat == typeTagToClass[M]).foldLeft(1.0) {
      (ret, modifier) => ret * modifier.getMultiplier
    }
  }

  def getDuration: Duration = duration

  override def toString: String = {
    val builder: StringBuilder = new StringBuilder
    builder.append("Effect: ")
    builder.append("Duration: ").append(duration.getRemaining)

    modifiersListBuffer.foreach(m => {
      builder.append(" Modifier: " + m.getAffectedStat.getSimpleName + " +" + m.getAdditiveAmount + " *" + m.getMultiplier)
    })

    builder.toString
  }

  final def applyTo(target: T): Unit = {
    this.target = target
    onApplicationCallback(target)
  }

  final def remove(): Unit = {
    getTarget.foreach(t => onExpirationCallback(t))
    this.target = Option.empty
  }

  def onApplicationCallback(target: T): Unit = { }

  def onExpirationCallback(target: T): Unit = { }

  def getTarget: Option[T] = target
}
