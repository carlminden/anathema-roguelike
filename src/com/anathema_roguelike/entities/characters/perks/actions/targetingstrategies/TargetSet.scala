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
package entities.characters.perks.actions.targetingstrategies

import com.anathema_roguelike.main.utilities.JavaUtils
import com.anathema_roguelike.main.utilities.datastructures.CircularArrayList
import com.anathema_roguelike.main.utilities.position.Direction._
import com.anathema_roguelike.main.utilities.position.{Direction, Point}
import com.google.common.collect.TreeBasedTable

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._

class TargetSet[T <: Targetable](val targets: Iterable[T]) {

  private val targetPositions = JavaUtils.createTreeBasedTable[ListBuffer[T]]()


  for (target <- targets) {
    val positionTargets = targetPositions.get(target.getX, target.getY)
    if(positionTargets == null) {
      targetPositions.put(target.getY, target.getX, ListBuffer[T]())
      targetPositions.get(target.getY, target.getX) += target
    }
  }

  private val targetList = new CircularArrayList[T](targetPositions.values.asScala.flatten)

  targetList.sortWith((p1, p2) => p1.getPosition.squareDistance(Point(0, 0)) > p2.getPosition.squareDistance(Point(0, 0)))

  private var index = 0
  private var tmpIndex = 0

  def inDirection(direction: Direction): T = {
    val currentPosition = get(current.getPosition)

    direction match {
      case UP | RIGHT | UP_RIGHT | UP_LEFT => tmpIndex += 1
      case DOWN | LEFT | DOWN_LEFT | DOWN_RIGHT | _ => tmpIndex -= 1
    }

    if(tmpIndex >= 0 && tmpIndex < currentPosition.size) {
      target(currentPosition(tmpIndex))
    } else {
      tmpIndex = 0
      val line = getLine(direction)

      direction match {
        case UP_RIGHT | UP_LEFT | DOWN_LEFT | DOWN_RIGHT =>
          val newTarget = get(Direction.offset(currentPosition.head, direction))
          if(newTarget != null) {
            target(newTarget.get.head)
          } else next
        case UP | LEFT =>
          target(line.get(line.indexOf(currentPosition) - 1))
        case DOWN | RIGHT | _ =>
          target(line.get(line.indexOf(currentPosition) + 1))
      }
    }
  }

  def next: T = next(1)

  def next(amount: Int): T = {
    index = index + amount
    targetList.get(index)
  }

  def prev: T = next(-1)

  def prev(amount: Int): T = next(-amount)

  def current: T = targetList.get(index)

  def getTargets: Iterable[T] = targetList

  private def get(position: Point) = targetPositions.get(position.getY, position.getX)

  private def target(target: Option[T]): T = {

    target.map(t => {
      val oldTarget = current
      index = targetList.indexOf(target)
      if(!(current == oldTarget)) {
        t
      } else {
        next
      }
    }).getOrElse(next)
  }

  private def getLine(direction: Direction) = direction match {
    case UP | UP_RIGHT | UP_LEFT =>
      new CircularArrayList(targetPositions.column(current.getX).values.asScala.flatten)
    case DOWN | DOWN_LEFT | DOWN_RIGHT =>
      new CircularArrayList(targetPositions.column(current.getX).values.asScala.flatten)
    case Direction.RIGHT =>
      new CircularArrayList(targetPositions.row(current.getY).values.asScala.flatten)
    case Direction.LEFT =>
      new CircularArrayList(targetPositions.row(current.getY).values.asScala.flatten)
    case _ =>
      new CircularArrayList(targetPositions.column(current.getX).values.asScala.flatten)
  }
}