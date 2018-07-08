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
package com.anathema_roguelike.entities.items.weapons

import java.util.Optional

import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.Modifier
import com.anathema_roguelike.stats.effects.MultiplicativeCalculation
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage
import com.anathema_roguelike.stats.itemstats.ItemStat
import com.anathema_roguelike.stats.itemstats.Weight

class MetalWeaponMaterial : WeaponMaterial {

    constructor() : super() {}

    constructor(name: String, weight: Double, damage: Double) : super(name, weight, damage) {}

    override fun getEffect(): Optional<Effect<Item, ItemStat>> {

        return Optional.of(Effect<Item, ItemStat>(this,
                Modifier(BaseWeaponDamage::class.java, MultiplicativeCalculation.build { damage }),
                Modifier(Weight::class.java, MultiplicativeCalculation.build { weight })
        ))
    }
}
