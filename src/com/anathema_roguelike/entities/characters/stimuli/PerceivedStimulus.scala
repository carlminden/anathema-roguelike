package com.anathema_roguelike
package entities.characters.stimuli

import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.{Color, VisualRepresentation}

class PerceivedStimulus(var location: Option[Location], var stimulus: Stimulus, var magnitude: Double) {

  private val created = Game.getInstance.getElapsedTime

  def getRemainingMagnitude: Double = magnitude - ((Game.getInstance.getElapsedTime - created) * 25)

  protected def getMagnitude: Double = magnitude

  def getLocation: Option[Location] = location

  def getStimulus: Stimulus = stimulus

  def getVisualRepresentation: VisualRepresentation = {
    val vr = getStimulus.getVisualRepresentation
    val opacity = (Math.min(100, getRemainingMagnitude) / 100).toFloat
    vr.setColor(Color.opacity(vr.getColor, opacity))
    vr
  }

  override def toString: String = getRemainingMagnitude + " remaining of " + stimulus.toString + location.map((value: Location) => " at: " + value).orElse("")
}
