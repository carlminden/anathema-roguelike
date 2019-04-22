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
package entities.items.armor

import com.anathema_roguelike.entities.items.{Item, ItemType}
import com.anathema_roguelike.stats.effects.{AdditiveCalculation, Effect, Modifier}
import com.anathema_roguelike.stats.itemstats.{AttenuationDefense, ConcealmentDefense, ItemStat, VeilDefense, Weight}

case class ArmorType(name: String, concealment: Double, veil: Double, attenuation: Double, weight: Double)
  extends ArmorProperty(name, concealment, veil, attenuation, weight) with ItemType[Armor] {

  override def getEffect: Option[Effect[Item, ItemStat]] = {
    new Effect(
      this,List(
        new Modifier[ConcealmentDefense](AdditiveCalculation.build(() => concealment)),
        new Modifier[AttenuationDefense](AdditiveCalculation.build(() => attenuation)),
        new Modifier[VeilDefense](AdditiveCalculation.build(() => veil)),
        new Modifier[Weight](AdditiveCalculation.build(() => getWeight)))
      )
  }
}
