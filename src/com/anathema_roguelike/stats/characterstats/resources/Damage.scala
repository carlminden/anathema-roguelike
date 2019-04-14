package com.anathema_roguelike
package stats.characterstats.resources

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.stats.effects.{Calculation, Effect, FixedCalculation, HasEffect}
import scala.reflect.runtime.universe._


class Damage[T <: Resource : TypeTag](
    attacker: Option[Character],
    source: Option[HasEffect[_ <: Effect[Character, T]]],
    calculation: Calculation
  ) extends ResourceModification[T](attacker, source, () => calculation() * -1) {


  def this(
    attacker: Option[Character],
    source: Option[HasEffect[_ <: Effect[Character, T]]],
    amount: Int
  ) {
    this(attacker, source, new FixedCalculation(amount * -1))
  }
}
