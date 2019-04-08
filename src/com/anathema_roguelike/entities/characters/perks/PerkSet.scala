

package com.anathema_roguelike
package entities.characters.perks

import java.util.function.Predicate

import com.anathema_roguelike.main.utilities.datastructures.CollectionUtils

import scala.collection.mutable
import scala.reflect.runtime.universe._

class PerkSet {
  private val perks: mutable.Set[Perk] = mutable.Set[Perk]()

  def get[T <: Perk : TypeTag]: Iterable[T] = CollectionUtils.filterByClass[Perk, T](perks)

  def get[T <: Perk : TypeTag](predicate: T => Boolean): Iterable[T] = CollectionUtils.filterByClass[Perk, T](perks).filter(predicate)

  def add(perk: Perk): Unit = {
    perks.add(perk)
  }

  def remove(perk: Perk): Unit = {
    perks.remove(perk)
  }
}
