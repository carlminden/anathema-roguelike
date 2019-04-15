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
package entities.items.weapons

import java.util.Random

import com.anathema_roguelike.entities.items.{Item, ItemPropertyCache}
import com.anathema_roguelike.entities.items.weapons.types.{MeleeWeaponType, RangedWeaponType, WeaponType}
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.display.{Color, VisualRepresentation}
import com.anathema_roguelike.stats.characterstats.secondarystats.{Accuracy, BonusWeaponDamage, WeaponDamageMultiplier}
import com.anathema_roguelike.stats.itemstats.{BaseWeaponDamage, Weight}
import com.anathema_roguelike.entities.characters.Character

class Weapon(weaponType: WeaponType, material: WeaponMaterial, location: Either[Location, Character]) extends Item(location) {

  def this(weaponType: String, material: String, location: Either[Location, Character]) {
    this(
      ItemPropertyCache.getProperty(classOf[WeaponType], weaponType),
      ItemPropertyCache.getProperty(classOf[WeaponMaterial], material),
      location
    )
  }

  applyEffect(weaponType.getEffect)
  applyEffect(material.getEffect)

  def getType: WeaponType = weaponType

  def getMaterial: WeaponMaterial = material

  override def toString: String = material.getName + " " + weaponType.getName

  def getWeaponDamage: Int = {
    if (getWearer.isDefined) {
      val character = getWearer.get

      val baseWeaponDamage = getStatAmount[BaseWeaponDamage]
      val weight = getStatAmount[Weight]

      val bonusWeaponDamage = character.getStatAmount[BonusWeaponDamage].toInt
      var weaponDamageMultiplier = character.getStatAmount[WeaponDamageMultiplier]
      var accuracy = character.getStatAmount[Accuracy].toInt

      weaponDamageMultiplier += 0.05 * weight
      accuracy = Math.min(50, accuracy)
      val min = -0.25 + accuracy.toDouble / 100
      val max = 0.25
      val r = new Random
      val accuracyMultiplier = 1 + min + (max - min) * r.nextDouble

      (((baseWeaponDamage * accuracyMultiplier) + bonusWeaponDamage) * weaponDamageMultiplier).toInt
    } else {
      throw new RuntimeException("Cannot get Weapon Damage of unequipped Weapon")
    }
  }

  override def getVisualRepresentation = new VisualRepresentation(getDisplayCharacter, getColor)

  private def getColor = material match {
    case _: WoodWeaponMaterial => Color.BROWN
    case _: MetalWeaponMaterial => Color.GRAY
    case _ => Color.ERROR
  }

  private def getDisplayCharacter = weaponType match {
    case _: MeleeWeaponType => '|'
    case _: RangedWeaponType => ')'
    case _ => 'X'
  }
}
