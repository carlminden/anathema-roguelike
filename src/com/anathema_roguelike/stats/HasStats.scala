package com.anathema_roguelike
package stats

import com.anathema_roguelike.stats.effects.{Effect, HasEffect}
import scala.reflect.runtime.universe._

trait HasStats[T, S <: Stat[_ <: T]] {

  def getStatSet: StatSet[T, S]

  def getStat[R <: S : TypeTag]: R = getStatSet.getStat[R]

  def getStatAmount[R <: S : TypeTag]: Double = getStatSet.getValue[R]

  def applyEffect(effect: Option[_ <: Effect[T, _ <: S]]): Unit = {
    effect.foreach(e => getStatSet.applyEffect(e))
  }

  def removeEffectBySource(source: HasEffect[_ <: Effect[_ <: T, _ <: S]]): Unit = {
    getStatSet.removeEffectBySource(source)
  }
}

