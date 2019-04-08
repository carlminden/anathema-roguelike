

package com.anathema_roguelike
package entities.characters.stimuli

import java.util.{Collections, Comparator}

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.events.TurnStartEvent
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.environment.Location
import com.google.common.collect.TreeMultiset
import com.google.common.eventbus.Subscribe
import scala.collection.JavaConverters._

class PerceivedStimuli(var character: Character, stimuli: PerceivedStimuli*) {

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

}
