package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.shapes

import com.anathema_roguelike.main.utilities.position.{Direction, HasPosition, Point}

class Cone(center: HasPosition, var radius: Double, var direction: Direction) extends Shape(center) {

  override protected def generatePoints(): Unit = {
    val angle = direction.toAngle

    val x = getX
    val y = getY

    for (i <- 0 until radius.toInt; j <- 0 until radius.toInt) {
      val p = new Point(x + i, y + j)

      if (getPosition.squareDistance(p) <= radius*radius && Math.abs(Direction.angleOf(getPosition, p) - angle) <= 22.5) {
        addPoint(new Point(x + i, y + j))
      }
    }
  }
}
