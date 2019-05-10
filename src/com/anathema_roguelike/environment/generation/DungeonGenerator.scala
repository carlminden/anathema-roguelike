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
package environment.generation

import com.anathema_roguelike.environment.{Environment, EnvironmentFactory, Location}
import com.anathema_roguelike.environment.generation.rooms.Door
import com.anathema_roguelike.environment.generation.rooms.Room
import com.anathema_roguelike.environment.terrain.Terrain
import com.anathema_roguelike.environment.terrain.grounds.Stairs
import com.anathema_roguelike.environment.terrain.walls.StoneWall
import com.anathema_roguelike.main.Config
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.main.utilities.position.{Direction, Point}
import org.jgrapht.alg.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.ListenableUndirectedGraph

object DungeonGenerator {
  def validPoint(point: Point): Boolean = {
    !(point.getX <= 1 || point.getX >= Config.DUNGEON_WIDTH - 1 || point.getY <= 1 || point.getY >= Config.DUNGEON_HEIGHT - 1)
  }
}

abstract class DungeonGenerator(defaultTerrain: () => Terrain = () => new StoneWall) {

  private val rooms = new FeatureGroup[Room]
  private val doors = new FeatureGroup[Door]
  private val tunnelSystems = new FeatureGroup[TunnelSystem]
  private val factory = new EnvironmentFactory(defaultTerrain)
  private val roomGraph = new ListenableUndirectedGraph[Room, DefaultEdge](classOf[DefaultEdge])

  protected def generateRooms(depth: Int): Iterable[Room]

  def createLevel(depth: Int): Environment = {

    val potentialRooms = generateRooms(depth).toList.sorted(Ordering[Room].reverse)

    for (room <- potentialRooms) {
      if(room.validate(this)) rooms += room
    }
    var inspector = new ConnectivityInspector[Room, DefaultEdge](roomGraph)

    for (room <- rooms) {
      roomGraph.addVertex(room)
    }
    val digger = new TunnelDigger
    while(!inspector.isGraphConnected) {
      val room1 = rooms(Utils.getRandom.nextInt(getRooms.size))
      val room2 = rooms(Utils.getRandom.nextInt(getRooms.size))

      if((room1 != room2 && !inspector.pathExists(room1, room2))){
        digger.connect(this, room1, room2)
        inspector = new ConnectivityInspector[Room, DefaultEdge](roomGraph)
      }
    }

    for (system <- tunnelSystems) {
      system.place(factory)
    }

    for (room <- rooms) {
      room.place(factory)
    }
    val upstairsRoom = rooms(Utils.getRandom.nextInt(rooms.size))
    val downstairsRoom = rooms(Utils.getRandom.nextInt(rooms.size))
    factory.setUpStairs(upstairsRoom.getRandomPointInRoom)
    factory.setDownStairs(downstairsRoom.getRandomPointInRoom)
    //TODO maybe not all rooms should get encounters, or maybe that should just be up to the room

    val environment = factory.build(depth)

    for (room <- rooms) {
      room.generateEncounter(factory)
    }

    environment
  }

  def getEnvironmentFactory: EnvironmentFactory = factory

  def getRooms: FeatureGroup[Room] = rooms

  def addRoom(room: Room): Unit = rooms += room

  def getTunnelSystems: FeatureGroup[TunnelSystem] = tunnelSystems

  def addTunnelSystem(system: TunnelSystem): Unit = tunnelSystems += system

  def getDoors: FeatureGroup[Door] = doors

  def addDoor(door: Door): Unit = doors += door

  def removeDoor(door: Door): Unit = {
    doors -= door
    door.remove()
  }

  def getRoomGraph: ListenableUndirectedGraph[Room, DefaultEdge] = roomGraph
}