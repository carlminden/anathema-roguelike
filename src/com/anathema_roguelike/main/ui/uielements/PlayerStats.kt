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
package com.anathema_roguelike.main.ui.uielements


import java.util.stream.Collectors

import com.anathema_roguelike.entities.characters.inventory.Chest
import com.anathema_roguelike.entities.characters.inventory.Feet
import com.anathema_roguelike.entities.characters.inventory.Head
import com.anathema_roguelike.entities.characters.inventory.Legs
import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon
import com.anathema_roguelike.entities.characters.inventory.SecondaryWeapon
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.Point
import com.anathema_roguelike.stats.characterstats.attributes.Agility
import com.anathema_roguelike.stats.characterstats.attributes.Constitution
import com.anathema_roguelike.stats.characterstats.attributes.Intelligence
import com.anathema_roguelike.stats.characterstats.attributes.Perception
import com.anathema_roguelike.stats.characterstats.attributes.Strength
import com.anathema_roguelike.stats.characterstats.resources.CurrentHealth
import com.anathema_roguelike.stats.characterstats.secondarystats.Health
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.Visibility
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.VisibilityLevel
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.defenses.Attenuation
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.defenses.Concealment
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.defenses.Veil

import squidpony.squidgrid.gui.gdx.SColor

class PlayerStats(position: Point, width: Int, height: Int, private val player: Player) : UIElement(position, width, height, "Character", 1f) {

    private var renderPos: Int = 0

    override fun renderContent() {
        renderPos = 0

        renderChar()
        renderPos++
        renderResources()
        renderPos++
        renderAbilities()
        renderPos++
        renderOtherStats()
        renderPos++
        renderInventory()
    }

    fun renderChar() {
        renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, player.location.toString())
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Level " + player.level + " " + player.classes.map { c -> Utils.getName(c) }.joinToString { "," })
    }

    fun renderResources() {

        val currentHP = player.getStatAmount(CurrentHealth::class.java).toInt()
        val maxHP = player.getStatAmount(Health::class.java).toInt()

        val color = Color.factory.blend(Color.RED, Color.GREEN, currentHP.toDouble() / maxHP.toDouble())

        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "HP: $currentHP/$maxHP", color)

    }

    fun renderAbilities() {
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "STR: " + player.getStatAmount(Strength::class.java))
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "AGI: " + player.getStatAmount(Agility::class.java))
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "CON: " + player.getStatAmount(Constitution::class.java))
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "INT: " + player.getStatAmount(Intelligence::class.java))
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "PER: " + player.getStatAmount(Perception::class.java))
    }

    fun renderOtherStats() {
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Concealment: " + player.getStatAmount(Concealment::class.java))
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Veil: " + player.getStatAmount(Veil::class.java))
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Attenuation: " + player.getStatAmount(Attenuation::class.java))
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Visibility: " + player.getStatAmount(Visibility::class.java).toInt())

        val visibility = player.getStat(Visibility::class.java).visibilityLevel

        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Visibility Level: " + visibility.getName(), visibility.color)
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Light Level: " + player.environment.lightLevels.get(player.position))
    }

    fun renderInventory() {
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Primary Weapon: " + player.inventory.getSlot<PrimaryWeapon>().equippedItem)
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Secondary Weapon: " + player.inventory.getSlot<SecondaryWeapon>().equippedItem)
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Head: " + player.inventory.getSlot<Head>().equippedItem)
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Chest: " + player.inventory.getSlot<Chest>().equippedItem)
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Legs: " + player.inventory.getSlot<Legs>().equippedItem)
        renderString(DisplayLayer.UI_FOREGROUND, 0, ++renderPos, "Feet: " + player.inventory.getSlot<Feet>().equippedItem)
    }
}
