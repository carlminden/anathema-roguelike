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
package entities.items.weapons.types

import com.anathema_roguelike.entities.items.{Item, ItemType}
import com.anathema_roguelike.entities.items.weapons.{Weapon, WeaponMaterial, WeaponProperty}
import com.anathema_roguelike.stats.effects.{AdditiveCalculation, Effect, Modifier}
import com.anathema_roguelike.stats.itemstats.{BaseWeaponDamage, ItemStat, WeaponRange, WeaponSpeed, Weight}

abstract class WeaponType(name: String, weight: Double, attackSpeed: Double, damage: Double) extends WeaponProperty(name, weight) with ItemType[Weapon] {

  def getAttackSpeed: Double = attackSpeed

  def getDamage: Double = damage

  def getMaterialType: Class[_ <: WeaponMaterial]

  def getRange: Double

  override def getEffect: Option[Effect[Item, ItemStat]] = {
    new Effect[Item, ItemStat](this, List(
      new Modifier[WeaponSpeed](AdditiveCalculation.build(() => getAttackSpeed)),
      new Modifier[BaseWeaponDamage](AdditiveCalculation.build(() => getDamage)),
      new Modifier[WeaponRange](AdditiveCalculation.build(() => getRange)),
      new Modifier[Weight](AdditiveCalculation.build(() => getWeight))))
  }
}
