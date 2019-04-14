/** *****************************************************************************
  * Copyright (C) 2017 Carl Minden
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
  * *****************************************************************************//*******************************************************************************
  * Copyright (C) 2017 Carl Minden
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
  * *****************************************************************************/

package com.anathema_roguelike
package entities.items.weapons

import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.stats.effects.{AdditiveCalculation, Effect, Modifier, MultiplicativeCalculation}
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage
import com.anathema_roguelike.stats.itemstats.ItemStat
import com.anathema_roguelike.stats.itemstats.Weight

class MetalWeaponMaterial(name: String, weight: Double, damage: Double) extends WeaponMaterial(name, weight, damage) {

  override def getEffect: Option[Effect[Item, ItemStat]] = {
    new Effect(
      this,
      List(
        new Modifier[BaseWeaponDamage](AdditiveCalculation.build(() => getDamage)),
        new Modifier[Weight](AdditiveCalculation.build(() => getWeight))
      )
    )
  }
}