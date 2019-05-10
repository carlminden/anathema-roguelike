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

import com.anathema_roguelike.environment.{Environment, EnvironmentFactory, Location}
import com.anathema_roguelike.environment.features.Doorway
import com.anathema_roguelike.environment.generation.DungeonFeature
import com.anathema_roguelike.environment.generation.DungeonGenerator
import com.anathema_roguelike.environment.terrain.grounds.Stone
import com.anathema_roguelike.main.Config
import com.anathema_roguelike.main.ui.uielements.Rectangular
import com.anathema_roguelike.main.utilities.position.{Direction, Orientation, Point}
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.Direction.Direction4

import scala.collection.mutable.ListBuffer
import util.control.Breaks._

object Room {
  implicit def ordering[T <: Room]: Ordering[T] = Ordering.by(r => r.getWidth * r.getHeight)
}

abstract class Room(depth: Int, averageWidth: Int, averageHeight: Int, minWidth: Option[Int] = None, minHeight: Option[Int] = None)
  extends DungeonFeature(Point(0, 0)) with Rectangular {

  private var width = 0
  private var height = 0
  private val doors = ListBuffer[Door]()

  height = Math.max(
    minHeight.map(h => Math.max(3, h)).getOrElse(3),
    (((Utils.getRandom.nextFloat - .5) * averageHeight) + averageHeight).toInt
  )

  height = Math.min(Config.DUNGEON_HEIGHT - 7, height)

  width = Math.max(
    minWidth.map(w => Math.max(3, w)).getOrElse(3),
    (((Utils.getRandom.nextFloat - .5) * averageWidth) + averageWidth).toInt
  )

  width = Math.min(Config.DUNGEON_WIDTH - 7, width)

  def generateEncounter(factory: EnvironmentFactory): Unit

  override def getWidth: Int = width

  override def getHeight: Int = height

  def addDoor(door: Door): Unit = doors += door

  def getDoors: Iterable[Door] = doors

  override def validate(generator: DungeonGenerator): Boolean = {

    var ret = false

    breakable {
      (0 until 5).foreach(attempts => {
        val x = Utils.getRandom.nextInt(Config.DUNGEON_WIDTH - (width + 6)) + 3
        val y = Utils.getRandom.nextInt(Config.DUNGEON_HEIGHT - (height + 6)) + 3
        setPosition(Point(x, y))

        for (i <- 0 until width; j <- 0 until height) {
          addPoint(Point(getX + i, getY + j))
        }

        var intersects = false

        breakable {
          for (i <- 0 until width) {
            if(generator.getRooms.intersections(Point(getX + i, getY + getHeight)).nonEmpty) {
              intersects = true
              break
            }
            if(generator.getRooms.intersections(Point(getX + i, getY - 1)).nonEmpty) {
              intersects = true
              break
            }
          }
        }

        breakable {
          for (i <- 0 until height) {
            if(generator.getRooms.intersections(Point(getX + getWidth, getY + i)).nonEmpty) {
              intersects = true
              break
            }
            if(generator.getRooms.intersections(Point(getX - 1, getY + i)).nonEmpty) {
              intersects = true
              break
            }
          }
        }

        if(generator.getRooms.intersections(this).isEmpty && !intersects) {
          ret = true

          break
        }
        else {
          getPoints.clear()
        }
      })
    }
    ret
  }

  override def place(factory: EnvironmentFactory): Unit = {

    for (i <- 0 until width; j <- 0 until height) {
      val x = getX + j
      val y = getY + i

      factory.setTerrain(Point(x, y), new Stone())
    }

    for (door <- doors) {
      factory.addFeatures(door, new Doorway(door.getDirection.toOrientation))
    }
  }

  def getRandomPointInRoom: Point = {
    val x = Utils.getRandom.nextInt(getWidth - 2) + getX + 1
    val y = Utils.getRandom.nextInt(getHeight - 2) + getY + 1

    Point(x, y)
  }

  def remove(door: Door): Unit = doors -= door

  def createRandomDoor(generator: DungeonGenerator): Door = {
    var pos = 0
    var point = Point(0, 0)

    var direction: Direction4 = Direction.NO_DIRECTION

    for (_ <- 0 until 10) {
      point = Point(0, 0)

      val rand = Utils.getRandom
      val side = rand.nextInt(4)

      direction = Direction.DIRECTIONS_4(side)

      if(direction.includes(Orientation.VERTICAL)) {

        pos = rand.nextInt(getWidth - 3) + 1
        if(direction.includes(Direction.UP.value)) {
          point = Point(getX + pos, getY)
        } else {
          point = Point(getX + pos, getY + getHeight - 1)
        }
      } else {
        pos = rand.nextInt(getHeight - 3) + 1

        if(direction.includes(Direction.LEFT.value)) {
          point = Point(getX, getY + pos)
        } else {
          point = Point(getX + getWidth - 1, getY + pos)
        }
      }

      //try to make a door that isnt adjacent to any existing doors
      var adjacentDoor = false

      for (j <- 0 until 8) {
        val dir = Direction.DIRECTIONS_8(j)
        val offset = Direction.offset(point, dir)

        if(generator.getDoors.intersections(offset).nonEmpty) {
          adjacentDoor = true
        }
      }

      if(!adjacentDoor) break //todo: break is not supported
    }

    val door = new Door(this, point, direction)

    addDoor(door)

    door
  }
}