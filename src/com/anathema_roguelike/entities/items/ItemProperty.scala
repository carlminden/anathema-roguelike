package com.anathema_roguelike
package entities.items

import com.anathema_roguelike.main.utilities.HasWeightedProbability
import com.anathema_roguelike.stats.effects.{Effect, HasEffect}
import com.anathema_roguelike.stats.itemstats.ItemStat

abstract class ItemProperty[T <: Item](name: String, weight: Double) extends HasEffect[Effect[Item, ItemStat]] with HasWeightedProbability {

  def getName: String = name

  protected def getWeight: Double = weight
}
