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

import java.util.Random

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon
import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.entities.items.ItemPropertyCache
import com.anathema_roguelike.entities.items.weapons.types.MeleeWeaponType
import com.anathema_roguelike.entities.items.weapons.types.RangedWeaponType
import com.anathema_roguelike.entities.items.weapons.types.WeaponType
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.VisualRepresentation
import com.anathema_roguelike.stats.characterstats.secondarystats.Accuracy
import com.anathema_roguelike.stats.characterstats.secondarystats.BonusWeaponDamage
import com.anathema_roguelike.stats.characterstats.secondarystats.WeaponDamageMultiplier
import com.anathema_roguelike.stats.itemstats.BaseWeaponDamage
import com.anathema_roguelike.stats.itemstats.Weight

import squidpony.squidgrid.gui.gdx.SColor

open class Weapon(val type: WeaponType, val material: WeaponMaterial) : Item() {

    val weaponDamage: Int
        get() {

            if (wearer.isPresent) {
                val character = wearer.get()
                val primaryWeapon = character.inventory.getSlot<PrimaryWeapon>().equippedItem

                val baseWeaponDamage = primaryWeapon!!.getStatAmount(BaseWeaponDamage::class.java)
                val weight = primaryWeapon.getStatAmount(Weight::class.java)

                val bonusWeaponDamage = character.getStatAmount(BonusWeaponDamage::class.java).toInt()
                var weaponDamageMultiplier = character.getStatAmount(WeaponDamageMultiplier::class.java)
                var accuracy = character.getStatAmount(Accuracy::class.java).toInt()

                weaponDamageMultiplier += 0.05 * weight

                accuracy = Math.min(50, accuracy)

                val min = -0.25 + accuracy.toDouble() / 100
                val max = 0.25

                val r = Random()

                val accuracyMultiplier = 1.0 + min + (max - min) * r.nextDouble()

                return ((baseWeaponDamage * accuracyMultiplier + bonusWeaponDamage) * weaponDamageMultiplier).toInt()
            } else {
                throw RuntimeException("Cannot get Weapon Damage of unequipped Weapon")
            }
        }

    private val color: SColor
        get() = if (material is WoodWeaponMaterial) {
            Color.BROWN
        } else if (material is MetalWeaponMaterial) {
            Color.GRAY
        } else {
            Color.ERROR
        }

    private val displayCharacter: Char
        get() = if (type is MeleeWeaponType) {
            '|'
        } else if (type is RangedWeaponType) {
            ')'
        } else {
            'X'
        }

    init {

        applyEffect(type.effect)
        applyEffect(material.effect)
    }

    constructor(type: String, material: String) : this(ItemPropertyCache.getProperty<WeaponType>(WeaponType::class.java, type), ItemPropertyCache.getProperty<WeaponMaterial>(WeaponMaterial::class.java, material)) {}

    override fun toString(): String {
        return material.name + " " + type.name
    }

    override fun getVisualRepresentation(): VisualRepresentation {
        return VisualRepresentation(displayCharacter, color)
    }
}
