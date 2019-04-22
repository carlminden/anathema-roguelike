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
package entities.characters.actions.costs

import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.entities.characters.stimuli.{Sight, Stimulus}
import com.anathema_roguelike.environment.HasLocation
import com.anathema_roguelike.entities.characters.Character
import scala.reflect.runtime.universe._

class StimulusCost[S <: Stimulus : TypeTag](
      character: Character,
      magnitude: () => Double,
      location: Option[HasLocation] = Option.empty,
      after: Boolean = false) extends CharacterActionCost(character, after) {

  override def pay(): Unit = {
    if ((getStimulus == classOf[Sight]) && getCharacter.isInstanceOf[Player]) {
      System.out.println("PLAYER GENERATING SIGHT STIMULI AT: " + location.getOrElse(character.getLocation))
    }

    val l = location.getOrElse(character.getLocation).getLocation

    l.generateStimulus(getStimulus.getConstructor(classOf[Double], classOf[Option[Character]]).newInstance(getMagnitude.asInstanceOf[AnyRef], Some(getCharacter)))
  }

  def getStimulus: Class[S] = typeTagToClass[S]

  def getMagnitude: Double = magnitude()
}

