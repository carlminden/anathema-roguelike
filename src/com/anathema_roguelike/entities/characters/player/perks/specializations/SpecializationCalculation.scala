package com.anathema_roguelike
package entities.characters.player.perks.specializations

import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability

abstract class SpecializationCalculation[T](var ability: Ability) extends (() => T) {
  override def apply: T = ability.getSpecializationLevel match {
    case 0 =>
      zero
    case 1 =>
      one
    case 2 =>
      two
    case 3 =>
      three
    case _ =>
      throw new RuntimeException("Invalid Specialization Level")
  }

  protected def zero: T

  protected def one: T

  protected def two: T

  protected def three: T
}