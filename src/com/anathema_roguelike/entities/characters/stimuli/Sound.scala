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

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Circle
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.display.Display.DisplayLayer
import com.anathema_roguelike.main.display.{Color, VisualRepresentation}
import com.anathema_roguelike.main.display.animations.Ripple
import com.anathema_roguelike.main.ui.UIConfig
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.senses.Hearing
import com.anathema_roguelike.entities.characters.Character

class Sound(magnitude: Double, owner: Option[Character] = None) extends Stimulus(magnitude, owner) {

  def computePerceivedStimulus(location: Location, character: Character): Option[PerceivedStimulus] = {

    (owner, character) match {
      case (Some(_), _: Player) => new Ripple(location, getMagnitude / 5, 0.2f).create(DisplayLayer.DUNGEON_OVERLAY, UIConfig.DUNGEON_OFFSET)
      case _ =>
    }

    //TODO determine how to handle sound being stopped/reduced by walls etc

    if (location.distance(character) > getMagnitude / 5) {
      Option.empty
    } else {
      val hearing = character.getStatAmount[Hearing]
      val perceivedSound = getMagnitude * (4 * hearing) / (hearing + 10)

      perceivedSound match {
        case _ if perceivedSound >= 100 => new PerceivedStimulus(location, this, perceivedSound)
        case _ if perceivedSound >= 50 => {
          val percievedLocation = location.getEnvironment.getLocation(new Circle(location, () => 2.0).getRandomPassablePoint(location.getEnvironment))
          new PerceivedStimulus(percievedLocation, this, perceivedSound)
        }
        case _ if perceivedSound >= 25 => {
          val percievedLocation = location.getEnvironment.getLocation(new Circle(location, () => 3.0).getRandomPassablePoint(location.getEnvironment))
          new PerceivedStimulus(percievedLocation, this, perceivedSound)
        }
        case _ => Option.empty
      }
    }
  }

  override def getVisualRepresentation = new VisualRepresentation('!', Color.WHITE)
}
