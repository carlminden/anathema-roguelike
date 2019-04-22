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

import com.anathema_roguelike.entities.items.weapons.MetalWeaponMaterial
import com.anathema_roguelike.entities.items.weapons.WeaponMaterial

object MeleeWeaponType {
  case class BluntWeapon(name: String, attackSpeed: Double, damage: Double, weight: Double) extends MeleeWeaponType(name, weight, attackSpeed, damage)
  case class LongBlade(name: String, attackSpeed: Double, damage: Double, weight: Double) extends MeleeWeaponType(name, weight, attackSpeed, damage)
  case class ShortBlade(name: String, attackSpeed: Double, damage: Double, weight: Double) extends MeleeWeaponType(name, weight, attackSpeed, damage)
  case class Spear(name: String, attackSpeed: Double, damage: Double, weight: Double) extends MeleeWeaponType(name, weight, attackSpeed, damage)
}

class MeleeWeaponType(name: String, weight: Double, attackSpeed: Double, damage: Double) extends WeaponType(name, weight, attackSpeed, damage) {

  override def getRange = 1

  override def getMaterialType: Class[_ <: WeaponMaterial] = classOf[MetalWeaponMaterial]
}