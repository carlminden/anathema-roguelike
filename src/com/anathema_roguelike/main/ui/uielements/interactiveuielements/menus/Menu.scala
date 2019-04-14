package com.anathema_roguelike
package main.ui.uielements.interactiveuielements.menus

import com.anathema_roguelike.main.utilities.position.Point

class Menu[T](
    position: Point,
    width: Int,
    height: Int,
    spacing: Int,
    background: Float,
    cancellable: Boolean,
    items: Array[_ <: T],
    centered: Boolean = false,
    finishText: Option[String] = None,
    onSelectListener: T => Unit
  ) extends AbstractMenu[T](position, width, height, spacing, background, cancellable, items, centered, finishText) {

  override def onSelect(obj: T): Unit = onSelectListener(obj)
}
