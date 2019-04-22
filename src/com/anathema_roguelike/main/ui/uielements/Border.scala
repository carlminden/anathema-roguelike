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

import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.main.utilities.position.Point

object Border {
  val SIDE = "│"
  val TOP_BOTTOM = "─"
  val UP_LEFT_CORNER = "┌"
  val UP_RIGHT_CORNER = "┐"
  val DOWN_LEFT_CORNER = "└"
  val DOWN_RIGHT_CORNER = "┘"
  val LEFT_T = "┤"
  val RIGHT_T = "├"
}

class Border(position: Point, width: Int, height: Int, t: String)
  extends UIElement(Direction.offset(position, Direction.UP_LEFT), width + 2, height + 2, 0f) {

  def this(uiElement: UIElement, t: String) = {
    this(uiElement.getPosition, uiElement.getWidth ,uiElement.getHeight, t)
  }

  private val title = new Title(this, t)

  override protected def renderContent(): Unit = {

    for (i <- 0 until getWidth) {
      renderString(DisplayLayer.UI_FOREGROUND, i, 0, Border.TOP_BOTTOM)
      renderString(DisplayLayer.UI_FOREGROUND, i, getHeight - 1, Border.TOP_BOTTOM)
    }

    for (i <- 0 until getHeight - 1) {
      renderString(DisplayLayer.UI_FOREGROUND, 0, i, Border.SIDE)
      renderString(DisplayLayer.UI_FOREGROUND, getWidth - 1, i, Border.SIDE)
    }

    renderString(DisplayLayer.UI_FOREGROUND, 0, 0, Border.UP_LEFT_CORNER)
    renderString(DisplayLayer.UI_FOREGROUND, getWidth - 1, 0, Border.UP_RIGHT_CORNER)
    renderString(DisplayLayer.UI_FOREGROUND, 0, getHeight - 1, Border.DOWN_LEFT_CORNER)
    renderString(DisplayLayer.UI_FOREGROUND, getWidth - 1, getHeight - 1, Border.DOWN_RIGHT_CORNER)

    title.render()
  }

  def setTitle(newTitle: String) = {
    title.title = newTitle
  }
}