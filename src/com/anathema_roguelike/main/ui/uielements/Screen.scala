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

import com.anathema_roguelike.main.ui.UIConfig
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.InteractiveUIElement
import com.anathema_roguelike.main.utilities.position.Point

class Screen[T](
      title: String,
      cancellable: Boolean,
      position: Point = Point(0, 0),
      width: Int = UIConfig.TERM_WIDTH,
      height: Int = UIConfig.TERM_HEIGHT,
      background: Float = 1f)
  extends InteractiveUIElement[T](position, width, height, background, cancellable, title) {

  private var focusedUIElement: Option[InteractiveUIElement[T]] = None

  def setFocusedUIElement(focusedUIElement: InteractiveUIElement[T]): Unit = {
    this.focusedUIElement = focusedUIElement
  }

  override def getUIElements: Iterable[UIElement] = {
    super.getUIElements ++ focusedUIElement
  }

  override def processKeyEvent(key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    for (element <- getUIElements) {

      focusedUIElement match {
        case Some(e) if e == element => e.processKeyEvent(key, alt, ctrl, shift)
        case _ => element match {
            case e: InteractiveUIElement[_] => e.processKeyEvent(key, alt, ctrl, shift)
            case _ =>
          }
      }
    }
  }

  override def processScrollEvent(amount: Int): Boolean = {
    for (element <- getUIElements) {
      element match {
        case e: InteractiveUIElement[_] => e.processScrollEvent(amount)
        case _ =>
      }
    }

    focusedUIElement.exists(f => f.processScrollEvent(amount))
  }

  override def registerMouseCallbacks(): Unit = {
    focusedUIElement.foreach(f => f.registerMouseCallbacks())
  }

  override protected def renderContent(): Unit = {
  }

  override def isFinished: Boolean = focusedUIElement.exists(_.isFinished)

  override def getResult: Option[T] = focusedUIElement.flatMap(_.getResult)
}
