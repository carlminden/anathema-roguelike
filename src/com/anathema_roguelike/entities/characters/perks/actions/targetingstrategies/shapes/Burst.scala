package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.shapes

import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.main.utilities.position.HasPosition
import com.anathema_roguelike.main.utilities.position.Point
import com.anathema_roguelike.stats.effects.Calculation

class Burst(val origin: HasPosition, var sizeCalculation: Calculation) extends Shape(origin) {
  override def generatePoints(): Unit = {
    val x = getX
    val y = getY

    sizeCalculation().intValue match {
      case 1 =>
        for (d <- Direction.DIRECTIONS_4) {
          addPoint(Direction.offset(getPosition, d))
        }
      case 2 =>
        for (d <- Direction.DIRECTIONS_8) {
          addPoint(Direction.offset(getPosition, d))
        }
        addPoint(new Point(x + 2, y))
        addPoint(new Point(x - 2, y))
        addPoint(new Point(x, y + 2))
        addPoint(new Point(x, y - 2))
      case 3 =>
        addPoint(new Point(x + 2, y + 2))
        addPoint(new Point(x - 2, y - 2))
        addPoint(new Point(x + 2, y - 2))
        addPoint(new Point(x - 2, y + 2))
        addPoint(new Point(x + 1, y + 2))
        addPoint(new Point(x - 1, y + 2))
        addPoint(new Point(x + 1, y - 2))
        addPoint(new Point(x - 1, y - 2))
        addPoint(new Point(x + 3, y))
        addPoint(new Point(x - 3, y))
        addPoint(new Point(x, y + 3))
        addPoint(new Point(x, y - 3))
      case _ =>
    }
  }
}