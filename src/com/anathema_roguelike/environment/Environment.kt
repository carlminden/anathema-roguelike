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
package com.anathema_roguelike.environment

import java.util.ArrayList
import java.util.Optional
import java.util.stream.Collectors

import com.anathema_roguelike.entities.Entity
import com.anathema_roguelike.entities.events.EnvironmentChangedEvent
import com.anathema_roguelike.environment.terrain.Terrain
import com.anathema_roguelike.environment.terrain.grounds.Stairs
import com.anathema_roguelike.fov.LightLevels
import com.anathema_roguelike.fov.LitFOVProcessor
import com.anathema_roguelike.fov.ObstructionChangedEvent
import com.anathema_roguelike.main.Config
import com.anathema_roguelike.main.display.DisplayBuffer
import com.anathema_roguelike.main.display.Renderable
import com.anathema_roguelike.main.utilities.datastructures.CollectionUtils
import com.anathema_roguelike.main.utilities.pathfinding.Path
import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.main.utilities.position.HasPosition
import com.anathema_roguelike.main.utilities.position.Point
import com.anathema_roguelike.stats.locationstats.Opacity
import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe

class Environment(val depth: Int) : Renderable {
    private val width = Config.DUNGEON_WIDTH
    private val height = Config.DUNGEON_HEIGHT
    val fovResistances = Array(width) { DoubleArray(height) }
    private var upStairs: Stairs? = null
    private var downStairs: Stairs? = null

    val fogOfWarForeground: DisplayBuffer
    val fogOfWarBackground: DisplayBuffer
    val fogOfWarLight: DisplayBuffer
    val lightLevels: LightLevels
    val litFOVProcessor: LitFOVProcessor

    val map = Array<Array<Location?>>(width) { arrayOfNulls(height) }
    private val entities = ArrayList<Entity>()

    val eventBus = EventBus()

    init {

        fogOfWarForeground = DisplayBuffer(width, height, false)
        fogOfWarBackground = DisplayBuffer(width, height, false)
        fogOfWarLight = DisplayBuffer(width, height, false)
        lightLevels = LightLevels(width, height, this)
        litFOVProcessor = LitFOVProcessor(width, height, fovResistances, lightLevels)

        eventBus.register(this)
    }

    fun init() {

        for (i in 0 until width) {
            for (j in 0 until height) {
                fovResistances[i][j] = getLocation(i, j)!!.getStatAmount(Opacity::class.java)
            }
        }
    }

    override fun render() {

        for (i in 0 until width) {
            for (j in 0 until height) {
                val location = getLocation(i, j)

                location.render()
            }
        }

        for (entity in entities) {
            entity.render()
        }
    }

    fun <T : Entity> getEntities(cls: Class<T>): Collection<T> {
        return CollectionUtils.filterByClass(entities, cls)
    }

    fun <T : Entity> getEntitiesAt(location: Location, cls: Class<T>): Collection<T> {
        return CollectionUtils.filterByClass(entities, cls).filter { e -> e.location == location }
    }

    fun getEntitiesAt(location: Location): Collection<Entity> {
        return entities.filter { e -> e.location == location }
    }

    fun removeEntity(entity: Entity) {
        entities.remove(entity)

        lightLevels.entityRemoved(entity)

        eventBus.unregister(entity)
    }

    fun addEntity(entity: Entity, position: HasPosition) {
        addEntity(entity, getLocation(position))
    }

    fun addEntity(entity: Entity, location: Location) {
        var old = Optional.empty<Environment>()
        if (entity.location != null) {
            old = Optional.of(entity.environment)

            old.ifPresent { e -> removeEntity(entity) }
        }

        entities.add(entity)

        entity.location = location

        eventBus.register(entity)
        entity.postEvent(EnvironmentChangedEvent(old, this))
    }

    fun moveEntityBy(entity: Entity, xOffset: Int, yOffset: Int) {
        val currentPosition = entity.position

        moveEntityTo(entity, getLocation(xOffset + currentPosition.x, yOffset + currentPosition.y))
    }

    fun moveEntityTo(entity: Entity, x: Int, y: Int) {
        moveEntityTo(entity, getLocation(x, y))
    }

    fun moveEntityTo(entity: Entity, p: Point) {
        moveEntityTo(entity, p.x, p.y)
    }

    fun moveEntityTo(entity: Entity, location: Location) {
        entity.location = location
    }

    fun setTerrain(terrain: Terrain, x: Int, y: Int) {
        map[x][y]!!.terrain = terrain
    }

    fun getLocation(x: Int, y: Int): Location {
        return map[x][y]!!
    }

    fun getLocation(position: HasPosition): Location {
        return getLocation(position.x, position.y)
    }

    fun getStairs(direction: Int): Stairs? {

        if (direction == Direction.UP) {
            return upStairs
        } else if (direction == Direction.DOWN) {
            return downStairs
        }

        return null
    }

    fun setUpStairs(upStairs: Stairs, position: Point) {
        this.upStairs = upStairs

        map[position.x][position.y]!!.terrain = upStairs
    }

    fun setDownStairs(downStairs: Stairs, position: Point) {
        this.downStairs = downStairs

        map[position.x][position.y]!!.terrain = downStairs
    }

    fun isPassable(x: Int, y: Int): Boolean {
        return getLocation(x, y).isPassable
    }

    fun isPassable(point: HasPosition): Boolean {
        return isPassable(point.x, point.y)
    }

    fun isInBounds(point: HasPosition): Boolean {
        return point.x >= 0 && point.y >= 0 && point.x < width && point.y < height
    }

    @Subscribe
    fun processObstructionChangedEvent(e: ObstructionChangedEvent) {

        val point = e.position

        val location = getLocation(point.x, point.y)

        fovResistances[point.x][point.y] = location.getStatAmount(Opacity::class.java)
    }

    fun lineOfEffectBetween(a: HasLocation, b: HasLocation): Boolean {
        val path = Path.straightLine(a.position, b.position)

        return path.stream().allMatch { p -> getLocation(p).isPassable }
    }
}
