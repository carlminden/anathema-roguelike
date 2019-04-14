

package com.anathema_roguelike
package main.ui.uielements.interactiveuielements.menus

import com.anathema_roguelike.main.utilities.position.Point

class MenuItem[T](menu: AbstractMenu[_], item: T, position: Point, background: Float, onSelectListener: T => Unit)
  extends AbstractMenuItem[T](menu, item, position, background) {

  override def onSelect(obj: T): Unit = onSelectListener(obj)
}
