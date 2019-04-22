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

import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.Display.DisplayLayer
import squidpony.squidgrid.gui.gdx.SColor

import scala.collection.mutable.ListBuffer

object Message {
  def split(longMessage: Message, width: Int): ListBuffer[Message] = {
    val ret = ListBuffer[Message]()
    var message = new Message(turn = longMessage.getTurn)
    var position = 0

    for (part <- longMessage.getMessage) {
      var str = part.toString
      while(str.length > 0) {

        val lastNewLine = str.indexOf("\n")
        if(lastNewLine == -1) {
          var length = Math.min(str.length, width + 1)
          if(position + length >= width) {
            length = str.lastIndexOf(" ", width - position)
            if(length == -1) if(str.length > width) {
              message.appendMessage(str.substring(0, width - position), part.color)
              str = str.substring(width - position)
            }
            else {
              message.appendMessage(str.substring(0, length), part.color)
              str = str.substring(length + 1)
            }
            ret += message
            message = new Message(turn = longMessage.getTurn)
            position = 0
          }
          else {
            position += length
            message.appendMessage(str.substring(0, length), part.color)
            str = str.substring(length)
          }
        }
        else {
          var length = lastNewLine
          if(position + length > width) {
            length = str.lastIndexOf(" ", width)
            if(length == -1) {
              message.appendMessage(str.substring(0, width - position), part.color)
              str = str.substring(width - position)
            }
            else {
              message.appendMessage(str.substring(0, length), part.color)
              str = str.substring(length + 1)
            }
            ret += message
            message = new Message(turn = longMessage.getTurn)
          }
          else {
            position = 0
            message.appendMessage(str.substring(0, length), part.color)
            str = str.substring(length + 1)
            ret += message
            ret += new Message("", turn = longMessage.getTurn)
            message = new Message(turn = longMessage.getTurn)
          }
        }
      }
    }
    ret += message
    message = new Message(turn = longMessage.getTurn)
    ret
  }
}

class Message(initial: Option[String] = None, color: SColor = Color.WHITE, turn: Long = Game.getInstance.getTurn) {
  private val message = ListBuffer[MessagePart]()
  private var repeating = 1

  initial.foreach(m => {
    appendMessagePart(MessagePart(m, color))
  })


  def appendMessagePart(part: MessagePart): Unit = message += part

  def appendMessage(m: String, color: SColor): Unit = message += new MessagePart(m, color)

  def appendMessage(m: String): Unit = appendMessage(m, Color.WHITE)

  def getMessage: Iterable[MessagePart] = message

  def getTurn: Long = turn

  def repeat(): Unit = repeating += 1

  override def toString: String = {
    val ret = new StringBuilder

    for (part <- message) {
      ret.append(part)
    }
    ret.toString
  }

  def render(x: Int, y: Int): Unit = {
    var position = 0
    var color = Color.WHITE

    for (part <- message) {
      color = part.color
      if(Game.getInstance.getTurn - getTurn > 1) {
        color = Color.factory.dimmest(color)
      }

      Game.getInstance.getDisplay.renderString(DisplayLayer.UI_FOREGROUND, x + position, y, part.toString, color)
      position += part.toString.length
    }

    if(repeating > 1 && !(toString == "")) {
      Game.getInstance.getDisplay.renderString(DisplayLayer.UI_FOREGROUND, x + position, y, " x" + repeating, color)
    }
  }
}