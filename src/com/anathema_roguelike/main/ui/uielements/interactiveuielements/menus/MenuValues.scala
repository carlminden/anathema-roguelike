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

import com.anathema_roguelike.main.ui.messages.Message
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.TextBox
import com.anathema_roguelike.main.utilities.position.Direction

class MenuValues[T](menu: AbstractMenu[T], xOffset: Int, start: Int, length: Int, background: Float, calculator: T => Message)
  extends TextBox(Direction.offset(menu.getPosition, Direction.RIGHT, xOffset), 5, menu.getHeight, background) {

  updateValues()

  override def renderContent(): Unit = {
    updateValues()
    super.renderContent()
  }

  private def updateValues(): Unit = {

    val values = (0 to length).foldLeft(List[Message]()) {
      case (list, i) => {
        list :+ calculator.apply(menu.getItem(i + start))
      }
    }

    setMessages(values)
  }
}

