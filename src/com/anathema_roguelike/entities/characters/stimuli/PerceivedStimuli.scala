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
package entities.characters.stimuli

import java.util.{Collections, Comparator}

import cats.Functor
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.events.TurnStartEvent
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.environment.Location
import com.google.common.collect.TreeMultiset
import com.google.common.eventbus.Subscribe

class PerceivedStimuli(var character: Character) {

  character.getEventBus.register(this)

  private val stimuliComparator: Comparator[PerceivedStimulus] = (o1: PerceivedStimulus, o2: PerceivedStimulus) => {
    var stimulus1: Double = o1.getRemainingMagnitude
    var stimulus2: Double = o2.getRemainingMagnitude

    if (o1.getLocation.isDefined && !(character.getLocation == o1.getLocation.get)) stimulus1 += 20 / o1.getLocation.get.squareDistance(character)
    if (o2.getLocation.isDefined && !(character.getLocation == o2.getLocation.get)) stimulus2 += 20 / o2.getLocation.get.squareDistance(character)

    stimulus1.compareTo(stimulus2)
  }

  private val set: TreeMultiset[PerceivedStimulus] = TreeMultiset.create(stimuliComparator)

  private def prune(): Unit = {
    set.removeIf(s => {
      var ret: Boolean = s.getRemainingMagnitude <= 0

      s.getLocation.map(l => {
        ret = ret || (character.isAdjacentTo(l) || !l.isPassable)

        s.getStimulus.getOwner.map(o => {
          ret = ret || (o eq character)

          if (o.isVisibleTo(character)) {
            ret || character.getCurrentVisibility.get(l)
          } else {
            ret
          }
        }).getOrElse(ret)

      }).getOrElse(ret)
    })
  }

  @Subscribe
  def perceiveStimulus(e: StimulusEvent): Unit = {
    val perceived: Option[PerceivedStimulus] = e.getPercievedStimulus(character)
    //TODO should override existing stimuli at location if magnitude is greater (including dissipation)
    perceived.foreach(p => set.add(p))
  }

  @Subscribe
  def handleTurnStartEvent(t: TurnStartEvent): Unit = {
    prune()
  }

  def mostInterestingStimulus: Option[PerceivedStimulus] = {
    if (set.size > 0) {
      set.firstEntry().getElement
    } else Option.empty
  }

  def foreach(f: PerceivedStimulus => Unit): Unit = {
    set.forEach(s => f(s))
  }
}
