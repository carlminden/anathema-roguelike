package com.anathema_roguelike
package entities.characters.perks

import java.util.function.Predicate

import scala.collection.mutable
import scala.reflect.runtime.universe._

class PerkSet {
  private val perks: mutable.Set[Perk] = mutable.Set[Perk]()

  def get[T <: Perk : TypeTag]: Iterable[T] = perks.filterByType[T]

  def get[T <: Perk : TypeTag](predicate: T => Boolean): Iterable[T] = perks.filterByType[T].filter(predicate)

  def add(perk: Perk): Unit = {
    perks.add(perk)
  }

  def remove(perk: Perk): Unit = {
    perks.remove(perk)
  }
}
