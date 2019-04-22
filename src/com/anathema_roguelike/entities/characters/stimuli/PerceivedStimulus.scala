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

import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.{Color, VisualRepresentation}

class PerceivedStimulus(location: Option[Location], stimulus: Stimulus, magnitude: Double) {

  private val created = Game.getInstance.getElapsedTime

  def getRemainingMagnitude: Double = magnitude - ((Game.getInstance.getElapsedTime - created) * 25)

  protected def getMagnitude: Double = magnitude

  def getLocation: Option[Location] = location

  def getStimulus: Stimulus = stimulus

  def getVisualRepresentation: VisualRepresentation = {
    val vr = getStimulus.getVisualRepresentation
    val opacity = (Math.min(100, getRemainingMagnitude) / 100).toFloat

    vr.color = (Color.opacity(vr.color, opacity))
    vr
  }

  override def toString: String = getRemainingMagnitude + " remaining of " + stimulus.toString + location.map((value: Location) => " at: " + value).orElse("")
}
