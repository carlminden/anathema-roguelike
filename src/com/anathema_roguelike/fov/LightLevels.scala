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
import com.anathema_roguelike.environment.Environment
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.main.utilities.position.Point
import com.google.common.collect.HashBasedTable
import com.google.common.eventbus.Subscribe
import squidpony.squidgrid.FOV
import squidpony.squidmath.PerlinNoise

import scala.collection.mutable
import scala.collection.JavaConverters._

object LightLevels {
  def anitmateLight(initialLight: Double, x: Double, y: Double): Double = {

    var light = initialLight

    val turnTime = Game.getInstance.getDisplay.getRenderTime
    val noise = PerlinNoise.noise(x, y, Math.sin(Math.floor(turnTime / 75 % 75) / 75 * Math.PI) * 13)

    light += Math.sin((turnTime + (noise * 1000)) / (100 * Math.PI)) / 18 + noise / 18
    light = Utils.clamp(light, 0.0, 1.0)

    light
  }
}

class LightLevels(width: Int, height: Int, level: Environment) extends FOVProcessor(width, height, level.getFOVResistances) {

  private val lightLevels = HashBasedTable.create[Entity, DirectionVector, Double]
  private val lastSeen = mutable.Map[Entity, Point]()
  private val dirty = mutable.Map[Entity, Boolean]()
  private val computedLightLevels = mutable.Map[Direction, Array[Array[Double]]]()

  computedLightLevels.put(Direction.UP, Array.ofDim[Double](width,height))
  computedLightLevels.put(Direction.DOWN, Array.ofDim[Double](width,height))
  computedLightLevels.put(Direction.RIGHT, Array.ofDim[Double](width,height))
  computedLightLevels.put(Direction.LEFT, Array.ofDim[Double](width,height))

  level.getEventBus.register(this)

  override protected def visit(entity: Entity, x: Int, y: Int, light: Double): Unit = {
    val position = Point(x, y)
    if(light > 0) {
      val directionToSource = Direction.of(entity.getPosition, position)

      for (direction <- Direction.DIRECTIONS_4) {
        if(directionToSource.includes(direction) || level.getFOVResistances(x)(y) != 1) {

          val dv = DirectionVector(position, direction)
          lightLevels.put(entity, dv, light)

          computedLightLevels(direction)(x)(y) += light
        }
      }
    }
  }

  def recomputeLightLevels(): Unit = {
    for (entity <- level.getEntities[Entity]) {
      if(entity.getLightEmission > 0 && !(entity.getPosition == lastSeen(entity)) || isDirty(entity)) {

        lightLevels.row(entity).entrySet.forEach(entry => {
          val p = entry.getKey.position
          val direction = entry.getKey.direction

          computedLightLevels(direction)(p.getX)(p.getY) -= entry.getValue
        })

        lightLevels.row(entity).clear()
        doFOV(entity, entity.getLightEmission, 0, 360, new FOV(FOV.RIPPLE_TIGHT))
        lastSeen.put(entity, entity.getPosition)
        dirty.put(entity, false)
      }
    }
  }

  def entityRemoved(entity: Entity): Unit = {

    lightLevels.row(entity).entrySet.forEach(entry => {
      val p = entry.getKey.position
      val direction = entry.getKey.direction

      computedLightLevels(direction)(p.getX)(p.getY) -= entry.getValue
    })

    lightLevels.row(entity).clear()
    lastSeen.remove(entity)
    dirty.remove(entity)
  }

  private def isDirty(entity: Entity) = {
    dirty.getOrElse(entity, false)
  }

  def get(x: Int, y: Int, direction: Direction): Double = {
    var ret = 0.0
    for (d <- Direction.DIRECTIONS_4) {
      if(direction == Direction.NO_DIRECTION || (direction.includes(d))) {
        ret = Math.min(1, Math.max(ret, computedLightLevels(d)(x)(y)))
      }
    }
    ret
  }

  def get(x: Int, y: Int): Double = get(x, y, 0)

  def get(point: Point, direction: Direction): Double = get(point.getX, point.getY, direction)

  def get(point: Point): Double = get(point.getX, point.getY, 0)

  def get(dv: DirectionVector): Double = get(dv.position.getX, dv.position.getY, dv.direction)

  @Subscribe def processObstructionChangedEvent(e: ObstructionChangedEvent): Unit = {
    val p = e.getPosition
    for (entity <- level.getEntities[Entity]) {
      if(p.squareDistance(entity.getPosition) < (entity.getLightEmission * entity.getLightEmission)) {
        dirty.put(entity, true)
      }
    }

    recomputeLightLevels()
  }
}