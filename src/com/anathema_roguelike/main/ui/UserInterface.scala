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
package main.ui

import com.anathema_roguelike.main.State
import com.anathema_roguelike.main.display.Color
import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.display.DungeonMap
import com.anathema_roguelike.main.ui.messages.Message
import com.anathema_roguelike.main.ui.messages.MessageBox
import com.anathema_roguelike.main.ui.uielements.Placeholder
import com.anathema_roguelike.main.ui.uielements.PlayerStats
import com.anathema_roguelike.main.ui.uielements.UIElement
import com.anathema_roguelike.main.utilities.position.Point

class UserInterface() extends UIElement(Point(0, 0), UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT, 0f) {

  private val mapPlaceholder = new Placeholder(Point(0, UIConfig.MAP_START_Y), UIConfig.DUNGEON_MAP_WIDTH + 1, UIConfig.DUNGEON_MAP_HEIGHT + 3, "Dungeon Map")
  private val messageBox = new MessageBox(Point(mapPlaceholder.getOuterWidth, 0), 50, 20, "Messages")
  private var map: Option[DungeonMap] = None
  private var statsUI: Option[PlayerStats] = None

  def init(state: State): Unit = {
    map = new DungeonMap(mapPlaceholder.getX, mapPlaceholder.getY, state)
    statsUI = new PlayerStats(Point(mapPlaceholder.getOuterWidth, messageBox.getOuterHeight), messageBox.getOuterWidth, mapPlaceholder.getOuterHeight - messageBox.getOuterHeight, state.getPlayer)

    addUIElement(mapPlaceholder)
    addUIElement(messageBox)
    addUIElement(statsUI.get)
  }

  def addMessage(message: Message): Unit = messageBox.addMessage(message)

  def addMessage(message: String): Unit = messageBox.addMessage(new Message(message))

  def newLine(): Unit = messageBox.newLine()

  def getMessageBox: MessageBox = messageBox

  def getMap: DungeonMap = map.get

  def getMapPlaceholder: Placeholder = mapPlaceholder

  def getStatsUI: PlayerStats = statsUI.get

  override protected def renderContent(): Unit = {

    for (i <- 0 until getWidth; j <- 0 until getHeight) {
      renderChar(DisplayLayer.UI_BACKGROUND, i, j, ' ', Color.BLACK)
      renderChar(DisplayLayer.UI_FOREGROUND, i, j, ' ', Color.BLACK)
    }

    map.foreach(_.render())
  }
}