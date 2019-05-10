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

import com.anathema_roguelike.main.ui.messages.Message
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.TextBox
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.Point

class MenuDescription[T](var menu: AbstractMenu[T], val itemType: Option[Any])
  extends TextBox(
    Point(menu.getX + menu.getWidth, menu.getY - 1),
    menu.getWidth * 2,
    menu.getHeight,
    menu.getBackground,
    None,
    itemType.map(i => Utils.getName(i)) + " Description"
    ) {

  menu.getFocusedItem.map {
    case obj: Class[_] => new Message(Utils.getDescription(obj))
    case item => new Message(Utils.getDescription(item.getClass))
  }.foreach(msg => setMessage(msg))

  menu.addOnSelectionChangedListener(() => {

    menu.getFocusedItem.map {
      case Some(item: Class[_]) => item
      case Some(item) => item.getClass
    }.foreach(cls => {
      setMessage(new Message(Utils.getDescription(cls)))
      setPosition(0)
    })
  })
}
