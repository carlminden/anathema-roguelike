

package com.anathema_roguelike
package main.ui.uielements.interactiveuielements.menus

import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.{InteractiveUIElement, MouseCallback}
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.Point

abstract class AbstractMenuItem[T](menu: AbstractMenu[_], item: T, position: Point, background: Float)
  extends InteractiveUIElement[T](position, Utils.getName(item).length, 1, background) {

  def onSelect(obj: T): Unit

  private var focused = false

  def getText: String = Utils.getName(item)

  def select(): Unit = {
    onSelect(item)
  }

  def focus(): Unit = {
    focused = true
  }

  def unfocus(): Unit = {
    focused = false
  }

  def getItem: T = item

  override def registerMouseCallbacks(): Unit = {
    val callback = new MouseCallback() {
      override def onMouseover(): Unit = {
        menu.setFocused(AbstractMenuItem.this)
      }

      override def onClick(): Unit = {
        menu.setFocused(AbstractMenuItem.this)
        select()
      }
    }

    (0 until getWidth).foreach(i => {
      Game.getInstance.getInput.registerMouseCallback(callback, new Point(getX + i, getY))
    })
  }

  override def processKeyEvent(key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    // TODO Auto-generated method stub
  }

  override def processScrollEvent(amount: Int) = false

  override def renderContent(): Unit = {

    if (focused) {
      (Color.WHITE, Color.BLACK)
    } else {
      (Color.BLACK, Color.WHITE)
    }.foreach {
      case (bg, fg) => renderString(DisplayLayer.UI_FOREGROUND, DisplayLayer.UI_BACKGROUND, 0, 0, getText, fg, bg)
    }

  }
}