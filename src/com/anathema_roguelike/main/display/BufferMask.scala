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

import java.util

import com.anathema_roguelike.main.utilities.position.HasPosition


class BufferMask(width: Int, height: Int, defaultValue: Boolean = false) {
  private val mask = new util.BitSet(width * height)

  if(defaultValue) {
    mask.set(0, width * height)
  }

  def set(x: Int, y: Int, value: Boolean): Unit = {
    mask.set(x * width + y, value)
  }

  def get(x: Int, y: Int): Boolean = {
    if(x < 0 || y < 0 || x >= width || y >= height) {
      false
    } else {
      mask.get(x * width + y)
    }
  }

  def get(point: HasPosition): Boolean = get(point.getX, point.getY)

  def getWidth: Int = width

  def getHeight: Int = height

  def getBitSet: util.BitSet = mask

  def and(bm: BufferMask): Unit = mask.and(bm.getBitSet)

  def nand(bm: BufferMask): Unit = mask.andNot(bm.getBitSet)

  def or(bm: BufferMask): Unit = mask.or(bm.getBitSet)
}