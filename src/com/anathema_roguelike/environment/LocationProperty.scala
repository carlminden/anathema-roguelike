

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
