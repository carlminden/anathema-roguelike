package com.anathema_roguelike
package main

import java.util.{Collections, Comparator, PriorityQueue}

import com.anathema_roguelike.actors.{Actor, TimeElapsedEvent}
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.environment.{Environment, Location}
import com.anathema_roguelike.environment.generation.{CaveDungeonGenerator, DungeonGenerator}
import com.anathema_roguelike.main.display.Renderable
import com.anathema_roguelike.main.ui.charactercreation.CharacterCreationUI
import com.anathema_roguelike.main.utilities.position.{Direction, Point}
import com.anathema_roguelike.entities.characters.Character

import scala.collection.mutable.ListBuffer

class State() extends Renderable {

  private var elapsedTime: Double = 0
  private var currentSegmentTime: Double = 1

  //	private DungeonGenerator dungeonLevelFactory = new DefaultDungeonLevelGenerator();
  private val dungeonLevelFactory: DungeonGenerator = new CaveDungeonGenerator
  //	private DungeonGenerator dungeonLevelFactory = new BigRoomDungeonGenerator();

  private val actors: PriorityQueue[Actor] = new PriorityQueue[Actor](Collections.reverseOrder(new Comparator[Actor]() {
    override def compare(o1: Actor, o2: Actor): Int = o1.getEnergyLevel.compareTo(o2.getEnergyLevel)
  }))

  private val dungeonLevels = ListBuffer[Environment]()
  generateDungeonLevels()


  val upstairs: Location = getEnvironment(0).getStairs(Direction.UP).getLocation

  private val player: Player = CharacterCreationUI.createCharacter(upstairs)

  def computeNextState(): Unit = {
    var currentActor: Actor = actors.remove

    while (!currentActor.isInstanceOf[Player]) {
      if (currentActor.getDuration.isExpired) {
        currentActor = actors.remove
      } else {
        handleActor(currentActor)
        actors.add(currentActor)
        currentActor = actors.remove
      }
    }

    handleActor(currentActor)
    actors.add(currentActor)
  }

  private def handleActor(actor: Actor): Unit = {
    val startTime: Double = actor.getEnergyLevel

    if (actor.getEnergyLevel <= 0) {
      actors.forEach((a: Actor) => a.energize())
    } else {
      actor.act()
      val timePassed: Double = startTime - actors.peek.getEnergyLevel

      elapsedTime += timePassed
      currentSegmentTime -= timePassed
      Game.getInstance.getEventBus.post(new TimeElapsedEvent(elapsedTime))
    }
  }

  override def render(): Unit = {
    getCurrentEnvironment.render()
  }

  def getPlayer: Player = player

  def getCurrentEnvironment: Environment = player.getEnvironment

  def getEnvironment(z: Int): Environment = {
    if (z < 0 || z >= Config.DUNGEON_DEPTH) return null
    //TODO: WTF is this? is it to wait for another thread or something? seems like we could do better with a concurrency object
    while (dungeonLevels.size <= z) { }

    dungeonLevels.get(z)
  }

  def generateDungeonLevels(): Unit = {

    (0 until Config.DUNGEON_DEPTH).foreach(i => {

      val newLevel: Environment = dungeonLevelFactory.createLevel(i)
      dungeonLevels :+ newLevel

      newLevel.getEntities(classOf[Character]).foreach(c => c.computeVisibility())
    })
  }

  def getElapsedTime: Double = elapsedTime

  def registerActor(actor: Actor): Unit = {
    actors.add(actor)
  }

  def removeActor(actor: Actor): Unit = {
    actors.remove(actor)
  }
}
