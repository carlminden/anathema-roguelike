

package com.anathema_roguelike
package main.ui.uielements.interactiveuielements

import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.input.InputHandler
import com.anathema_roguelike.main.ui.uielements.UIElement
import com.anathema_roguelike.main.utilities.position.Point
import com.badlogic.gdx.InputAdapter
import squidpony.squidgrid.gui.gdx.SquidInput
import squidpony.squidgrid.gui.gdx.SquidInput.KeyHandler

abstract class InteractiveUIElement[T](
      position: Point,
      width: Int,
      height: Int,
      background: Float,
      cancellable: Boolean = false,
      title: Option[String] = None
      )
  extends UIElement(position, width, height, background, title) {

  private var result: Option[T] = None
  private var finished = false

  def registerMouseCallbacks(): Unit

  def processKeyEvent(key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean): Unit

  def processScrollEvent(amount: Int): Boolean

  def run: Option[T] = {
    Game.getInstance.getUserInterface.addUIElement(this)
    setResult(None)

    val keyHandler: KeyHandler = (key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean) => {

      key match {
        case SquidInput.ESCAPE if isCancellable => finish()
        case _ => processKeyEvent(key, alt, ctrl, shift)
      }
    }

    val mouse = new InputAdapter() {
      override def scrolled(amount: Int): Boolean = processScrollEvent(amount)
    }

    Game.getInstance.getInput.proccessInput(new InputHandler(keyHandler, mouse), () => isFinished, () => {
        for (element <- getUIElements.filterByType[InteractiveUIElement[_]]) {
          element.registerMouseCallbacks()
        }
      }
    )

    Game.getInstance.getUserInterface.removeUIElement(this)
    getResult
  }

  protected def setResult(result: Option[T]): Unit = {
    this.result = result
  }

  def getResult: Option[T] = result

  def isCancellable: Boolean = cancellable

  def finish(): Unit = {
    finished = true
  }

  def isFinished: Boolean = finished
}

