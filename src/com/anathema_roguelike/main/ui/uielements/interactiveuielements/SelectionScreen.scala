package com.anathema_roguelike
package main.ui.uielements.interactiveuielements

import com.anathema_roguelike.main.ui.UIConfig
import com.anathema_roguelike.main.ui.messages.Message
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.SelectionMenu
import com.anathema_roguelike.main.utilities.position.Point

class SelectionScreen[T](
  title: String,
  choices: Array[T],
  position: Point = new Point(0, 0),
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
