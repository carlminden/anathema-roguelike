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
package main.ui.messages

import com.anathema_roguelike.main.ui.uielements.UIElement
import com.anathema_roguelike.main.utilities.position.Point

class MessageBox(position: Point, width: Int, height: Int, title: String)
  extends UIElement(position, width, height, 1f, title) {

  private val messages = new MessageBuffer(getWidth, getHeight)
  private var pendingNewLine = false

  override protected def renderContent(): Unit = {
    var pos = Math.max(0, getHeight - messages.size)
    for (message <- messages.getVisibleMessages) {
      message.render(getX, getY + pos)
      pos += 1
    }
  }

  def addMessage(message: Message): Unit = {
    if(pendingNewLine) {
      pendingNewLine = false
      addMessage(new Message("", turn = message.getTurn))
    }
    messages.addMessage(message)
  }

  def newLine(): Unit = {
    if(pendingNewLine) {
      pendingNewLine = false
      addMessage(new Message(""))
    }
    pendingNewLine = true
  }
}