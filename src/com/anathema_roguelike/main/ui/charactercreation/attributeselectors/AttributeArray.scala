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
import com.anathema_roguelike.main.ui.messages.Message
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.stats.characterstats.attributes.Attribute
import java.util

import scala.collection.mutable.ArrayBuffer

object AttributeArray {
  var array: Array[Int] = Array[Int](16, 14, 14, 12, 10)
}

class AttributeArray extends AttributeSelector {
  override def selectScores(player: Player): Unit = {

    var attributes = Utils.getSubclasses[Attribute]().toVector

    for (value <- AttributeArray.array) {

      val instructions = new Message("Choose The Attribute to assign ")
      instructions.appendMessage(Integer.toString(value), Color.GREEN)
      instructions.appendMessage(" pointSet")

      val selectorScreen = new SelectionScreen[Class[_ <: Attribute]]("Select your Ability Scores", attributes.toArray, instructionsMessage = instructions, cancellable = false)

      val attribute = selectorScreen.run.get

      player.setAttributeScore(value)(attribute)

      attributes = attributes.filterNot(_ == attribute)
    }
  }
}