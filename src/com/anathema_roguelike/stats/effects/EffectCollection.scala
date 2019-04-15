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


import com.anathema_roguelike.actors.TimeElapsedEvent
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.stats.Stat
import com.google.common.collect.{HashBiMap, Iterables}
import com.google.common.eventbus.Subscribe

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scala.reflect.runtime.universe._

class EffectCollection[T, S <: Stat[_ <: T]](var affected: T) {

  Game.getInstance.getEventBus.register(this)
  private val sourcedEffects: HashBiMap[HasEffect[_ <: Effect[_ <: T, _]], Effect[_ <: T, _]] = HashBiMap.create[HasEffect[_ <: Effect[_ <: T, _]], Effect[_ <: T, _]]
  private var unsourcedEffects: ListBuffer[Effect[_ <: T, _]] = ListBuffer()

  def getStatBonus[G <: S : TypeTag]: Double = {
    getEffects.foldLeft(0.0) {
      (bonus, effect) => bonus + effect.getAdditiveBonus[G]
    }
  }

  def getStatMultiplier[G <: S : TypeTag]: Double = {
    getEffects.foldLeft(1.0) {
      (bonus, effect) => bonus * effect.getMultiplier[G]
    }
  }

  @Subscribe
  def handleSegmentElapsedEvent(event: TimeElapsedEvent): Unit = {
    elapse(event.getElapsedTime)
    removeExpired()
  }

  def getEffects: Iterable[Effect[_ <: T, _]] = sourcedEffects.values.asScala ++ unsourcedEffects

  def apply(effect: Effect[T, _]): Unit = {

    if(effect.getSource.isDefined) {
      sourcedEffects.forcePut(effect.getSource.get, effect)
    } else {
      unsourcedEffects += effect
    }

    effect.applyTo(affected)
  }

  def removeBySource(source: HasEffect[_ <: Effect[_ <: T, _]]): Unit = {
    val effect: Effect[_ <: T, _] = sourcedEffects.get(source)
    if (effect != null) {
      sourcedEffects.remove(effect)
      effect.remove()
    }
  }

  def elapse(duration: Double): Unit = {
    for (effect <- getEffects) {
      effect.getDuration.elapse(duration)
    }
  }

  def removeExpired(): Unit = {
    sourcedEffects.entrySet.removeIf(entry => {
      if(entry.getValue.getDuration.isExpired) {
        entry.getValue.remove()
        true
      } else {
        false
      }
    })

    unsourcedEffects = unsourcedEffects.filterNot(e => {
      if(e.getDuration.isExpired) {
        e.remove()
        true
      }
      else false
    })
  }
}
