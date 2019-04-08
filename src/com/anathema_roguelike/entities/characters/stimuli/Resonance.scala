

package com.anathema_roguelike
package entities.characters.stimuli

import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.display.{Color, VisualRepresentation}
import com.anathema_roguelike.entities.characters.Character

class Resonance(magnitude: Double, owner: Character) extends Stimulus(magnitude, owner) {

  def computePerceivedStimulus(location: Location, character: Character): Option[PerceivedStimulus] = {
    new PerceivedStimulus(location, this, getMagnitude)
  }

  override def getVisualRepresentation = new VisualRepresentation('*', Color.RESONANCE)
}
