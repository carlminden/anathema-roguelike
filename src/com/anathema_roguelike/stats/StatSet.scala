package com.anathema_roguelike
package stats

import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap
import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.EffectCollection
import com.anathema_roguelike.stats.effects.HasEffect

import scala.reflect.runtime.universe._
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.stats.Stat._
import com.anathema_roguelike.stats.itemstats.ItemStat

object StatSet {
  type CharacterStats = StatSet[Character, CharacterStat]
  type LocationStats = StatSet[Location, LocationStat]
  type ItemStats = StatSet[Item, ItemStat]
}

class StatSet[T : TypeTag, S <: Stat[_ <: T] : TypeTag](val obj: T) {

  private val effects = new EffectCollection[T, S](obj)
  private val stats = new AutoClassToInstanceMap[S](List(typeTagToClass[T]), obj)

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