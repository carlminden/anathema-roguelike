

package com.anathema_roguelike
package entities.characters.stimuli

import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.display.VisualRepresentation
import com.anathema_roguelike.entities.characters.Character

abstract class Stimulus(magnitude: Double, owner: Option[Character] = None) {

  def getVisualRepresentation: VisualRepresentation

  def getMagnitude: Double = magnitude

  def getOwner: Option[Character] = owner

  def computePerceivedStimulus(location: Location, character: Character): Option[PerceivedStimulus]

  override def toString: String = magnitude + " " + this.getClass.getSimpleName
}
