/*******************************************************************************
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
 * along with this program.  If not, see <http:></http:>//www.gnu.org/licenses/>.
 */
package com.anathema_roguelike.entities.items.weapons.types

import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.entities.items.ItemType
import com.anathema_roguelike.entities.items.weapons.Weapon
import com.anathema_roguelike.entities.items.weapons.WeaponMaterial
import com.anathema_roguelike.entities.items.weapons.WeaponProperty
import com.anathema_roguelike.stats.effects.AdditiveCalculation
import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.Modifier
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage
import com.anathema_roguelike.stats.itemstats.ItemStat
import com.anathema_roguelike.stats.itemstats.WeaponRange
import com.anathema_roguelike.stats.itemstats.WeaponSpeed
import com.anathema_roguelike.stats.itemstats.Weight

abstract class WeaponType : WeaponProperty, ItemType<Weapon> {

    var attackSpeed: Double = 0.toDouble()
    var damage: Double = 0.toDouble()

    abstract val materialType: Class<out WeaponMaterial>

    abstract val range: Double

    constructor() : super() {}

    constructor(name: String, weight: Double, attackSpeed: Double, damage: Double) : super(name, weight) {
        this.attackSpeed = attackSpeed
        this.damage = damage
    }

    override fun getEffect(): java.util.Optional<Effect<Item, ItemStat>> {

        return java.util.Optional.of(Effect<Item, ItemStat>(this,
                Modifier(WeaponSpeed::class.java, AdditiveCalculation.build { attackSpeed }),
                Modifier(BaseWeaponDamage::class.java, AdditiveCalculation.build { damage }),
                Modifier(WeaponRange::class.java, AdditiveCalculation.build { range }),
                Modifier(Weight::class.java, AdditiveCalculation.build { weight })
        ))
    }
}
