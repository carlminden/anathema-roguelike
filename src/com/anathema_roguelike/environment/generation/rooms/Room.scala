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

import com.anathema_roguelike.environment.Environment
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.environment.features.Doorway
import com.anathema_roguelike.environment.generation.DungeonFeature
import com.anathema_roguelike.environment.generation.DungeonGenerator
import com.anathema_roguelike.environment.terrain.grounds.Stone
import com.anathema_roguelike.main.Config
import com.anathema_roguelike.main.ui.uielements.Rectangular
import com.anathema_roguelike.main.utilities.position.{Orientation, Point}
import java.util.Random

import scala.collection.mutable.ListBuffer
import util.control.Breaks._

object Room {
  private val rand = new Random
}

abstract class Room(depth: Int, averageWidth: Int, averageHeight: Int, minWidth: Option[Int] = None, minHeight: Option[Int] = None)
  extends DungeonFeature(new Point(0, 0)) with Rectangular with Comparable[Room] {

  private var width = 0
  private var height = 0
  private val doors = ListBuffer[Door]()

  height = Math.max(
    minHeight.map(h => Math.max(3, h)).getOrElse(3),
    (((Room.rand.nextFloat - .5) * averageHeight) + averageHeight).toInt
  )

  height = Math.min(Config.DUNGEON_HEIGHT - 7, height)

  width = Math.max(
    minWidth.map(w => Math.max(3, w)).getOrElse(3),
    (((Room.rand.nextFloat - .5) * averageWidth) + averageWidth).toInt
  )

  width = Math.min(Config.DUNGEON_WIDTH - 7, width)

  def generateEncounter(level: Environment): Unit

  override def getWidth: Int = width

  override def getHeight: Int = height

  def addDoor(door: Door): Unit = doors += door

  def getDoors: Iterable[Door] = doors

  override def validate(generator: DungeonGenerator): Boolean = {

    var ret = false

    breakable {
      (0 until 5).foreach(attempts => {
        val x = Room.rand.nextInt(Config.DUNGEON_WIDTH - (width + 6)) + 3
        val y = Room.rand.nextInt(Config.DUNGEON_HEIGHT - (height + 6)) + 3
        setPosition(new Point(x, y))

        for (i <- 0 until width; j <- 0 until height) {
          addPoint(new Point(getX + i, getY + j))
        }

        var intersects = false

        breakable {
          for (i <- 0 until width) {
            if(!generator.getRooms.intersections(new Point(getX + i, getY + getHeight)).isEmpty) {
              intersects = true
              break
            }
            if(!generator.getRooms.intersections(new Point(getX + i, getY - 1)).isEmpty) {
              intersects = true
              break
            }
          }
        }

        breakable {
          for (i <- 0 until height) {
            if(!generator.getRooms.intersections(new Point(getX + getWidth, getY + i)).isEmpty) {
              intersects = true
              break
            }
            if(!generator.getRooms.intersections(new Point(getX - 1, getY + i)).isEmpty) {
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

  override def place(generator: DungeonGenerator): Unit = {
    val map = generator.getMap
    val level = generator.getLevel

    for (i <- 0 until width; j <- 0 until height) {
      val x = getX + j
      val y = getY + i

      map(x)(y).setTerrain(new Stone())
    }

    for (door <- doors) {
      map(door.getX)(door.getY).addFeature(new Doorway(door.getDirection))
    }
  }

  def getRandomPointInRoom: Point = {
    val x = Room.rand.nextInt(getWidth - 2) + getX + 1
    val y = Room.rand.nextInt(getHeight - 2) + getY + 1
    new Point(x, y)
  }

  override def compareTo(room: Room): Int = (getWidth * getHeight) - (room.getWidth * room.getHeight)

  def remove(door: Door): Unit = doors -= door
}