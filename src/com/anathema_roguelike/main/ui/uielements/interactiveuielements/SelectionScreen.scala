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
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.SelectionMenu
import com.anathema_roguelike.main.utilities.position.Point

class SelectionScreen[T](
  title: String,
  choices: Array[T],
  position: Point = Point(0, 0),
  width: Int = UIConfig.TERM_WIDTH,
  height: Int = UIConfig.TERM_HEIGHT,
  background: Float = 1f,
  contentBackground: Float = 1f,
  cancellable: Boolean = true,
  instructionsMessage: Option[Message] = None)
  extends MenuScreen[T, SelectionMenu[T]](
    title,
    choices,
    position,
    width,
    height,
    background,
    contentBackground,
    cancellable,
    instructionsMessage) {


  override def run: Option[T] = {

    getChoices.size match {
      case 0 => None
      case 1 => getChoices.headOption
      case _ => super.run
    }
  }

  protected def createMenu(position: Point, width: Int, height: Int, choices: Array[_ <: T], cancellable: Boolean, background: Float): SelectionMenu[T] = {
    new SelectionMenu[T](position, width, height, false, 1, choices, cancellable, background)
  }
}
