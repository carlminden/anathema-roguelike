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

  override def onSelect(menuItem: AbstractMenuItem[T]): Unit = {
    setResult(menuItem.getItem)
    finish()
  }
}
