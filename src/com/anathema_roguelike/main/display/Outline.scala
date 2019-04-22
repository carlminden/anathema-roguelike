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
import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.main.utilities.position.Point
import squidpony.squidgrid.gui.gdx.SColor
import java.util

abstract class Outline(color: SColor, private var offset: Point = Point(0, 0)) extends Renderable {

  def getPoints: Iterable[Point]

  def validPoint(point: Point): Boolean

  override def render(): Unit = {
    for (p <- getPoints) {
      if(validPoint(p)) {
        if(!validPoint(Direction.offset(p, Direction.UP))) top(p, color)
        if(!validPoint(Direction.offset(p, Direction.DOWN))) bottom(p, color)
        if(!validPoint(Direction.offset(p, Direction.LEFT))) left(p, color)
        if(!validPoint(Direction.offset(p, Direction.RIGHT))) right(p, color)
      }
    }
  }

  private def top(point: Point, color: SColor) = {
    val x = point.getX + offset.getX
    val y = point.getY + offset.getY
    Game.getInstance.getDisplay.drawLine(x * Display.cellWidth - 1, y * Display.cellHeight, (x + 1) * Display.cellWidth, y * Display.cellHeight, color)
  }

  private def bottom(point: Point, color: SColor) = {
    val x = point.getX + offset.getX
    val y = point.getY + offset.getY
    Game.getInstance.getDisplay.drawLine(x * Display.cellWidth - 1, (y + 1) * Display.cellHeight - 1, (x + 1) * Display.cellWidth, (y + 1) * Display.cellHeight - 1, color)
  }

  private def left(point: Point, color: SColor) = {
    val x = point.getX + offset.getX
    val y = point.getY + offset.getY
    Game.getInstance.getDisplay.drawLine(x * Display.cellWidth, y * Display.cellHeight, x * Display.cellWidth, (y + 1) * Display.cellHeight, color)
  }

  private def right(point: Point, color: SColor) = {
    val x = point.getX + offset.getX
    val y = point.getY + offset.getY
    Game.getInstance.getDisplay.drawLine((x + 1) * Display.cellWidth, y * Display.cellHeight, (x + 1) * Display.cellWidth, (y + 1) * Display.cellHeight, color)
  }

  def setOffset(point: Point): Unit = this.offset = point
}