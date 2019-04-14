

package com.anathema_roguelike
package main.utilities.position

import com.anathema_roguelike.main.utilities.position.Direction.{DOWN, DOWN_LEFT, DOWN_RIGHT, LEFT, RIGHT, UP, UP_LEFT, UP_RIGHT}

sealed trait DirectionUpDown

class Direction(val value: Int) {
  def |(d: Direction): Direction = new Direction(value | d.value)
  def &(d: Direction): Direction = new Direction(value & d.value)

  def toAngle: Double = {
    this match {
      case UP =>
        270
      case DOWN =>
        90
      case LEFT =>
        180
      case RIGHT =>
        0
      case UP_RIGHT =>
        315
      case UP_LEFT =>
        225
      case DOWN_RIGHT =>
        45
      case DOWN_LEFT =>
        135
      case _ =>
        270
    }
  }
}

object Direction {

  sealed trait Direction2
  sealed trait Direction4

  case object NO_DIRECTION extends Direction(0x0) with Direction2 with Direction4
  case object UP extends Direction(0x1) with Direction2 with Direction4
  case object DOWN extends Direction(0x2) with Direction2 with Direction4
  case object LEFT extends Direction(0x4) with Direction4
  case object RIGHT extends Direction(0x8) with Direction4
  case object UP_RIGHT extends Direction((UP | RIGHT).value)
  case object UP_LEFT extends Direction((UP | LEFT).value)
  case object DOWN_RIGHT extends Direction((DOWN | RIGHT).value)
  case object DOWN_LEFT extends Direction((DOWN | LEFT).value)

  val DIRECTIONS_4 = Array(UP, DOWN, LEFT, RIGHT)
  val DIAGONALS = Array(UP_RIGHT, DOWN_RIGHT, DOWN_LEFT, UP_LEFT)
  val DIRECTIONS_8 = Array(UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT)


  def offset(point: HasPosition, direction: Direction): Point = offset(point, direction, 1)

  def offset(point: HasPosition, direction: Direction, amount: Int): Point = {
    var x = point.getX
    var y = point.getY

    if ((direction & Direction.UP) != 0){
      y = y - amount
    } else if ((direction & Direction.DOWN) != 0) {
      y = y + amount
    }

    if ((direction & Direction.LEFT) != 0) {
      x = x - amount
    } else if ((direction & Direction.RIGHT) != 0) {
      x = x + amount
    }

    new Point(x, y)
  }

  def of(src: HasPosition, dst: HasPosition): Direction = {
    var ret: Direction = NO_DIRECTION

    val dx = dst.getX - src.getX
    val dy = dst.getY - src.getY

    if (dy > 0) ret = ret | DOWN
    if (dy < 0) ret = ret | UP
    if (dx > 0) ret = ret | RIGHT
    if (dx < 0) ret = ret | LEFT

    ret
  }

  def angleOf(src: HasPosition, dst: HasPosition): Double = {
    val dx = dst.getX - src.getX
    val dy = dst.getY - src.getY

    val ret = (Math.atan2(dy, dx) * 57.3).round

    if (ret < 0) {
      ret + 360
    } else {
      ret
    }
  }

  def angleToDirection(angle: Double): Direction = {
    angle match {
      case _ if angle >= 330 && angle <= 360 => RIGHT
      case _ if angle >= 0 && angle <= 30 => RIGHT
      case _ if angle > 30 && angle < 60 => DOWN_RIGHT
      case _ if angle >= 60 && angle <= 120 => DOWN
      case _ if angle > 120 && angle < 150 => DOWN_LEFT
      case _ if angle >= 150 && angle <= 210 => LEFT
      case _ if angle > 210 && angle < 240 => UP_LEFT
      case _ if angle >= 240 && angle <= 300 => UP
      case _ if angle > 300 && angle < 330 => UP_RIGHT
      case _ => RIGHT
    }
  }

  //returns the direction from src to dst but ignores a direction if it is less than ~ (16 degrees/.28 radians)
  //used to solve some issues with lighting squares from different sides
  def approximationOf(src: Point, dst: Point): Direction = {
    var ret: Direction = NO_DIRECTION

    val dx = dst.getX - src.getX
    val dy = dst.getY - src.getY

    val angle = Math.atan2(dy, dx)

    if (angle >= -1.29 && angle <= 1.29) ret = ret | RIGHT
    if ((angle >= 1.85 && angle <= Math.PI) || (angle <= -1.85 && angle >= -Math.PI)) ret = ret | LEFT
    if (angle >= .28 && angle <= 2.86) ret = ret | DOWN
    if (angle <= -.28 && angle >= -2.86) ret = ret | UP

    ret
  }
}
