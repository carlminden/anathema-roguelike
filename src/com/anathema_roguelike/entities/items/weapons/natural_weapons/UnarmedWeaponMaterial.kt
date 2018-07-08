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
package com.anathema_roguelike.entities.items.weapons.natural_weapons

import java.util.Optional

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.stats.effects.AdditiveCalculation
import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.Modifier
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage
import com.anathema_roguelike.stats.itemstats.ItemStat

class UnarmedWeaponMaterial : NaturalWeaponMaterial("Unarmed") {

    override fun getEffect(): Optional<Effect<Item, ItemStat>> {
        val effect = super.getEffect()
        effect.ifPresent { e ->
            e.addModifier(Modifier(BaseWeaponDamage::class.java, AdditiveCalculation.build {
                val wearer = e.target.wearer

                if (wearer.isPresent) wearer.get().level as Double else 0.0
            }
            ))
        }

        return effect
    }

}
