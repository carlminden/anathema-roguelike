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
package environment.generation

import com.anathema_roguelike.environment.EnvironmentFactory
import com.anathema_roguelike.environment.generation.rooms.Room
import com.anathema_roguelike.main.utilities.position.Point

import scala.collection.mutable

class TunnelSystem(private var startingRoom: Room) extends DungeonFeature(Point(-1, -1)) {
  private val tunnels = mutable.Map[Point, Tunnel]()
  private var endingRoom: Room = _

  def add(tunnel: Tunnel): Unit = {
    tunnels(tunnel.getPosition) = tunnel
    getPoints ++= tunnel.getPoints
  }

  def getStartingRoom: Room = startingRoom

  def setStartingRoom(startingRoom: Room): Unit = this.startingRoom = startingRoom

  def getEndingRoom: Room = endingRoom

  def setEndingRoom(room: Room): Unit = endingRoom = room

  def getTunnel(p: Point): Tunnel = tunnels(p)

  def getTunnels: mutable.Map[Point, Tunnel] = tunnels

  override def validate(factory: DungeonGenerator): Boolean = {
    tunnels.values.forall(t => t.validate(factory))
  }

  override def place(factory: EnvironmentFactory): Unit = {
    for (tunnel <- tunnels.values) {
      tunnel.place(factory)
    }
  }
}