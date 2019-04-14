package com.anathema_roguelike
package main.ui.uielements

import com.anathema_roguelike.main.ui.UIConfig
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.InteractiveUIElement
import com.anathema_roguelike.main.utilities.position.Point

class Screen[T](
      title: String,
      cancellable: Boolean,
      position: Point = new Point(0, 0),
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
