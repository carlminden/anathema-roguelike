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
package main.display

import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.animations.Animation
import com.anathema_roguelike.main.utilities.position.Point
import squidpony.squidgrid.gui.gdx.SColor

abstract class RenderSurface(var width: Int, var height: Int) {
  def renderChar(layer: Display.DisplayLayer, x: Int, y: Int, string: Char, color: SColor): Unit = {
    if(x >= 0 && x < width && y >= 0 && y < height) {
      Game.getInstance.getDisplay.put(layer, x, y, string, color)
    }
  }

  def createAnimation(layer: Display.DisplayLayer, animation: Animation, offset: Point = Point(0, 0)): Unit = {
    animation.setOffset(offset)
    animation.create(layer)
  }

  def getWidth: Int = width

  def getHeight: Int = height

  def renderOutline(outline: Outline): Unit = outline.render()

  def renderString(foregroundLayer: Display.DisplayLayer, backgroundLayer: Display.DisplayLayer, x: Int, y: Int, string: String, foreground: SColor, background: SColor): Unit = {

    for (i <- 0 until string.length) {
      renderChar(foregroundLayer, x + i, y, string.charAt(i), foreground)
      renderChar(backgroundLayer, x + i, y, '\u2588', background)
    }
  }

  def renderString(layer: Display.DisplayLayer, x: Int, y: Int, string: String, color: SColor): Unit = {

    for (i <- 0 until string.length) {
      renderChar(layer, x + i, y, string.charAt(i), color)
    }
  }

  def renderString(layer: Display.DisplayLayer, x: Int, y: Int, string: String): Unit = {
    renderString(layer, x, y, string, Color.WHITE)
  }

  def renderString(foregroundLayer: Display.DisplayLayer, backgroundLayer: Display.DisplayLayer, x: Int, y: Int, string: String): Unit = {
    renderString(foregroundLayer, backgroundLayer, x, y, string, Color.WHITE, Color.BLACK)
  }

  def renderVisualRepresentation(layer: Display.DisplayLayer, x: Int, y: Int, rep: VisualRepresentation): Unit = {
    renderChar(layer, x, y, rep.char, rep.color)
  }

  def renderStringCentered(foregroundLayer: Display.DisplayLayer, backgroundLayer: Display.DisplayLayer, y: Int, string: String, foreground: SColor, background: SColor): Unit = {
    val offset = width / 2 - string.length / 2
    renderString(foregroundLayer, backgroundLayer, offset, y, string, foreground, background)
  }

  def renderStringCentered(layer: Display.DisplayLayer, y: Int, string: String, color: SColor): Unit = {
    val offset = width / 2 - string.length / 2
    renderString(layer, offset, y, string, color)
  }

  def renderStringCentered(layer: Display.DisplayLayer, y: Int, string: String): Unit = {
    val offset = width / 2 - string.length / 2
    renderString(layer, offset, y, string)
  }

  def renderStringCentered(foregroundLayer: Display.DisplayLayer, backgroundLayer: Display.DisplayLayer, y: Int, string: String): Unit = {
    val offset = width / 2 - string.length / 2
    renderString(foregroundLayer, backgroundLayer, offset, y, string)
  }

  def centerOnLine(foregroundLayer: Display.DisplayLayer, backgroundLayer: Display.DisplayLayer, y: Int, string: String): Unit = {
    val offset = getWidth / 2 - string.length / 2
    renderString(foregroundLayer, backgroundLayer, offset, y, string)
  }

  def centerOnLine(layer: Display.DisplayLayer, y: Int, string: String): Unit = {
    val offset = getWidth / 2 - string.length / 2
    renderString(layer, offset, y, string)
  }

  def alignRight(foregroundLayer: Display.DisplayLayer, backgroundLayer: Display.DisplayLayer, y: Int, string: String): Unit = {
    var offset = getWidth - string.length
    if(getHeight - 1 == y) offset -= 1
    renderString(foregroundLayer, backgroundLayer, offset, y, string)
  }

  def alignRight(layer: Display.DisplayLayer, y: Int, string: String): Unit = {
    var offset = getWidth - string.length
    if(getHeight - 1 == y) offset -= 1
    renderString(layer, offset, y, string)
  }
}