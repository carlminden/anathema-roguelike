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
package main.ui.uielements.interactiveuielements

import com.anathema_roguelike.main.ui.UIConfig
import com.anathema_roguelike.main.ui.messages.Message
import com.anathema_roguelike.main.ui.uielements.Screen
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.{AbstractMenu, MenuDescription}
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.{Direction, Point}

abstract class MenuScreen[T, M <: AbstractMenu[T]](
    title: String,
    choices: Array[_ <: T],
    position: Point = new Point(0, 0),
    width: Int = UIConfig.TERM_WIDTH,
    height: Int = UIConfig.TERM_HEIGHT,
    background: Float = 1f,
    contentBackground: Float = 1f,
    cancellable: Boolean = true,
    var instructionsMessage: Option[Message] = None,
  ) extends Screen[T](title, cancellable, position, width, height, background) {

  private val x: Int = getX + (getWidth * .05).toInt
  private val y: Int = getY + (getHeight * .2).toInt

  private var instructions: Option[TextBox] = None
  private var menu: Option[M] = None

  override def run: Option[T] = {

    val menu = createMenu(Point(x, y), (getWidth * .3).toInt, getHeight / 2, choices, cancellable, contentBackground)

    val description = new MenuDescription[T](menu, choices.headOption.map(c => Utils.getSuperclass(c)))

    instructionsMessage.foreach(msg => {
      val i = new TextBox(Direction.offset(getPosition, Direction.DOWN, 2), menu.getWidth + description.getWidth, 1, contentBackground, msg)

      instructions = i
      addUIElement(i)
    })

    addUIElement(description)
    setFocusedUIElement(menu)
    super.run
  }

  protected def getMenu: Option[M] = menu

  protected def setInstructionsMessage(message: Message): Unit = {
    instructions.get.setMessage(message)
  }

  def getChoices: Iterable[_ <: T] = choices

  protected def createMenu(position: Point, width: Int, height: Int, choices: Array[_ <: T], cancellable: Boolean, background: Float): M
}
