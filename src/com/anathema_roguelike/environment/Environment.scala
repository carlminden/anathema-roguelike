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
package environment

import com.anathema_roguelike.entities.Entity
import com.anathema_roguelike.entities.events.EnvironmentChangedEvent
import com.anathema_roguelike.environment.Environment.EnvironmentMap
import com.anathema_roguelike.environment.terrain.Terrain
import com.anathema_roguelike.environment.terrain.grounds.Stairs
import com.anathema_roguelike.fov.{LightLevels, LitFOVProcessor, ObstructionChangedEvent}
import com.anathema_roguelike.main.{Config, Game}
import com.anathema_roguelike.main.display.{DisplayBuffer, Renderable}
import com.anathema_roguelike.main.utilities.pathfinding.Path
import com.anathema_roguelike.main.utilities.position.Direction.Direction2
import com.anathema_roguelike.main.utilities.position.Orientation.Orientation
import com.anathema_roguelike.main.utilities.position.{Direction, HasPosition, Point}
import com.anathema_roguelike.stats.locationstats.Opacity
import com.google.common.eventbus.{EventBus, Subscribe}

import scala.collection.mutable.ListBuffer
import scala.reflect.runtime.universe._

object Environment {
  type EnvironmentMap = Array[Array[Location]]
}

class Environment(mapTiles: Array[Array[MapTile]], up: Point, down: Point, depth: Int, initialEntities: ListBuffer[((Location) => Entity, Point)]) extends Renderable {

  private val width = Config.DUNGEON_WIDTH
  private val height = Config.DUNGEON_HEIGHT

  private val eventBus: EventBus = new EventBus

  eventBus.register(this)

  private val fovResistance: Array[Array[Double]] = Array.ofDim[Double](width, height)

  private val fogOfWarForeground = new DisplayBuffer(width, height, false)
  private val fogOfWarBackground = new DisplayBuffer(width, height, false)
  private val fogOfWarLight = new DisplayBuffer(width, height, false)
  private val lightLevels = new LightLevels(width, height, this)
  private val litFOVProcessor = new LitFOVProcessor(width, height, fovResistance, lightLevels)

  private val map = Array.ofDim[Location](width, height)

  for (i <- map.indices; j <- map(i).indices) {
    map(i)(j) = new Location(this, Point(i, j), eventBus, mapTiles(i)(j).terrain, mapTiles(i)(j).getFeatures.toSeq:_*)
  }

  private val entities: ListBuffer[Entity] = ListBuffer()

  initialEntities.foreach {
    case (entitiyBuilder, p) => entitiyBuilder(map(p.x)(p.y))
  }

  private val upStairsLocation = map(up.getX)(up.getY)
  private val downStairsLocation = map(down.getX)(down.getY)

  upStairsLocation.setTerrain(new Stairs(Direction.UP))
  downStairsLocation.setTerrain(new Stairs(Direction.DOWN))

  for (i <- 0 until width; j <- 0 until height) {
    computeOpacity(getLocation(i, j))
  }

  def computeOpacity(location: HasLocation): Unit = {
    fovResistance(location.getX)(location.getY) = location.getLocation.getStatAmount[Opacity]
  }

  override def render(): Unit = {

    for (i <- 0 until width; j <- 0 to height) {
        getLocation(i, j).render()
    }

    for (entity <- entities) {
      entity.render()
    }
  }

  def getEntities[T <: Entity : TypeTag]: Iterable[T] = entities.filterByType[T]

  def getEntitiesAt[T <: Entity : TypeTag](location: HasLocation): Iterable[T] = {
    entities.filterByType[T].filter(e => e.getLocation.equals(location))
  }

  def getEntitiesAt(location: Location): Iterable[Entity] = entities.filter(e => e.getLocation == location)

  def removeEntity(entity: Entity): Unit = {
    entities -= entity
    lightLevels.entityRemoved(entity)
    eventBus.unregister(entity)
  }

  def addEntity(entity: Entity): Unit = {
    addEntity(entity, entity.getLocation)
  }

  def addEntity(entity: Entity, location: HasLocation): Unit = {

    if(entity.getEnvironment != location.getEnvironment) {
      val old: Environment = entity.getEnvironment

      old.removeEntity(entity)

      entity.postEvent(new EnvironmentChangedEvent(old, this))
    }

    entities += entity
    eventBus.register(entity)

    entity.setLocation(location)
    computeOpacity(location)
  }

  def moveEntityBy(entity: Entity, xOffset: Int, yOffset: Int): Unit = {
    val currentPosition: Point = entity.getPosition
    moveEntityTo(entity, getLocation(xOffset + currentPosition.getX, yOffset + currentPosition.getY))
  }

  def moveEntityTo(entity: Entity, x: Int, y: Int): Unit = {
    moveEntityTo(entity, getLocation(x, y))
  }

  def moveEntityTo(entity: Entity, p: Point): Unit = {
    moveEntityTo(entity, p.getX, p.getY)
  }

  def moveEntityTo(entity: Entity, location: HasLocation): Unit = {
    entity.setLocation(location.getLocation)
  }

  def getMap: EnvironmentMap = map

  def setTerrain(terrain: Terrain, x: Int, y: Int): Unit = {
    map(x)(y).setTerrain(terrain)
  }

  def getLocation(x: Int, y: Int): Location = map(x)(y)

  def getLocation(position: HasPosition): Location = getLocation(position.getX, position.getY)

  def getStairs(direction: Direction2): Stairs = {
    direction match {
      case Direction.UP => upStairsLocation.getTerrain.asInstanceOf[Stairs]
      case Direction.DOWN => downStairsLocation.getTerrain.asInstanceOf[Stairs]
    }
  }

  def getDepth: Int = depth

  def isPassable(x: Int, y: Int): Boolean = getLocation(x, y).isPassable

  def isPassable(point: HasPosition): Boolean = isPassable(point.getX, point.getY)

  def isInBounds(point: HasPosition): Boolean = point.getX >= 0 && point.getY >= 0 && point.getX < width && point.getY < height

  def getFOVResistances: Array[Array[Double]] = fovResistance

  def getFogOfWarForeground: DisplayBuffer = fogOfWarForeground

  def getFogOfWarBackground: DisplayBuffer = fogOfWarBackground

  def getFogOfWarLight: DisplayBuffer = fogOfWarLight

  def getLightLevels: LightLevels = lightLevels

  def getEventBus: EventBus = eventBus

  def getLitFOVProcessor: LitFOVProcessor = litFOVProcessor

  @Subscribe
  def processObstructionChangedEvent(e: ObstructionChangedEvent): Unit = {
    computeOpacity(getLocation(e.getX, e.getY))
  }

  def lineOfEffectBetween(a: HasLocation, b: HasLocation): Boolean = {
    val path: Path = Path.straightLine(a.getPosition, b.getPosition)
    path.forall(p => getLocation(p).isPassable)
  }
}
