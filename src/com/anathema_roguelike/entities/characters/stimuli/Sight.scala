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
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.display.{Color, VisualRepresentation}
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.VisibilityLevel
import com.anathema_roguelike.entities.characters.Character

class Sight(magnitude: Double, owner: Character) extends Stimulus(magnitude, owner) {

  def computePerceivedStimulus(location: Location, character: Character): Option[PerceivedStimulus] = {

    getOwner.flatMap(owner => {
      if (character.getLastSeenCharacterLocations.contains(owner)) {
        if (character.getLastSeenCharacterLocations(owner).isAdjacentTo(location)) {
          System.out.println(character + " perceiving " + owner + " at: " + location + " with magnitude: " + getMagnitude)
          new PerceivedStimulus(location, this, Math.min(getMagnitude, getMagnitude + 100))
        } else {
          Option.empty
        }
      } else {
        Option.empty
      }
    }) match {
      case Some(s) => s
      case None => {
        if (character.getVisibilityOf(getOwner.get) == VisibilityLevel.PARTIALLYCONCEALED) {
          val percievedLocation = character.getEnvironment.getLocation(new Circle(location, () => 3.0).getRandomPoint)
          new PerceivedStimulus(percievedLocation, this, getMagnitude)

        } else if (character.getVisibilityOf(getOwner.get).value > VisibilityLevel.VISIBLE.value) {
          new PerceivedStimulus(location, this, getMagnitude)
        } else {
          Option.empty
        }
      }
    }
  }

  override def getVisualRepresentation = new VisualRepresentation('?', Color.WHITE)
}
