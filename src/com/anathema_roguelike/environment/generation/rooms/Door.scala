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
package environment.generation.rooms

import com.anathema_roguelike.environment.EnvironmentFactory
import com.anathema_roguelike.environment.features.Doorway
import com.anathema_roguelike.environment.generation.DungeonFeature
import com.anathema_roguelike.environment.generation.DungeonGenerator
import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.main.utilities.position.Direction.Direction4
import com.anathema_roguelike.main.utilities.position.Point

class Door(room: Room, point: Point, direction: Direction4) extends DungeonFeature(point) {

  room.addDoor(this)

  override def validate(generator: DungeonGenerator): Boolean = {
    room.intersects(Direction.offset(getPosition, direction, -1))
  }

  override def place(factory: EnvironmentFactory): Unit = {
    factory.addFeatures(Point(getX, getY), new Doorway(direction.toOrientation, false))
  }

  def getDirection: Direction4 = direction

  def remove(): Unit = room.remove(this)
}