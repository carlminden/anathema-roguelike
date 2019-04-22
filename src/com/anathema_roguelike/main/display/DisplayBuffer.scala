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

import com.anathema_roguelike.main.Game
import squidpony.squidgrid.gui.gdx.SColor

class DisplayBuffer(width: Int, height: Int, display: Boolean = true) extends RenderSurface(width, height) {

  private val buffer = Array.ofDim[DisplayCell](width, height)

  for (i <- 0 until width; j <- 0 until height) {
    buffer(i)(j) = new DisplayCell
    buffer(i)(j).setDisplay(display)
  }

  def this(oldBuffer: DisplayBuffer, transform: DisplayCellTransformation) {
    this(oldBuffer.getWidth, oldBuffer.getHeight)

    for (i <- 0 until width; j <- 0 until height) {
      val old = oldBuffer.get(i, j)

      buffer(i)(j) = transform.compute(this, i, j, old.getChar, old.getColor, old.getDisplay)
    }
  }

  def get(x: Int, y: Int): DisplayCell = {
    buffer(x)(y)
  }

  def set(x: Int, y: Int, value: DisplayCell): Unit = {
    buffer(x)(y) = new DisplayCell(value)
  }

  def clear(): Unit = {

    for (i <- 0 until width; j <- 0 until height) {
      buffer(i)(j).setDisplay(false)
    }
  }

  def render(layer: Display.DisplayLayer, x: Int, y: Int, width: Int, height: Int): Unit = {
    render(layer, x, y, width, height, DisplayCellTransformation.noTransformation)
  }

  def render(layer: Display.DisplayLayer, x: Int, y: Int, width: Int, height: Int, transformation: DisplayCellTransformation): Unit = {


    for (i <- 0 until width; j <- 0 until height) {
      var cell = get(i, j)

      if(cell != null && cell.getDisplay) {
        cell = transformation.compute(this, i, j, cell.getChar, cell.getColor, cell.getDisplay)

        if(cell.getDisplay) {
          Game.getInstance.getDisplay.renderChar(layer, i + x, j + y, cell.getChar, cell.getColor)
        }
      }
    }
  }

  def renderDebug(layer: Display.DisplayLayer, x: Int, y: Int, width: Int, height: Int): Unit = {
    render(layer, x, y, width, height, new DisplayCellTransformation() {
      override def compute(buffer: DisplayBuffer, x: Int, y: Int, string: Char, color: SColor, display: Boolean) = {
        new DisplayCell('X', Color.ERROR, display)
      }
    })
  }

  def applyMask(mask: BufferMask): Unit = {

    for (i <- 0 until width; j <- 0 until height) {
      buffer(i)(j).setDisplay(mask.get(i, j) && buffer(i)(j).getDisplay)
    }
  }

  def transform(transformation: DisplayCellTransformation): Unit = {

    for (i <- 0 until width; j <- 0 until height) {
      var cell = get(i, j)
      cell = transformation.compute(this, i, j, cell.getChar, cell.getColor, cell.getDisplay)

      set(i, j, cell)
    }
  }

  def compose(overlay: DisplayBuffer, transformation: DisplayCellTransformation): Unit = {

    for (i <- 0 until width; j <- 0 until height) {
      var cell = overlay.get(i, j)
      cell = transformation.compute(this, i, j, cell.getChar, cell.getColor, cell.getDisplay)

      if(cell.getDisplay) {
        set(i, j, cell)
      }
    }
  }

  def compose(overlay: DisplayBuffer): Unit = compose(overlay, DisplayCellTransformation.noTransformation)

  def renderChar(x: Int, y: Int, string: Char, color: SColor): Unit = {
    get(x, y).setChar(string)
    get(x, y).setColor(color)
  }
}