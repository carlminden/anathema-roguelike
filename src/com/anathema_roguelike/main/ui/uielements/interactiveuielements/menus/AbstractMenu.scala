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
package main.ui.uielements.interactiveuielements.menus

import com.anathema_roguelike.main.ui.uielements.interactiveuielements.InteractiveUIElement
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.{Direction, Point}
import squidpony.squidgrid.gui.gdx.SquidInput

import scala.collection.mutable.ArrayBuffer

//The way I handled the finishItem is horrible
abstract class AbstractMenu[T](
      position: Point,
      width: Int,
      height: Int,
      spacing: Int,
      background: Float,
      cancellable: Boolean,
      items: Array[_ <: T],
      centered: Boolean = false,
      finishText: Option[String] = None
    ) extends InteractiveUIElement[T](position, width, height, background, cancellable) {

  def onSelect(obj: T): Unit

  private val typedMenuItems: ArrayBuffer[AbstractMenuItem[T]] = ArrayBuffer[AbstractMenuItem[T]]()
  private val menuItems: ArrayBuffer[AbstractMenuItem[_]] = ArrayBuffer[AbstractMenuItem[_]]()
  private val onFocusChangedListeners: ArrayBuffer[OnFocusChangedListener] = ArrayBuffer[OnFocusChangedListener]()
  private var focused: Int = 0

  items.zipWithIndex.foreach {
    case (item, i)  => {
      if (isCentered) {
        typedMenuItems :+ new MenuItem[T](
          this,
          item,
          new Point(getX + (getWidth / 2) - (Utils.getName(item).length / 2), getY + i * spacing),
          getBackground,
          onSelect
        )

      } else {
        typedMenuItems :+ new MenuItem[T](
          this,
          item,
          Direction.offset(getPosition, Direction.DOWN, i * spacing),
          getBackground,
          onSelect
        )
      }
    }
  }

  menuItems.addAll(typedMenuItems)
  menuItems.get(0).focus()

  finishText.foreach(text => {
    this.menuItems :+ new MenuItem[String](
      this,
      text,
      Direction.offset(getPosition, Direction.DOWN, this.items.size + 1 * this.spacing),
      getBackground,
      (_: String) => finish()
    )
  })

  private def initializeMenuItems(): Unit = {

  }

  override def registerMouseCallbacks(): Unit = {
    for (item <- menuItems) {
      item.registerMouseCallbacks()
    }
  }

  override def renderContent(): Unit = {
    for (item <- menuItems) {
      item.renderContent()
    }
  }

  protected def getFocused: Int = focused

  def setFocused(menuItem: AbstractMenuItem[_]): Unit = {
    setFocus(menuItems.indexOf(menuItem))
  }

  protected def setFocus(index: Int): Unit = {
    menuItems.get(focused).unfocus()
    if (focused != index) {
      focused = index
      if (focused < 0) {
        focused = getSize - 1
      }

      if (focused >= getSize) {
        focused = 0
      }

      for (listener <- onFocusChangedListeners) {
        listener.onChanged()
      }
    }

    menuItems.get(focused).focus()
  }

  protected def getSize: Int = menuItems.size

  private def menuNext(): Unit = {
    setFocus(focused + 1)
  }

  private def menuPrev(): Unit = {
    setFocus(focused - 1)
  }

  private def select(): Unit = {
    menuItems.get(focused).select()
  }

  def getItems: Iterable[_ <: T] = items

  def getItem(i: Int): T = items(i)

  protected def getMenuItems: ArrayBuffer[AbstractMenuItem[_]] = menuItems

  def addOnSelectionChangedListener(listener: OnFocusChangedListener): Unit = {
    onFocusChangedListeners :+ listener
  }

  protected def getOnSelectionChangedListeners: Iterable[OnFocusChangedListener] = onFocusChangedListeners

  def getFocusedItem: Option[T] = {
    if (getFocused >= items.length) {
      Option.empty
    } else {
      items.get(getFocused)
    }
  }

  private def getFocusedMenuItem: AbstractMenuItem[_] = menuItems.get(getFocused)

  override def processKeyEvent(key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    key match {
      case SquidInput.ENTER | ' ' =>
        select()
      case SquidInput.DOWN_ARROW | 'j'=>
        menuNext()
      case SquidInput.UP_ARROW | 'k'=>
        menuPrev()
      case _ =>
        getFocusedMenuItem.processKeyEvent(key, alt, ctrl, shift)
    }
  }

  override def processScrollEvent(amount: Int): Boolean = getFocusedMenuItem.processScrollEvent(amount)

  private def isCentered: Boolean = centered

  protected def getSpacing: Int = spacing
}
