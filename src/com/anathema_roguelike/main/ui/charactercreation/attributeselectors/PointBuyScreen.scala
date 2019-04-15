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
package main.ui.charactercreation.attributeselectors

import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.ui.charactercreation.attributeselectors.PointBuyMenu
import com.anathema_roguelike.main.ui.messages.Message
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.{MenuScreen, TextBox}
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.MenuValues
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.Point
import com.anathema_roguelike.stats.characterstats.attributes.Attribute


object PointBuyScreen {
  private final val attributeList: Array[Class[_ <: Attribute]] = Utils.getSubclasses[Attribute]().toArray

  private final val instructionsMessage: Message = new Message("You have ")
  instructionsMessage.appendMessage("15", Color.GREEN)
  instructionsMessage.appendMessage(" remaining pointSet to allocate to your Ability scores")

}

class PointBuyScreen (
    title: String,
    var player: Player,
    attributes: Array[Class[_ <: Attribute]] = PointBuyScreen.attributeList,
    instructionsMessage: Message = PointBuyScreen.instructionsMessage
  ) extends MenuScreen[Class[_ <: Attribute], PointBuyMenu](title, attributes, instructionsMessage = instructionsMessage, cancellable = false) {


  private def updateCurrentValues(): Unit = {
    val instructionsMessage: Message = new Message("You have ")
    instructionsMessage.appendMessage(Integer.toString(getMenu.get.getPoints), Color.GREEN)
    instructionsMessage.appendMessage(" remaining pointSet to allocate to your Ability scores")

    setInstructionsMessage(instructionsMessage)
  }

  override def processKeyEvent(key: Char, alt: Boolean, ctrl: Boolean, shift: Boolean): Unit = {
    super.processKeyEvent(key, alt, ctrl, shift)
    updateCurrentValues()
  }

  override protected def createMenu(position: Point, width: Int, height: Int, choices: Array[_ <: Class[_ <: Attribute]], cancellable: Boolean, background: Float): PointBuyMenu = {
    val menu = new PointBuyMenu(position, width, height, cancellable, 1, player)

    val currentValues: TextBox = new MenuValues(menu.get, 18, 0, PointBuyScreen.attributeList.size, 1.0f,
      (attribute: Class[_ <: Attribute]) => {
        val current: Int = menu.get.getPlayer.getStatAmount(attribute).asInstanceOf[Int]
        new Message(Integer.toString(current), Color.GREEN)
    })

    addUIElement(currentValues)

    menu
  }
}
