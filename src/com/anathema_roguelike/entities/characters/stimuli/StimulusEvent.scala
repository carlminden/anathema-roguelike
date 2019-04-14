package com.anathema_roguelike
package entities.characters.stimuli

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.environment.{HasLocation, Location}

class StimulusEvent(var location: Location, var stimulus: Stimulus) extends HasLocation {

  def getPercievedStimulus(character: Character): Option[PerceivedStimulus] = {
    stimulus.computePerceivedStimulus(location, character)
  }

  def getStimulus: Stimulus = stimulus

  override def getLocation: Location = location
}

