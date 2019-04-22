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
package main.display

import squidpony.squidgrid.gui.gdx.SColor

case class DisplayCell(private var character: Char = '\u2588', private var color: SColor = Color.BLACK, private var display: Boolean = true) {

  def this(dc: DisplayCell) {
    this(dc.getChar, dc.getColor, dc.getDisplay)
  }

  def getChar: Char = character

  def setChar(character: Char): Unit = {
    this.character = character
    display = true
  }

  def getColor: SColor = color

  def setColor(color: SColor): Unit = {
    this.color = color
    display = true
  }

  def getDisplay: Boolean = display

  def setDisplay(display: Boolean): Unit = this.display = display

  def set(character: Char, color: SColor): Unit = {
    this.character = character
    this.color = color
    this.display = true
  }
}