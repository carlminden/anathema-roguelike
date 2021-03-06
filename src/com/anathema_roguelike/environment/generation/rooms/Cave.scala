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

import com.anathema_roguelike.entities.characters.foes.corruptions.Thrall
import com.anathema_roguelike.entities.characters.foes.roles.Brawler
import com.anathema_roguelike.entities.characters.foes.species.generic.Orc
import com.anathema_roguelike.environment.{Environment, EnvironmentFactory}
import com.anathema_roguelike.environment.generation.DungeonGenerator
import com.anathema_roguelike.environment.terrain.grounds.Stone
import com.anathema_roguelike.environment.terrain.walls.StoneWall
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.main.utilities.position.Point
import java.util.Random

import com.anathema_roguelike.main.utilities.Utils

import scala.collection.mutable.ListBuffer

class Cave(val depth: Int, val averageWidth: Int, val averageHeight: Int)
  extends Room(depth, 15, 15, averageWidth, averageHeight) {

  private var cells = Array.ofDim[Boolean](getWidth, getHeight)
  private var cells2 = Array.ofDim[Boolean](getWidth, getHeight)
  private val rand = Utils.getRandom
  private val floors = ListBuffer[Point]()

  var openArea = 0f
  while (openArea < .5f) {

    cells = Array.ofDim[Boolean](getWidth, getHeight)
    cells2 = Array.ofDim[Boolean](getWidth, getHeight)

    for (i <- 0 until getWidth; j <- 0 until getHeight) {
      cells(i)(j) = rand.nextFloat < .4f
    }

    for(passes <- 0 until 3) {
      for (i <- 0 until getWidth; j <- 0 until getHeight) {
        if(filledCellsWithin(i, j, 1) >= 5 || filledCellsWithin(i, j, 2) <= 2) {
          cells2(i)(j) = true
        } else {
          cells2(i)(j) = false
        }
      }
      cells = cells2
      cells2 = Array.ofDim[Boolean](getWidth, getHeight)
    }

    for(passes <- 0 until 5) {
      for (i <- 0 until getWidth; j <- 0 until getHeight) {
        if(filledCellsWithin(i, j, 1) >= 5 || filledCellsWithin(i, j, 2) <= -1) {
          cells2(i)(j) = true
        } else {
          cells2(i)(j) = false
        }
      }

      cells = cells2
      cells2 = Array.ofDim[Boolean](getWidth, getHeight)
    }

    var x = rand.nextInt(getWidth)
    var y = rand.nextInt(getHeight)


    while (cells(x)(y)) {
      x = rand.nextInt(getWidth)
      y = rand.nextInt(getHeight)
    }

    for (i <- 0 until getWidth; j <- 0 until getHeight) {
      cells2(i)(j) = cells(i)(j)
    }

    openArea = floodFill(x, y, cells2).toFloat / (getWidth * getHeight)

  }

  for (i <- 0 until getWidth; j <- 0 until getHeight) {
    cells(i)(j) = !(cells(i)(j) ^ cells2(i)(j))
  }

  for (i <- 0 until getWidth; j <- 0 until getHeight) {
    if(!cells(i)(j)) {
      floors += Point(i, j)
    }
  }

  private def floodFill(x: Int, y: Int, temp: Array[Array[Boolean]]): Int = {
    if(temp(x)(y)) {
      0
    } else {
      temp(x)(y) = true

      var ret = 1

      for (direction <- Direction.DIRECTIONS_8) {
        val next = Direction.offset(Point(x, y), direction)

        if((0 until getWidth).contains(next.x) && (0 until getHeight).contains(next.y)) {
          ret += floodFill(next.getX, next.getY, temp)
        }
      }
      ret
    }
  }

  override def place(factory: EnvironmentFactory): Unit = {

    for (floor <- floors) {
      val x = floor.getX + getX
      val y = floor.getY + getY

      factory.setTerrain(Point(x, y), new Stone)
    }

    for (door <- getDoors) {
      var current = door.getPosition

      while(DungeonGenerator.validPoint(current) && factory.getTerrainAt(current).isInstanceOf[StoneWall]) {

        factory.setTerrain(current, new Stone)

        current = Direction.offset(current, door.getDirection, 1)
      }

      current = Direction.offset(door.getPosition, door.getDirection, -1)
      while(DungeonGenerator.validPoint(current) && factory.getTerrainAt(current).isInstanceOf[StoneWall]) {

        factory.setTerrain(current, new Stone)

        current = Direction.offset(current, door.getDirection, -1)
      }
    }
  }

  override def getRandomPointInRoom: Point = {
    val floor = floors.get(rand.nextInt(floors.size))
    Point(floor.getX + getX, floor.getY + getY)
  }

  private def filledCellsWithin(x: Int, y: Int, distance: Int) = {

    var ret = 0

    for (i <- -distance to distance; j <- -distance to distance) {
      if(getCell(x + i, y + j)) {
        ret += 1
      }
    }

    ret
  }

  private def getCell(x: Int, y: Int) = {
    if(x >= 0 && x < getWidth && y >= 0 && y < getHeight) {
      cells(x)(y)
    } else {
      true
    }
  }

  override def generateEncounter(factory: EnvironmentFactory): Unit = {

    for (_ <- 0 until 10) {
      val x = Game.getInstance.getRandom.nextInt(getWidth - 2) + getX + 1
      val y = Game.getInstance.getRandom.nextInt(getHeight - 2) + getY + 1

      if(factory.getTerrainAt(Point(x, y)).isPassable) {
          factory.addEntity((l) => new Orc(l, new Brawler, new Thrall), Point(x, y))
      }
    }
  }
}