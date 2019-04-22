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
package entities.characters.foes.ai

import com.anathema_roguelike.entities.characters.actions.TurnAction
import com.anathema_roguelike.entities.characters.actions.WaitAction
import com.anathema_roguelike.entities.characters.foes.Foe
import com.anathema_roguelike.entities.characters.stimuli.PerceivedStimulus
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.utilities.position.Direction
import java.security.SecureRandom

import com.anathema_roguelike.entities.characters.Character

import scala.util.Random

class AI(var npc: Foe) {
  private val pathfinder = new AIPathFinder(npc)

  def addNextPendingAction(): Unit = {
    var enemy: Option[Character] = Option.empty
    var target: Option[Location] = Option.empty
    val mostInterestingPerceivedStimulus = npc.getMostInterestingStimulus

    if(npc.getVisibleEnemies.nonEmpty) {
      enemy = npc.getVisibleEnemies.headOption
      target = enemy.map(e => e.getLocation)
    } else if(mostInterestingPerceivedStimulus.isDefined) {
      val ps = mostInterestingPerceivedStimulus.get
      if(ps.getLocation.isDefined) {
        npc.getPercievedStimuli.foreach(s => System.out.println(s))
        target = ps.getLocation
      }
    }

    if(target.isDefined) {
      val t = target.get

      val path = pathfinder.getPath(npc, t)

      if(path.isDefined && !(npc.getLocation == t)) {
        if(npc.getFacing != Direction.angleOf(npc, target.get)) {
          npc.addPendingAction(new TurnAction(npc, Direction.angleOf(npc, t)))
        } else {

          val p = path.get

          npc.move(Direction.of(npc.getLocation, p(1)))
        }
      } else {
        randomStep()
      }
    } else {
      randomStep()
    }

    if(!npc.hasPendingActions) {
      System.out.println(npc + " did not compute a new action!")
      npc.addPendingAction(new WaitAction(npc))
    }
  }

  private def randomStep(): Unit = {
    val rand = new SecureRandom
    val action = rand.nextDouble

    if(action < .8) { //continue walking
      val continueWalking = Direction.offset(npc.getPosition, Direction.angleToDirection(npc.getFacing))

      if(npc.getEnvironment.isPassable(continueWalking)) {
        npc.move(Direction.angleToDirection(npc.getFacing))
      } else { //turn around

        val reverse = Direction.offset(npc.getPosition, Direction.angleToDirection((npc.getFacing + 180) % 360))
        if(npc.getEnvironment.isPassable(reverse)) {
          npc.addPendingAction(new TurnAction(npc, (npc.getFacing + 180) % 360))
        } else {
          val passableDirections = Direction.DIRECTIONS_8.filter(d => npc.getEnvironment.isPassable(Direction.offset(npc.getPosition, d)))

          if(passableDirections.isEmpty) {
            throw new RuntimeException(npc + " is stuck at " + npc.getPosition)
          }

          npc.move(passableDirections.get(new Random().nextInt(passableDirections.length)))
        }
      }
    } else if(action < .9) {
      npc.addPendingAction(new WaitAction(npc))
    } else { //random turn
      val angle = rand.nextInt(180) - 90

      npc.addPendingAction(new TurnAction(npc, ((npc.getFacing + angle) + 360) % 360))
    }
  }
}