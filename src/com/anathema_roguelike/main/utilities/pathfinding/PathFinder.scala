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

import java.util.Comparator
import java.util
import java.util.PriorityQueue

import com.anathema_roguelike.main.utilities.position.{Direction, Point}

import scala.collection.mutable

object PathFinder {
  private def distance(src: Point, dst: Point) = src.manhattanDistance(dst) * 10
}

abstract class PathFinder {
  protected def isPassable(p: Point, direction: Direction): Boolean

  protected def getExtraCost(p: Point, direction: Direction, previousDirection: Direction): Int

  def getPath(src: Point, dst: Point): Option[Path] = {

    val f: Comparator[Node] = (o1: Node, o2: Node) => {
      val df = o1.getF - o2.getF

      if(df == 0) {
        o1.getPosition.compareTo(o2.getPosition)
      } else {
        df
      }
    }

    val closed = mutable.Map[Point, Node]()
    val openMap = mutable.Map[Point, Node]()

    val openQueue = new PriorityQueue[Node](25, f)
    val startingNode = new Node(null, dst, Direction.NO_DIRECTION, 0, PathFinder.distance(dst, src))

    openQueue.add(startingNode)
    openMap.put(startingNode.getPosition, startingNode)
    val directions = getValidDirections
    while(!openQueue.isEmpty && !closed.contains(src)) {
      val current = openQueue.poll

      openMap.remove(current.getPosition)
      closed.put(current.getPosition, current)

      for (direction <- directions) {
        val neighbor = Direction.offset(current.getPosition, direction)

        if(!closed.contains(neighbor) && isPassable(neighbor, direction)) {
          val cost = getBaseCost(neighbor, direction, current.getDirection) + getExtraCost(neighbor, direction, current.getDirection)

          val neighborNode = new Node(current, neighbor, direction, current.getG + cost, PathFinder.distance(neighbor, src))

          if(!openMap.contains(neighbor)) {
            openQueue.add(neighborNode)
            openMap.put(neighborNode.getPosition, neighborNode)
          } else if(openMap(neighbor).getG > (current.getG + cost)) {
            val old = openMap(neighbor)
            openQueue.remove(old)
            old.setParent(current)
            old.setG(current.getG + cost)
            openQueue.add(old)
          }
        }
      }
    }

    if(openQueue.isEmpty){
      None
    } else {
      val path = new Path
      var node = closed.get(src)
      while(node.isDefined) {
        path += node.get.getPosition
        node = node.get.getParent
      }

      path
    }

  }

  protected def getValidDirections: Array[Direction] = Direction.DIRECTIONS_8

  protected def getBaseCost(point: Point, direction: Direction, previousDirection: Direction): Int = {
    /*if(Arrays.binarySearch(Direction.DIRECTIONS_4, direction) >= 0) {
         return 10;
       } else {
         return 14;
       }*/
    //I don't think this makes sense because diagonals are no more expensive than non-diagonals
    //although I could make moving on diagonals slower to compensate for their otherwise extra efficiency,
    //if I did so, it would make sense to have this
    10
  }
}