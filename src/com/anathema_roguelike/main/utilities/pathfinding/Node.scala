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
package main.utilities.pathfinding

import com.anathema_roguelike.main.utilities.position.{Direction, Point}

class Node(var parent: Node, var position: Point, var direction: Direction, var g: Int, var h: Int) extends Comparable[Node] {
  def getParent: Node = parent

  def getPosition: Point = position

  def getG: Int = g

  def setG(g: Int): Unit = this.g = g

  def getH: Int = h

  def getF: Int = g + h

  def getDirection: Direction = direction

  override def compareTo(o: Node): Int = this.getF - o.getF

  def setParent(parent: Node): Unit = this.parent = parent
}