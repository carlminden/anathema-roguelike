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
