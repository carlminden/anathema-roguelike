

package com.anathema_roguelike
package environment

import com.anathema_roguelike.entities.Entity
import com.anathema_roguelike.entities.events.EnvironmentChangedEvent
import com.anathema_roguelike.environment.Environment.EnvironmentMap
import com.anathema_roguelike.environment.terrain.Terrain
import com.anathema_roguelike.environment.terrain.grounds.Stairs
import com.anathema_roguelike.fov.{LightLevels, LitFOVProcessor, ObstructionChangedEvent}
import com.anathema_roguelike.main.Config
import com.anathema_roguelike.main.display.{DisplayBuffer, Renderable}
import com.anathema_roguelike.main.utilities.pathfinding.Path
import com.anathema_roguelike.main.utilities.position.Direction.Direction2
import com.anathema_roguelike.main.utilities.position.{Direction, HasPosition, Point}
import com.anathema_roguelike.stats.locationstats.Opacity
import com.google.common.eventbus.{EventBus, Subscribe}

import scala.collection.mutable.ListBuffer
import scala.reflect.runtime.universe._

object Environment {
  type EnvironmentMap = Array[Array[Location]]

  private val width: Int = Config.DUNGEON_WIDTH
  private val height: Int = Config.DUNGEON_HEIGHT


  def createEnvironmentMap: EnvironmentMap = Array.ofDim[Location](width, height)
}

class Environment(map: EnvironmentMap, upStairs: Stairs, downStairs: Stairs, depth: Int) extends Renderable {

  private val width = Environment.width
  private val height = Environment.height

  private val fovResistance: Array[Array[Double]] = Array.ofDim[Double](width, height)

  private val entities: ListBuffer[Entity] = ListBuffer[Entity]()

  private val fogOfWarForeground = new DisplayBuffer(width, height, false)
  private val fogOfWarBackground = new DisplayBuffer(width, height, false)
  private val fogOfWarLight = new DisplayBuffer(width, height, false)
  private val lightLevels = new LightLevels(width, height, this)
  private val litFOVProcessor = new LitFOVProcessor(width, height, fovResistance, lightLevels)

  private val eventBus: EventBus = new EventBus
  eventBus.register(this)

  map(upStairs.getX)(upStairs.getY).setTerrain(upStairs)
  map(downStairs.getX)(downStairs.getY).setTerrain(downStairs)

  for (i <- 0 until width; j <- 0 to height) {
    computeOpacity(getLocation(i, j))
  }

  def computeOpacity(location: Location): Unit = {
    fovResistance(location.getX)(location.getY) = location.getStatAmount[Opacity]
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

  def addEntity(entity: Entity, position: HasPosition): Unit = {
    addEntity(entity, getLocation(position))
  }

  def addEntity(entity: Entity): Unit = {
    val old: Environment = entity.getEnvironment

    entity.getEnvironment.removeEntity(entity)

    entities :+ entity
    eventBus.register(entity)

    computeOpacity(entity.getLocation)

    entity.postEvent(new EnvironmentChangedEvent(old, this))
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

  def moveEntityTo(entity: Entity, location: Location): Unit = {
    entity.setLocation(location)
  }

  def getMap: EnvironmentMap = map

  def setTerrain(terrain: Terrain, x: Int, y: Int): Unit = {
    map(x)(y).setTerrain(terrain)
  }

  def getLocation(x: Int, y: Int): Location = map(x)(y)

  def getLocation(position: HasPosition): Location = getLocation(position.getX, position.getY)

  def getStairs(direction: Direction2): Stairs = {
    direction match {
      case Direction.UP => upStairs
      case Direction.DOWN => downStairs
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
    path.stream.allMatch((p: Point) => getLocation(p).isPassable)
  }
}
