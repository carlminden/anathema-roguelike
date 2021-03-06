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
package entities.items.weapons.natural_weapons

import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.entities.items.weapons.WeaponMaterial
import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.Modifier
import com.anathema_roguelike.stats.effects.MultiplicativeCalculation
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage
import com.anathema_roguelike.stats.itemstats.ItemStat
import com.anathema_roguelike.stats.itemstats.Weight

class NaturalWeaponMaterial(name: String) extends WeaponMaterial(name, 1.0, 1.0) {
  override def getEffect: Option[Effect[Item, ItemStat]] = {
    new Effect[Item, ItemStat](
      this,
      List(
        new Modifier[BaseWeaponDamage](MultiplicativeCalculation.build(() => getDamage)),
        new Modifier[Weight](MultiplicativeCalculation.build(() => getWeight))
      )
    )
  }
}