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
package main.ui.uielements.interactiveuielements

import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.ui.uielements.Screen
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.{AbstractMenu, AbstractMenuItem, Menu, MenuItem}
import com.anathema_roguelike.main.utilities.position.Point
import java.awt.event.KeyEvent

class SplashScreen() extends Screen[String]("", false) {

  private val menu = new Menu[String](
    Point(1, 11),
    getWidth - 1,
    30,
    2,
    1f,
    false,
    Array("New Game", "Exit"),
    (_: String) => _,
    true,
    selectListenerOverrides = Map(
      "New Game" -> (item => item.getMenu.finish()),
      "Exit" -> (_ => Game.getInstance.quit())
    )
  )

  setFocusedUIElement(menu)

  override protected def renderContent(): Unit = {
    super.renderContent()
    renderStringCentered(DisplayLayer.UI_FOREGROUND, 0, "Anathema")
    renderStringCentered(DisplayLayer.UI_FOREGROUND, 1, "A stealth roguelike")
    renderStringCentered(DisplayLayer.UI_FOREGROUND, 3, "Copyright (C) 2017 Carl Minden")
    renderStringCentered(DisplayLayer.UI_FOREGROUND, 6, "This program comes with ABSOLUTELY NO WARRANTY, This is free software, and you are welcome to redistribute it under certain conditions.")
    renderStringCentered(DisplayLayer.UI_FOREGROUND, 8, "------------------------------")
  }

  override def processKeyEvent(key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    key match {
      case KeyEvent.VK_ESCAPE | 'Q'=>
        Game.getInstance.quit()
      case _ =>
    }
    super.processKeyEvent(key, alt, ctrl, shift)
  }

  private def showLegalNotices() = {
    //TODO do this or some other thing to make shit legal
  }
}