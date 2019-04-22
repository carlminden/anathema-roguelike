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
package main.ui.uielements.interactiveuielements.menus

import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.{InteractiveUIElement, MouseCallback}
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.Point

abstract class AbstractMenuItem[T](menu: AbstractMenu[_], item: T, position: Point, background: Float)
  extends InteractiveUIElement[T](position, Utils.getName(item).length, 1, background) {

  def onSelect(menuItem: AbstractMenuItem[T]): Unit

  private var focused = false

  def getText: String = Utils.getName(item)

  def select(): Unit = {
    onSelect(this)
  }

  def focus(): Unit = {
    focused = true
  }

  def unfocus(): Unit = {
    focused = false
  }

  def getItem: T = item

  def getMenu: AbstractMenu[_] = menu

  override def registerMouseCallbacks(): Unit = {
    val callback = new MouseCallback() {
      override def onMouseover(): Unit = {
        menu.setFocused(AbstractMenuItem.this)
      }

      override def onClick(): Unit = {
        menu.setFocused(AbstractMenuItem.this)
        select()
      }
    }

    (0 until getWidth).foreach(i => {
      Game.getInstance.getInput.registerMouseCallback(callback, Point(getX + i, getY))
    })
  }

  override def processKeyEvent(key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    // TODO Auto-generated method stub
  }

  override def processScrollEvent(amount: Int) = false

  override def renderContent(): Unit = {

    val colors = if (focused) {
      (Color.BLACK, Color.WHITE)
    } else {
      (Color.WHITE, Color.BLACK)
    }

    renderString(DisplayLayer.UI_FOREGROUND, DisplayLayer.UI_BACKGROUND, 0, 0, getText, colors._1, colors._2)
  }
}