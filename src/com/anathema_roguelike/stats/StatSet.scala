package com.anathema_roguelike.stats

import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap
import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.EffectCollection
import com.anathema_roguelike.stats.effects.HasEffect

import scala.reflect.runtime.universe._

class StatSet[T, S <: Stat[_ <: T] : TypeTag](val obj: T, val objectType: Class[T], val statType: Class[S]) {

  private val effects = new EffectCollection[T, S](obj)
  private val stats = new AutoClassToInstanceMap[S](List(objectType), obj)

  protected def getEffects: EffectCollection[T, S] = effects

  def applyEffect(effect: Effect[T, _ <: S]): Unit = {
    effects.apply(effect)
  }

  def removeEffectBySource(source: HasEffect[_ <: Effect[_ <: T, _ <: S]]): Unit = {
    effects.removeBySource(source)
  }

  def getStat[G <: S : TypeTag]: G = stats.get[G]

  private def getBaseValue[G <: S : TypeTag] = getStat[G].getAmount

  def getValue[G <: S : TypeTag]: Double = {
    val base = getBaseValue[G]
    val modifier = effects.getStatBonus[G]
    base + modifier * effects.getStatMultiplier[G]
  }
}