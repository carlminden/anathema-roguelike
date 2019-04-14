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
package environment

import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer
import com.anathema_roguelike.main.display.VisualRepresentation
import com.anathema_roguelike.stats.Stat.LocationStat
import com.anathema_roguelike.stats.effects.{Effect, HasEffect}
abstract class LocationProperty(
    location: Location,
    visualRepresentation: VisualRepresentation,
    fogOfWarRepresentation: VisualRepresentation,
    foreground: Boolean,
    passable: Boolean,
    opacity: Double,
    damping: Double) extends HasEffect[Effect[Location, LocationStat]] with HasLocation {

  private var brightness = 0.0

  private val layer = if(foreground) DungeonLayer.FOREGROUND else DungeonLayer.FOG_OF_WAR_FOREGROUND

  private val fogOfWarLayer = if(foreground) DungeonLayer.FOG_OF_WAR_FOREGROUND else DungeonLayer.FOG_OF_WAR_BACKGROUND

  override def getLocation: Location = location

  def isPassable: Boolean = passable

  def getOpacity: Double = opacity

  def getDamping: Double = damping

  def getBrightness: Double = brightness

  protected def setBrightness(brightness: Double): Unit = {
    this.brightness = brightness
  }

  protected def renderToFogOfWar(): Unit = {
    Game.getInstance.getMap.renderVisualRepresentation(fogOfWarLayer, location.getX, getY, getFogOfWarRepresentation)
  }

  def render(): Unit = {
    Game.getInstance.getMap.renderVisualRepresentation(layer, getX, getY, getRepresentation)
    renderToFogOfWar()
  }

  def getRepresentation: VisualRepresentation = visualRepresentation

  def getFogOfWarRepresentation: VisualRepresentation = if (fogOfWarRepresentation != null) fogOfWarRepresentation
  else getRepresentation
}
