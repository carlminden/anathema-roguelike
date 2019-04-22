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
package stats

import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap
import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.EffectCollection
import com.anathema_roguelike.stats.effects.HasEffect

import scala.reflect.runtime.universe._
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.stats.characterstats.CharacterStat
import com.anathema_roguelike.stats.itemstats.ItemStat
import com.anathema_roguelike.stats.locationstats.LocationStat

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