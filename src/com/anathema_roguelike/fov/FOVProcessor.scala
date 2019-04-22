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
package fov

import com.anathema_roguelike.entities.Entity
import squidpony.squidgrid.FOV
import squidpony.squidgrid.Radius

abstract class FOVProcessor(var width: Int, var height: Int, var resistances: Array[Array[Double]]) {
  protected def visit(entity: Entity, x: Int, y: Int, light: Double): Unit

  protected def doFOV(entity: Entity, radius: Double, angle: Double, span: Double, fov: FOV): Unit = {
    val startx = entity.getX
    val starty = entity.getY

    val light = fov.calculateFOV(resistances, startx, starty, radius, Radius.CIRCLE, angle, span)

    for (
      x <- Math.max(startx - radius, 0).toInt until Math.min(startx + radius, width).toInt;
      y <- Math.max(starty - radius, 0).toInt until Math.min(starty + radius, height).toInt
    ) {
      visit(entity, x, y, light(x)(y) / 2)
    }
  }
}