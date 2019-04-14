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
