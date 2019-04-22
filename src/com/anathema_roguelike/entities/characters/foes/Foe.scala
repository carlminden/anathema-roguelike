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
package entities.characters.foes

import com.anathema_roguelike.entities.characters.foes.ai.AI
import com.anathema_roguelike.entities.characters.foes.ai.Faction
import com.anathema_roguelike.entities.characters.foes.corruptions.Corruption
import com.anathema_roguelike.entities.characters.foes.roles.Role
import com.anathema_roguelike.entities.characters.foes.traits.Trait
import com.anathema_roguelike.entities.characters.stimuli.PerceivedStimulus
import com.anathema_roguelike.main.Game

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer

abstract class Foe(role: Role, corruption: Corruption, traits: Trait[_]*) extends Character {
  setFaction(Faction.FOES)

  private val ai = new AI(this)
  new BaseFoeStats().grant(this)

  private var mostInterestingStimulus: Option[PerceivedStimulus] = Option.empty
  private val distanceComparator = (o1: Character, o2: Character) => {
    val d1 = getPosition.squareDistance(o1)
    val d2 = getPosition.squareDistance(o2)


    if(d1 > d2) true
    else if(d2 > d1) false
    else o1.getPosition > o2.getPosition
  }

  override def onDeath(): Unit = {
    getEnvironment.removeEntity(this)
    Game.getInstance.getState.removeActor(this)
    Game.getInstance.getEventBus.unregister(this)
    getEventBus.unregister(this)
  }

  override def setNextPendingAction(): Unit = {
    mostInterestingStimulus = getPercievedStimuli.mostInterestingStimulus
    ai.addNextPendingAction()
  }

  def getAI: AI = ai

  def getVisibleEnemies: Iterable[Character] = {
    getCurrentlyVisibleCharacters.toList.filter(c => !Faction.friendly(this, c)).sortWith(distanceComparator)
  }

  def getMostInterestingStimulus: Option[PerceivedStimulus] = mostInterestingStimulus

  override protected def renderThis(): Unit = Game.getInstance.getMap.renderEntity(DungeonLayer.NPCS, this)
}