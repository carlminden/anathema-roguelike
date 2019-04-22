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
package main.ui.uielements

import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.Point
import com.anathema_roguelike.stats.characterstats.attributes._
import com.anathema_roguelike.stats.characterstats.resources.CurrentHealth
import com.anathema_roguelike.stats.characterstats.secondarystats.Health
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.{Visibility, VisibilityLevel}
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.defenses.Attenuation
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.defenses.Concealment
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.defenses.Veil

import com.anathema_roguelike.entities.characters.inventory.SingleSlot._

class PlayerStats(position: Point, width: Int, height: Int, player: Player)
  extends UIElement(position, width, height, 1f, "Character") {

  private var renderPos = 0

  override protected def renderContent(): Unit = {
    renderPos = 0
    renderChar()
    renderPos += 1
    renderResources()
    renderPos += 1
    renderAbilities()
    renderPos += 1
    renderOtherStats()
    renderPos += 1
    renderInventory()
  }

  def renderChar(): Unit = {
    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, player.getLocation.toString)
    renderPos += 1
    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos,
      s"Level ${player.getLevel} ${player.getClasses.map(c => Utils.getName(c)).mkString(",")}")
  }

  def renderResources(): Unit = {
    val currentHP = player.getStatAmount(classOf[CurrentHealth]).toInt
    val maxHP = player.getStatAmount(classOf[Health]).toInt
    val color = Color.factory.blend(Color.RED, Color.GREEN, currentHP.toDouble / maxHP.toDouble)

    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"HP: $currentHP/$maxHP", color)
  }

  def renderAbilities(): Unit = {
    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"STR: ${player.getStatAmount[Strength]}")
    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"AGI: ${player.getStatAmount[Agility]}")
    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"CON: ${player.getStatAmount[Constitution]}")
    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"INT: ${player.getStatAmount[Intelligence]}")
    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"PER: ${player.getStatAmount[Perception]}")
  }

  def renderOtherStats(): Unit = {
    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"Concealment: ${player.getStatAmount[Concealment]}")
    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"Veil: ${player.getStatAmount[Veil]}")
    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"Attenuation: ${player.getStatAmount[Attenuation]}")
    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"Visibility: ${player.getStatAmount[Visibility].toInt}")
    renderPos += 1

    val visibility = player.getStat[Visibility].getVisibilityLevel

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"Visibility Level: ${visibility.name}", visibility.color)
    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"Light Level: ${player.getEnvironment.getLightLevels.get(player.getPosition)}")
  }

  def renderInventory(): Unit = {
    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"Primary Weapon: ${player.getInventory.getSlot[PrimaryWeapon].getEquippedItem}")
    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"Secondary Weapon: ${player.getInventory.getSlot[SecondaryWeapon].getEquippedItem}")
    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"Head: ${player.getInventory.getSlot[Head].getEquippedItem}")
    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"Chest: ${player.getInventory.getSlot[Chest].getEquippedItem}")
    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"Legs: ${player.getInventory.getSlot[Legs].getEquippedItem}")
    renderPos += 1

    renderString(DisplayLayer.UI_FOREGROUND, 0, renderPos, s"Feet: ${player.getInventory.getSlot[Feet].getEquippedItem}")
  }
}