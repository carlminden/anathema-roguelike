package com.anathema_roguelike
package main.ui.uielements.interactiveuielements.menus

import com.anathema_roguelike.main.utilities.position.Point

class SelectionMenu[T](
    position: Point,
    width: Int,
    height: Int,
    centered: Boolean,
    spacing: Int,
    choices: Array[_ <: T],
    cancellable: Boolean,
    background: Float
  ) extends AbstractMenu[T](
    position,
    width,
    height,
    spacing,
    background,
    cancellable,
    choices,
    centered
  ) {

  override def onSelect(obj: T): Unit = {
    setResult(obj)
    finish()
  }
}
