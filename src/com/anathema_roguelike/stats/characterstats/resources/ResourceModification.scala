

package com.anathema_roguelike
package stats.characterstats.resources

import com.anathema_roguelike.actors.Duration
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.stats.effects.{Calculation, Effect, FixedCalculation, HasEffect}
import scala.reflect.runtime.universe._

class ResourceModification[T <: Resource : TypeTag](
      initiator: Option[Character],
      source: Option[_ <: HasEffect[Effect[Character, T]]],
      calculation: Calculation
  ) extends Effect[Character, T](source, List(), Duration.instant()) {

  def this(initiator: Option[Character], source: Option[_ <: HasEffect[Effect[Character, T]]], amount: Int) {

    this(initiator, source, new FixedCalculation(amount))
  }

  def getResource: Class[T] = typeTagToClass
  def getInitiator: Option[Character] = initiator

  override def onApplicationCallback(character: Character): Unit = {
    super.onApplicationCallback(character)
    character.modifyResource[T](getInitiator, getSource, calculation().intValue)
  }
}
