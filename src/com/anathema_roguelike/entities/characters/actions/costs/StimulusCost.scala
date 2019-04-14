package com.anathema_roguelike
package entities.characters.actions.costs

import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.entities.characters.stimuli.{Sight, Stimulus}
import com.anathema_roguelike.environment.HasLocation
import com.anathema_roguelike.entities.characters.Character

class StimulusCost[S <: Stimulus](
      character: Character,
      stimulus: Class[S],
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

  def getStimulus: Class[S] = stimulus

  def getMagnitude: Double = magnitude()
}

