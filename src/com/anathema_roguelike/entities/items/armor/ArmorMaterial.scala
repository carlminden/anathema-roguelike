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

import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.stats.effects.{Effect, Modifier, MultiplicativeCalculation}
import com.anathema_roguelike.stats.itemstats.{AttenuationDefense, ConcealmentDefense, ItemStat, VeilDefense, Weight}

object ArmorMaterial {
  val CLOTH = "Cloth"
  val LEATHER = "Leather"
  val CHAINMAIL = "Chainmail"
  val PLATE = "Plate"
  val UMBRALSILK = "Umbralsilk"
  val DRAGONHIDE = "Dragonhide"
  val COLD_IRON = "Cold Iron"
  val BLACKSTEEL = "Blacksteel"
  val SHADOWEAVE = "Shadoweave"
  val SILENAI_CRYSTAL = "Silenai Crystal"
  val MITHRIL = "Mithril"
  val MAGEPLATE = "Mageplate"
}

class ArmorMaterial(name: String, weight: Double) extends ArmorProperty(name, weight) {

  override def getEffect: Option[Effect[Item, ItemStat]] = {
    new Effect(this, List(
      new Modifier[ConcealmentDefense](MultiplicativeCalculation.build(() => getConcealment)),
      new Modifier[AttenuationDefense](MultiplicativeCalculation.build(() => getAttenuation)),
      new Modifier[VeilDefense](MultiplicativeCalculation.build(() => getVeil)),
      new Modifier[Weight](MultiplicativeCalculation.build(() => getWeight))
    ))
  }
}
