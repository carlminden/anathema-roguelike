

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

