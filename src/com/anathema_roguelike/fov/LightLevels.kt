/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
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
 * along with this program.  If not, see <http:></http:>//www.gnu.org/licenses/>.
 */
package com.anathema_roguelike.fov

import java.util.HashMap
import kotlin.collections.Map.Entry

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

class LightLevels(width: Int, height: Int, private val level: Environment) : FOVProcessor(width, height, level.fovResistances) {

    private val lightLevels = HashBasedTable.create<Entity, DirectionVector, Double>()
    private val lastSeen = HashMap<Entity, Point>()
    private val dirty = HashMap<Entity, Boolean>()

    internal var computedLightLevels = HashMap<Int, Array<FloatArray>>()

    init {

        computedLightLevels[Direction.UP] = Array(width) { FloatArray(height) }
        computedLightLevels[Direction.DOWN] = Array(width) { FloatArray(height) }
        computedLightLevels[Direction.RIGHT] = Array(width) { FloatArray(height) }
        computedLightLevels[Direction.LEFT] = Array(width) { FloatArray(height) }
        level.eventBus.register(this)
    }

    override fun visit(entity: Entity, x: Int, y: Int, light: Double) {
        val position = Point(x, y)

        if (light > 0) {
            val directionToSource = Direction.of(entity.position, position)

            for (direction in Direction.DIRECTIONS_4) {
                if (directionToSource and direction != 0 || level.fovResistances[x][y] !== 1.0) {
                    val dv = DirectionVector(position, direction)

                    lightLevels.put(entity, dv, light)
                    computedLightLevels[direction]!![x][y] += light.toFloat()
                }
            }
        }
    }

    fun recomputeLightLevels() {
        for (entity in level.getEntities(Entity::class.java)) {
            if (entity.lightEmission > 0 && entity.position != lastSeen[entity] || isDirty(entity)) {

                for ((key, value) in lightLevels.row(entity)) {
                    val p = key.position
                    val direction = key.direction
                    computedLightLevels[direction]!![p.x][p.y] -= value.toFloat()
                }

                lightLevels.row(entity).clear()
                doFOV(entity, entity.lightEmission, 0.0, 360.0, FOV(FOV.RIPPLE_TIGHT))
                lastSeen[entity] = entity.position
                dirty[entity] = false
            }
        }
    }

    fun entityRemoved(entity: Entity) {
        for ((key, value) in lightLevels.row(entity)) {
            val p = key.position
            val direction = key.direction
            computedLightLevels[direction]!![p.x][p.y] -= value.toFloat()
        }

        lightLevels.row(entity).clear()
        lastSeen.remove(entity)
        dirty.remove(entity)
    }

    private fun isDirty(entity: Entity): Boolean {
        val isDirty = dirty[entity]

        return isDirty ?: false
    }

    @JvmOverloads
    operator fun get(x: Int, y: Int, direction: Int = 0): Float {

        var ret = 0f

        for (d in Direction.DIRECTIONS_4) {
            if (direction == 0 || direction and d != 0) {
                ret = Math.min(1f, Math.max(ret, computedLightLevels[d]!![x][y]))
            }
        }

        return ret
    }

    operator fun get(point: Point, direction: Int): Float {
        return get(point.x, point.y, direction)
    }

    operator fun get(point: Point): Float {
        return get(point.x, point.y, 0)
    }

    operator fun get(dv: DirectionVector): Float {
        return get(dv.position.x, dv.position.y, dv.direction)
    }

    @Subscribe
    fun processObstructionChangedEvent(e: ObstructionChangedEvent) {
        val p = e.position
        for (entity in level.getEntities(Entity::class.java)) {
            if (p.squareDistance(entity.position) < entity.lightEmission * entity.lightEmission) {
                dirty[entity] = true
            }
        }
        recomputeLightLevels()
    }

    companion object {

        fun anitmateLight(light: Double, x: Double, y: Double): Double {
            var light = light
            val turnTime = Game.getInstance().display.renderTime.toDouble()

            val noise = PerlinNoise.noise(x, y, Math.sin(Math.floor(turnTime / 75 % 75) / 75 * Math.PI) * 13)

            light += Math.sin((turnTime + noise * 1000) / (100 * Math.PI)) / 18 + noise / 18

            light = Utils.clamp(light, 0.0, 1.0)

            return light
        }
    }
}
