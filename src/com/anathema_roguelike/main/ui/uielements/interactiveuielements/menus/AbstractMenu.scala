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
        items: Iterable[T],
        centered: Boolean = false,
        finishText: Option[String] = None,
        selectListenerOverrides: Map[T, (AbstractMenuItem[T]) => Unit] = Map[T, (AbstractMenuItem[T]) => Unit]()
    ) extends InteractiveUIElement[T](position, width, height, background, cancellable) {

  def onSelect(menuItem: AbstractMenuItem[T]): Unit

  private var typedMenuItems: Vector[AbstractMenuItem[T]] = Vector[AbstractMenuItem[T]]()
  private var menuItems: Vector[AbstractMenuItem[_]] = Vector[AbstractMenuItem[_]]()
  private val onFocusChangedListeners: Vector[() => Unit] = Vector[() => Unit]()
  private var focused: Int = 0

  typedMenuItems = items.zipWithIndex.map {
    case (item, i)  => {
      if (isCentered) {
        new MenuItem[T](
          this,
          item,
          Point(getX + (getWidth / 2) - (Utils.getName(item).length / 2), getY + i * spacing),
          getBackground,
          selectListenerOverrides.getOrElse(item, onSelect)
        )

      } else {
        new MenuItem[T](
          this,
          item,
          Direction.offset(getPosition, Direction.DOWN, i * spacing),
          getBackground,
          selectListenerOverrides.getOrElse(item, onSelect)
        )
      }
    }
  }.toVector

  menuItems ++= typedMenuItems
  menuItems.get(0).focus()

  finishText.foreach(text => {
    menuItems :+= new AbstractMenuItem[String](
      this,
      text,
      Direction.offset(getPosition, Direction.DOWN, typedMenuItems.length + 1 * this.spacing),
      getBackground
    ) {
      override def onSelect(menuItem: AbstractMenuItem[String]): Unit = finish()
    }
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
        listener()
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

  def getItems: Iterable[_ <: T] = typedMenuItems.map(i => i.getItem)

  def getItem(i: Int): T = typedMenuItems(i).getItem

  protected def getMenuItems: Vector[AbstractMenuItem[_]] = menuItems

  def addOnSelectionChangedListener(listener: () => Unit): Unit = {
    onFocusChangedListeners :+ listener
  }

  protected def getOnSelectionChangedListeners: Iterable[() => Unit] = onFocusChangedListeners

  def getFocusedItem: Option[T] = {
    if (getFocused >= typedMenuItems.length) {
      Option.empty
    } else {
      typedMenuItems.get(getFocused).getItem
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
