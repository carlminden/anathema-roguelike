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

import com.anathema_roguelike.environment.generation.rooms.Door
import com.anathema_roguelike.environment.generation.rooms.Room
import com.anathema_roguelike.main.utilities.pathfinding.PathFinder
import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.main.utilities.position.Orientation
import com.anathema_roguelike.main.utilities.position.Point
import util.control.Breaks._

class TunnelDigger extends PathFinder {
  private var generator: DungeonGenerator = _
  private var startingRoom: Room = _
  private var endingRoom: Room = _

  override protected def getValidDirections: Array[Direction] = Direction.DIRECTIONS_4

  protected def isPassable(p: Point, direction: Direction): Boolean = DungeonGenerator.validPoint(p)

  protected def getExtraCost(p: Point, direction: Direction, previousDirection: Direction): Int = {
    var extraCost = 0

    if(adjacentAlignedTunnel(generator, p, direction)) extraCost += 1000
    if(cornerOfRoom(generator, p)) extraCost += 1000
    if(adjacentDoor(generator, p)) extraCost += 1000
    if(diagonalTunnel(generator, p, previousDirection)) extraCost += 1000

    extraCost
  }

  protected override def getBaseCost(point: Point, direction: Direction, previousDirection: Direction): Int = {
    if(generator.getRooms.intersections(point).nonEmpty){
      10
    } else if((direction == previousDirection) || generator.getTunnelSystems.intersections(point).nonEmpty) {
      10
    } else {
      20
    }
  }

  def connect(generator: DungeonGenerator, room1: Room, room2: Room): Unit = {
    this.generator = generator
    this.startingRoom = room1
    this.endingRoom = room2

    val tunnelSystem = new TunnelSystem(room1)
    val startDoor = room1.createRandomDoor(generator)
    generator.addDoor(startDoor)

    val start = Direction.offset(startDoor.getPosition, startDoor.getDirection)
    val endDoor = room2.createRandomDoor(generator)
    generator.addDoor(endDoor)

    val end = endDoor.getPosition

    getPath(start, end).foreach(path => {
      var currentRoom = room1
      var lastDoor = startDoor
      var currentDirection: Direction = Direction.NO_DIRECTION

      breakable {
        for (i <- 0 until path.size - 1) {
          val point = path(i)
          val next = path(i + 1)

          currentDirection = Direction.of(path(i), next)

          if(startingRoom.intersects(point)) {
            generator.removeDoor(startDoor)
            generator.removeDoor(endDoor)
            generator.removeDoor(lastDoor)
            return //horrifying, but I think this should be fine?
          }

          if(endingRoom.intersects(point)) {
            generator.removeDoor(endDoor)
            break
          }

          if(currentRoom.intersects(point)) {
            if(!currentRoom.intersects(next)) {
              lastDoor = new Door(currentRoom, point, currentDirection)
              generator.addDoor(lastDoor)
            }
          } else {
            if(generator.getDoors.intersections(point).isEmpty) {
              for (room <- generator.getRooms) {
                if(room.intersects(next)) {
                  if(!generator.getRoomGraph.containsEdge(currentRoom, room)) {
                    generator.getRoomGraph.addEdge(currentRoom, room)
                  }

                  currentRoom = room
                  lastDoor = new Door(room, next, currentDirection)
                  generator.addDoor(lastDoor)
                  break
                }
              }
              val tunnel = new Tunnel(point, currentDirection.value)
              tunnelSystem.add(tunnel)
            }
          }
        }
      }

      generator.addTunnelSystem(tunnelSystem)
    })
  }

  private def adjacentAlignedTunnel(generator: DungeonGenerator, position: Point, direction: Direction): Boolean = {
    Direction.DIAGONALS.exists(dir => {
      generator.getTunnelSystems.intersections(Direction.offset(position, dir)).exists(ts => {
        ts.getTunnels.values.exists(t => t.getDirection == direction)
      })
    })
  }

  private def diagonalTunnel(generator: DungeonGenerator, position: Point, direction: Direction): Boolean = {

    Direction.DIAGONALS.exists(dir => {
      generator.getTunnelSystems.exists(ts => {
        val tunnel = ts.getTunnel(Direction.offset(position, dir))
        if(tunnel != null && tunnel.getDirection == direction) {
          true
        } else {
          false
        }
      })
    })
  }

  private def cornerOfRoom(generator: DungeonGenerator, position: Point): Boolean = {


    Direction.DIRECTIONS_4.exists(dir => {
      val offset = Direction.offset(position, dir)

      generator.getRooms.intersections(offset).exists(room => {
        var ret = false

        if(room.getX == offset.getX) {
          if(room.getY == offset.getY || (room.getY + room.getHeight - 1 == offset.getY)){
            ret = true
          }
        }

        if(room.getY == offset.getY && (room.getX + room.getWidth - 1) == offset.getX){
          ret = true
        } else if(room.getY + room.getHeight - 1 == offset.getY && (room.getX + room.getWidth - 1) == offset.getX) {
          ret = true
        }

        ret
      })
    })
  }

  private def adjacentDoor(generator: DungeonGenerator, position: Point): Boolean = {

    Direction.DIRECTIONS_8.exists(dir => {
      val offset = Direction.offset(position, dir)

      generator.getDoors.intersections(offset).nonEmpty
    })
  }
}